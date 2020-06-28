import java.util.ArrayList;
import java.util.Random;

public class Group {
    String title;
    ArrayList<Student> students;
    Student head;

    Group(String name){
        this.title = name;
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.addGroup(this);
    }

    public void chooseHead(){
        if (students.size() > 0) {
            Random random = new Random();
            this.head = students.get(random.nextInt(students.size()));
        }
    }

    public Student searchStudent(String name) {
        for (Student student: this.students){
            if (name == student.getFio()){
                return student;
            }
        }
        return null;
    }

    public Student searchStudent(int id) {
        for (Student student: this.students){
            if (id == student.getId()){
                return student;
            }
        }
        return null;
    }

    public double avgMarkInGroup(){
        float sum = 0;
        for (Student student: students){
            sum += student.avgMark();
        }
        return sum / students.size();
    }

    public String getTitle(){

        return this.title;
    }

    public Student getHead(){

        return this.head;
    }

    public ArrayList<Student> getStudents(){

        return this.students;
    }

    public void removeStudent(Student student){
        students.remove(student);
    }
}
