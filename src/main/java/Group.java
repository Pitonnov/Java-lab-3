import java.util.ArrayList;

public class Group {
    private String title;
    private ArrayList <Student> students = new ArrayList<Student>();
    private Student head;

    public Group(String title){
        this.title = title;
    }
    public void setHead(Student head){
        this.head = head;
    }
    public String getTitle() {
        return title;
    }
    public Student getHead() {
        return head;
    }
    public void addStudent(Student student){
        this.students.add(student);
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof Group))
            return false;
        Group group = (Group) obj;
        return (title.equals(group.title) && students.equals(group.students));
    }
    public int hashcode(){
        int hash = 37;
        hash = hash*17 + title.hashCode();
        hash = hash*17 + students.hashCode();
        hash = hash*17 + head.hashCode();
        return hash;
    }
    class NoStudentException extends Exception{}
    public Student findStudent(int id) throws NoStudentException{
        Student foundStudent = null;
        for(Student student:students){
            int studentId = student.getId();
            if(studentId == id)
                foundStudent = student;
        }
        if (foundStudent!=null)
            return foundStudent;
        else throw new NoStudentException();
    }
    public Student findStudent(String fio) throws NoStudentException{
        Student foundStudent = null;
        for(Student student:students){
            String studentFio = student.getFio();
            if(studentFio.equals(fio))
                foundStudent = student;
        }
        if (foundStudent!=null)
            return foundStudent;
        else throw new NoStudentException();
    }
    public int averageMark (){
        int totalMark=0;
        for(Student student:students){
            totalMark+=student.averageMark();
        }
        double averageMark = (double) totalMark/(double) students.size();
        int result = (int) Math.round(averageMark);
        return result;
    }
    public void expellStudent (Student student){
        students.remove(student);
    }
}
