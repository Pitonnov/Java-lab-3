import java.util.ArrayList;

public class Group {
    private String title;
    private Student head;
    protected ArrayList<Student> studentsInGroup = new ArrayList<Student>();

    public Group(String title) {
        this.title = title;
    }

    public void addStudentToGroup(Student student) {

        this.studentsInGroup.add(student);
    }

    public Student findStudentById(int id) {
        for (Student i : this.studentsInGroup) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    public Student findStudentByFio(String fio) {
        for (Student i : this.studentsInGroup) {
            if (i.getFio().equals(fio)) {
                return i;
            }
        }
        return null;
    }

    public float avarageMarkForGroup() {
        float avarageMarkForGroup = 0;
        for (int i = 0; i < this.studentsInGroup.size(); i++) {
            avarageMarkForGroup += this.studentsInGroup.get(i).averageMark();
        }
        return avarageMarkForGroup / this.studentsInGroup.size();
    }

    public void deleteStudentFromGroup(Student student) {
        this.studentsInGroup.remove(findStudentByFio(student.getFio()));
    }


    public Student getHead() {
        return head;
    }

    public void setHead(Student student) {
        if (student instanceof Student) {
            this.head = student;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
