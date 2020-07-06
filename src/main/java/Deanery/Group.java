package Deanery;

import java.util.ArrayList;
import java.util.Random;

public class Group implements Comparable<Group>{
    private String title;
    private ArrayList<Student> st= new ArrayList<Student>();;
    private Student head;
    private int headId;
    private int id;

    public int getHeadId() {
        return headId;
    }

    public Group(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public void expelStudent(Student student){
        if (head == student){
            head = null;
            electionHead();
        }
        st.remove(student);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void addStud(Student entrant) {
        st.add(entrant);
    }

    public void electionHead() {
        if (head == null) {
            Random rndm = new Random();
            int i = rndm.nextInt(st.size());
            head = st.get(i);
            headId = st.get(i).getId();
            st.get(i).setAsHead();
        }
        else {
            System.out.println("The coup failed, first you need to overthrow the current headman");
        }
    }

    public double averageMark(){
        double average = 0;
        for(Student student: st){
            average += student.averageMark();
        }
        int temp = (int)average*100/st.size();
        average = (double)temp/100;
        return average;
    }

    public String getTitle() {
        return title;
    }

    public int numberOfStudents(){
        return st.size();
    }

    public ArrayList<Student> getStudents() {
        return st;
    }

    public Student getHead() {
        return head;
    }

    public int getId() {
        return id;
    }

    public Student findStudent(int id) {
        for (Student student: st){
            if (student.getId()== id){
                return student;
            }
        }
        return null;
    }

    public Student findStudent(String fio) {
        for (Student student: st){
            if (student.getFIO().equals(fio) || student.getSurname().equals(fio) || student.getName().equals(fio) || student.getMiddle_name().equals(fio)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Group gr) {
        return toString().compareTo(gr.toString());
    }
}
