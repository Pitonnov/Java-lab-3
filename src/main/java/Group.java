import java.util.ArrayList;
import java.util.Random;

class Group {
    private String title;
    private ArrayList<Student> students;
    private Student head;

    public Group(String title) {
        this.title = title;
        this.students = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Student getHead() {
        return head;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this);
    }

    void setHead(Student head) {
        if (!students.isEmpty() && students.contains(head)) {
            this.head = head;
            System.out.println("New head of group " + title + " is " + this.head.getName());
        }
    }

    void setHead() {                // set head (random)
        if (!students.isEmpty()) {
            Random random = new Random();
            int indexOfHead = random.nextInt(this.students.size());
            head = students.get(indexOfHead);
        }
    }

    public Student searchStudent(int id) {
        for (Student student: students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Double averageMarkInGroup() {
        Double sum = 0.0;
        for (Student student: students) {
            sum += student.averageMark();
        }
        return sum / students.size();
    }

    void removeStudent(int id) {
        Student student = searchStudent(id);
        if (student != null){
            if ((head != null) && (id == head.getId())) {
                students.remove(student);
                this.setHead();
                System.out.println("New head of group " + title + " is " + head.getName());
            }
            else {
                students.remove(student);
            }
        }
    }

    void printGroupData() {
        String head;
        if (this.head == null) {
            head = "not elected yet.";
        } else {
            head = this.head.getName();
        }
        System.out.println();
        System.out.println(title + ", amount of students is " + students.size() + ", head is " + head);
    }

    void printAverageMarkForGroup() {
        if (!students.isEmpty()) {
            System.out.printf("Average mark in group is %.2f\n", averageMarkInGroup());
            System.out.println();
        }
    }

    void printAverageMarkForEachStudentInGroup() {
        if (!students.isEmpty()) {
            for (Student student: students) {
                student.printStudentData();
                student.printAverageMarkForStudent();
            }
            System.out.println();
        }
    }
}

