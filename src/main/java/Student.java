import java.util.ArrayList;
import java.util.Date;

public class Student {
    private Integer id;
    private String fio;
    private Group group;
    //private ArrayList<Integer> marks;
    private ArrayList<Marks> marks;

    public Student(Integer id, String fio){
        this.id = id;
        this.fio = fio;
        this.marks = new ArrayList<Marks>();
    }

    @Override
    public int hashCode() {
        return id.hashCode() + fio.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Student)){
            return false;
        }
        Student student = (Student)obj;
        return (this.id == student.getId() && this.fio.equals(student.getFio()) && this.group.getTitle().equals(student.group.getTitle()));
    }

    public ArrayList<Marks> getMarks(){
        return this.marks;
    }

    public String getFio(){
        return this.fio;
    }

    public Group getGroup(){
        return this.group;
    }

    public Integer getId(){
        return this.id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void addMark(Integer value){
        Marks mark = new Marks(this, new Date(), value); //текущая дата
        this.marks.add(mark);
    }

    public void addMark(Integer value, Date date){
        Marks mark = new Marks(this, date, value); //текущая дата
        this.marks.add(mark);
    }

    public float getAverageMark(){
        //средняя оценка по всем группам за любой период
        float sum = 0;
        for(Marks mark : marks){
            sum += mark.getMark();
        }
        return sum/marks.size();
    }

    public float getAverageMark(Group group){
        float sum = 0;
        int count = 0;
        for(Marks mark : marks) {
            if (mark.getGroup().equals(group)) {
                sum += mark.getMark();
                count++;
            }
        }
        return sum/count;
    }

    public float getAverageMark(Group group, Date begin, Date end){
        float sum = 0;
        int count = 0;
        for(Marks mark : marks) {
            Date current = mark.getDate();
            if (mark.getGroup().equals(group) && begin.before(current) && end.after(current)) {
                sum += mark.getMark();
                count++;
            }
        }
        return sum/count;
    }

    public float getAverageMark(Date begin, Date end){
        float sum = 0;
        int count = 0;
        for(Marks mark : marks) {
            Date current = mark.getDate();
            if (begin.before(current) && end.after(current)) {
                sum += mark.getMark();
                count++;
            }
        }
        return sum/count;
    }
}
