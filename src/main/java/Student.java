import org.json.JSONObject;

import java.util.ArrayList;

public class Student {
    private int id;
    private String fio;
    private Group group;
    protected ArrayList<Integer> marks = new ArrayList<>();

    public Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public Group getGroup() {
        return group;
    }

    protected void enrolltoGroup(Group group) {
        group.studentsArray.add(this);
        this.group = group;
        System.out.println("The student " + getFio() + " is enrolled to group " + group);
    }

    public ArrayList<Integer> getMark() {
        return marks;
    }

    protected ArrayList<Integer> addMark(int mark) {
        if (mark > 0 && mark <= 5) {
            marks.add(mark);
        }
        return marks;
    }

    protected int calculateAverageMark() {
        int sum = 0;
        for (int i = 0; i < marks.size(); i++) {
            sum += marks.get(i);
        }
        if (marks.size() == 0) {
            return 0;
        } else {
            return sum / marks.size();
        }
    }

    public JSONObject getJsonObject(){
        JSONObject js = new JSONObject();
        js.put("name", fio);
        return js;
    }

    public String toString(){
        return "Student(id=" + id + "; fio='" +fio +"')";
    }
}
