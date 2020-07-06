package Deanery;

import java.util.ArrayList;

public class Student {
//fields
    private int id;
    private String fio;
    private Group group;
    private ArrayList<MARKS> marks;
//constructor
    private Student(int id, String fio){
        this.id = id;
        this.fio = fio;
        this.group = null;
        this.marks = new ArrayList<>();
    }
//getters
    public int getId(){return this.id;}
    public String getFio(){return this.fio;}
    public Group getGroup(){return this.group;}
    public ArrayList<MARKS> getMarks(){return this.marks;}
//setters
    public void setGroup(Group group) { this.group = group; }
//methods
//1. create new student (factory)
    public static Student createNewStudent(int id, String fio, String password){
        try {
            Password.checkPassword(password);
            return new Student(id,fio);
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
            return null;
        }
    }
//2. add marks
    public void addMarks(String password, MARKS ... marks){
        try {
            Password.checkPassword(password);
            for (MARKS mark : marks) {
                this.marks.add(mark);
            }
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
    public void addMarks(String password, int ... markValues){
        try {
            Password.checkPassword(password);
            for (int markValue : markValues) {
                this.marks.add(MARKS.intToMARKS(markValue));
            }
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
    public void addMarks(String password, String ... markNames){
        try {
            Password.checkPassword(password);
            for (String markName : markNames) {
                this.marks.add(MARKS.valueOf(markName));
            }
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
//3. getting the middle mark
    public MARKS getMiddleMark() {
            if(this.getMarks().size() == 0){
                System.out.println(this.getFio()+" has not marks");
                return null;
            }
            int sumMiddleMarkStudent = 0;
            for (MARKS mark : this.marks) {
                sumMiddleMarkStudent += MARKS.MARKStoInt(mark);
            }
            int middleMarkValue = (int) Math.round((double) sumMiddleMarkStudent / this.marks.size());
            return MARKS.intToMARKS(middleMarkValue);
    }
//others
// equals
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Student)){
            return  false;}
        Student student = (Student)obj;
        return fio.equals(student.fio) && id==student.id;
    }
    public boolean equalID(int id){
        return this.id==id;
    }
    public boolean equalFIO(String fio){
        return this.fio.equals(fio);
    }
// find student in ArrayList by FIO or ID
    public static Student findStudentinArrayList(ArrayList<Student> arrayList, String fio){
        Student resultStudent = null;
        for (Student student : arrayList) {
            if(student.equalFIO(fio)){
                resultStudent = student;
                break;
            }
        }
        return resultStudent;
    }
    public static Student findStudentinArrayList(ArrayList<Student> arrayList, int id){
        Student resultStudent = null;
        for (Student student : arrayList) {
            if(student.equalID(id)){
                resultStudent = student;
                break;
            }
        }
        return resultStudent;
    }
}