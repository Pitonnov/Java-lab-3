import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
//provide full group control
interface GroupControl{
    Group addStudent(Student student);
    ArrayList<Student> getStudents();
    Student getHead();
    void setHead(Student student);
    Group getGroup();
    boolean expelling(Student student);
}

public class Group {
    private String title;
    private ArrayList<Student> students = new ArrayList<Student>();
    private Student head;
    private Deanery deanery = null;

    private Group(Deanery deanery, String title){
        this.title = title;
        this.deanery = deanery;
    }

    public static GroupControl getInstance(Deanery deanery, String title){
        return new Group(deanery, title).getControl();
    }
    //return object which implements GroupControl interface using anonymous class
    private GroupControl getControl(){
        return new GroupControl() {
            @Override
            public Group addStudent(Student student) {
                students.add(student);
                return Group.this;
            }
            @Override
            public ArrayList<Student> getStudents() {
                return students;
            }
            @Override
            public Student getHead() {
                return head;
            }
            public Group getGroup(){
                return Group.this;
            }
            public boolean expelling(Student student){
                students.remove(student);
                if (head != null && head.equals(student))
                    headElection();
                return true;
            }
            @Override
            public void setHead(Student student){
                head = student;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return title.equals(group.title) &&
               Objects.equals(deanery, group.deanery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, deanery);
    }

    @Override
    public String toString(){
        return title + " group";
    }

    public String findStudent(String name){
        if (name == null || name.isEmpty()){
            Display.write("You must give student name");
            return null;
        }
        String foundStudents = "";
        for (Student student : students){
            if (student.getName().contains(name))
                foundStudents += (student.toString() + "\n");
        }
        if (foundStudents.isEmpty())
            foundStudents = "Nobody is found";
        return foundStudents;
    }
    public String findStudent(int id){
        if (id <= 0) {
            Display.write("Invalid id");
            return null;
        }
        for (Student student : students){
            if (student.getId() == id)
                return student.toString();
        }
        return "Nobody is found";
    }

    public void headElection(){
        if (students.size() >= 1) {
            head = students.get(new Random().nextInt(students.size()));
            Display.write("Head of " + this.toString() + " is " + head.toString());
        } else
            Display.write("There is no student into " + this.toString());
    }

    public String getTitle() {
        return title;
    }

    public Student getRandomStudent(){
        return students.get(new Random().nextInt(students.size()));
    }

    public String whoIsHead() {
        if (head == null)
            return this.toString() + " has no head";
        else
            return "Head of " + this.toString() + " is " + head.toString();
    }

    public float averageRating(){
        if (students.isEmpty())
            return 0;
        float ar = 0;
        for (Student student : students)
            ar += student.averageRating();
        return ar / students.size();
    }
}
