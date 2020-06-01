import java.util.ArrayList;

public class Group {
    private String title;
    private ArrayList<Student> students;
    private Student head;

    public Group(String title){
        this.title = title;
        this.students = new ArrayList<Student>();
        this.head = null;

    }

    @Override
    public int hashCode() {
        return title.hashCode() + head.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Group)){
            return false;
        }
        Group group = (Group)obj;
        //return (this.title.equals(group.getTitle()) && this.head.equals(group.getHead()));
        return (this.title.equals(group.getTitle()));
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void setHead(Student head){
        this.head = head;
    }

    public Student getHead(){
        return this.head;
    }

    public String getTitle(){
        return this.title;
    }

    public Student getStudent(String fio){
        for(Student student : students) {
            if (student.getFio().equals(fio)) {
                return student;
            }
        }
        return null;
    }

    public Student getStudent(Integer id){
        for(Student student : students){
            if(student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public float getAverageMark(){
        Float sumMarks = 0.0f;
        for(Student student : students){
            sumMarks += student.getAverageMark();
        }
        return sumMarks/students.size();
    }

    public void studentExclusion(String fio){
        int remove = 0;
        for(int i = 0; i<students.size(); i++){
            if(students.get(i).getFio().equals(fio)){
                remove = i;
                break;
            }
        }
        students.remove(remove);
    }

    public void studentExclusion(Integer id){
        int remove = 0;
        for(int i = 0; i<students.size(); i++){
            if(students.get(i).getId() == id){
                remove = i;
                break;
            }
        }
        students.remove(remove);
    }


}
