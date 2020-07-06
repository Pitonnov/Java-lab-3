import java.util.ArrayList;

public class Student {
    private int id;
    private String fio;
    protected Group group;
    protected ArrayList<Integer> marks = new ArrayList<Integer>();

    Student(int id, String name){
        this.id = id;
        this.fio = name;
    }

    public void addGroup(Group group){
        this.group = group;
    }

    public void addMark(int mark){
        this.marks.add(mark);
    }

    public double avgMark(){
        float sum = 0;
        for (Integer el: marks){
            sum += el;
        }
        return sum / marks.size();
    }

    public String getFio(){
        return this.fio;
    }

    public int getId(){
        return this.id;
    }

}
