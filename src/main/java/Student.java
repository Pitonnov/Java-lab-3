import java.util.ArrayList;

public class Student {
    protected ArrayList<Integer> marks = new ArrayList();
    private int id;
    private String fio;
    private Group studentGroup;

    public Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public void addMark(int mark) {
        this.marks.add(mark);
    }

    public float averageMark() {
        int sum = 0;
        for (Integer m : this.marks) {
            sum += m;
        }
        return (float) sum / this.marks.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Group getGroup() {
        return studentGroup;
    }

    public void setGroup(Group group) {
        this.studentGroup = group;
    }


}
