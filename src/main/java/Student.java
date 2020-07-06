import java.util.ArrayList;
import java.util.Objects;

//provide full student control
interface StudentControl{
    Group getGroup();
    void setGroup(Group group);
    ArrayList<Integer> getRating();
    Student getStudent();
}

public class Student {
    private String name;
    private int id;
    private Group group = null;
    private Deanery deanery;
    private ArrayList<Integer> marks = new ArrayList<Integer>();

    private Student(Deanery deanery, String name, int id){
        this.name = name;
        this.id = id;
        this.deanery = deanery;
    }

    public static StudentControl getInstance(Deanery deanery, String name, int id){
        if (deanery != null)
            return new Student(deanery, name, id).getControl();
        else
            return null;
    }
    //return object which implements StudentControl interface using anonymous class
    private StudentControl getControl(){
        return new StudentControl() {
            @Override
            public Group getGroup() {
                return group;
            }
            @Override
            public void setGroup(Group group) {
                Student.this.group = group;
            }
            @Override
            public ArrayList<Integer> getRating() {
                return marks;
            }
            @Override
            public Student getStudent(){
                return Student.this;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
               name.equals(student.name) &&
               Objects.equals(deanery, student.deanery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return name + "(id : " + id + ", " + this.sayGroup() + " group)";
    }

    public void enrollTo(String title) {
        deanery.enrolling(this, title);
    }

    public void transferTo(String title){
        deanery.transferring(this, title);
    }

    public void addMark(int value) {
        deanery.setStudentRating(this, value);
    }

    public String getName() {
        return name;
    }

    public int getId() {
            return id;
    }

    public String sayGroup() {
        if (group == null)
            return "Without group";
        else
            return group.getTitle();
    }

    public float averageRating(){
        if (marks.isEmpty())
            return 0;
        float ar = 0;
        for (Integer r : marks) {
            ar += r;
        }
        return ar / marks.size();
    }

    public String markList(){
        return marks.toString();
    }
}
