import org.json.JSONObject;

import java.util.ArrayList;

import static javax.swing.UIManager.get;

public class Group {
    protected ArrayList<Student> studentsArray = new ArrayList<Student>();
    private String title;
    private Student head;

    public Group(String title) {
        this.title = title;
    }

    public ArrayList addStudent(Student student) {
        student.enrolltoGroup(this);
        return studentsArray;
    }

    public Student ellectHead(Student student) {
        head = student;
        return head;
    }

    public Student getHead() {
        return head;
    }

    public void findStudentByFio(String fio) {
        for (int i = 0; i < studentsArray.size(); i++) {
            Student student = studentsArray.get(i);
            if (student.getFio() == studentsArray.get(i).getFio()) {
                System.out.println(student.getFio());
                System.out.println("id" +student.getId());
                System.out.println(student.getGroup());
                System.out.println("Marks " + student.getMark());
              break;
            } else {
                System.out.println("The student " + fio + " doesn`t excist!");
            }
        }
    }

    public void findStudentById(int id) {
        for (int i = 0; i < studentsArray.size(); i++) {
            Student student = studentsArray.get(i);
            if (student.getId() == studentsArray.get(i).getId()) {
                System.out.println(student.getFio());
                System.out.println("id" + student.getId());
                System.out.println(student.getGroup());
                System.out.println("Marks " + student.getMark());
                break;
            } else {
                System.out.println("The student whith id " + id + " doesn`t excist!");
            }
        }
    }

    protected int calculateAverageGroupMark() {
        int sum = 0;
        ArrayList<Integer> groupsMarks = new ArrayList<>();
        for (int j = 0; j < studentsArray.size(); j++) {
            groupsMarks.add(studentsArray.get(j).calculateAverageMark());
        }
        for (int i = 0; i < groupsMarks.size(); i++) {
            sum += groupsMarks.get(i);
        }
        if (groupsMarks.size() == 0) {
            return 0;
        } else {
            return sum / groupsMarks.size();
        }
    }

    protected ArrayList<Student> expellStudentFromGroup(Student student) {
            student.getGroup();
            if (studentsArray.contains(student)) {
                studentsArray.remove(student);
                System.out.println("The student " + student.getFio() + " is expelled from group " + getTitle());
            } else {
                System.out.println("There isn`t such student in this group");
            }
        return studentsArray;
    }

    public ArrayList<Student> getStudentsArray(){
        return studentsArray;
    }

    public String getTitle(){
        return title;
    }

    public String toString(){
        return "Group(" + title +")";
    }
}