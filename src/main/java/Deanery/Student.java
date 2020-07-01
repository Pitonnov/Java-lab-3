package Deanery;

import java.util.*;

public class Student implements Comparable<Student>{
    private int id;
    private int groupId;
    private ArrayList<Integer> marks= new ArrayList<>();
    private Group group = null;
    private Boolean headman = false;
    private String surname;
    private String name;
    private String middle_name;

    public Student(int id, String surname, String name, String middle_name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
    }

    @Override
    public String toString() {
        return surname+" "+name+" "+middle_name;
    }

    public void addMark (int mark){
        marks.add(mark);
    }

    public void changeGroup(Group gr){
        if (group != null){
            group.expelStudent(this);
            groupId = gr.getId();
        }
        headman = false;
        group = gr;
    }

    public double averageMark(){
        double average = 0;
        int j = 0;
        int temp = 0;
        for (int i: marks){
            average = average + i;
            j++;
        }
        temp =  (int) average*100 / j;
        average = (double)temp / 100;
        return average;
    }

    public void setAsHead(){
        headman = true;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public String getFIO() { return surname+" "+name+" "+middle_name; }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isTheHeadman(){return headman;    }

    @Override
    public int compareTo(Student st) {
        return toString().compareTo(st.toString());
    }
}
