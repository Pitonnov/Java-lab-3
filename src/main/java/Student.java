import java.util.ArrayList;

public class Student {
    private int id;
    private String fio;
    private Group group;
    private ArrayList <Integer> marks = new ArrayList<Integer>();

    public Student(int id, String fio){
        this.id = id;
        this.fio = fio;
    }
    public void setGroup (Group group){
        this.group = group;
    }
    public Group getGroup() {
        return group;
    }
    public int getId(){
        return id;
    }
    public String getFio(){
        return fio;
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof Student))
            return false;
        Student student = (Student) obj;
        return id == student.id && fio.equals(student.fio) && group == student.group && marks.equals(student.marks);
    }
    public int hashcode(){
        int hash = 37;
        hash = hash*17 + id;
        hash = hash*17 + fio.hashCode();
        hash = hash*17 + group.hashCode();
        hash = hash*17 + marks.hashCode();
        return hash;
    }
    public void addMark(int mark){
        if(mark>=1 && mark<=5){
            this.marks.add(mark);
        }
    }
    public int averageMark(){
        int totalMark=0;
        for(int mark:marks){
            totalMark+=mark;
        }
        if (totalMark==0)
            return 0;
        else {
            double averageMark = (double) totalMark / (double) marks.size();
            int result = (int) Math.round(averageMark);
            return result;
        }
    }
    public void printMarks(){
        System.out.print("    marks: ");
        for(int i=0; i<marks.size();i++)
            System.out.format("%d ", marks.get(i));
        System.out.println();
    }

}
