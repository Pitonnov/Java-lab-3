package Deanery;

import java.util.ArrayList;

public class Group {
//fields
    private String title;
    private ArrayList<Student> students;
    private Student head;
//constructor
    private Group(String title){
        this.title = title;
        this.students = new ArrayList<>();
        this.head = null;
    }
//getters
    public String getTitle(){return this.title;}
    public ArrayList<Student> getStudents(){return this.students;}
    public Student getHead(){ return this.head; }
//methods
//1. create new group (factory)
    public static Group createNewGroup(String title, String password){
        try {
            Password.checkPassword(password);
            return new Group(title);
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
            return null;
        }
    }
//2. add student
    public void addStudent(Student student, String password) {
        try {
            Password.checkPassword(password);
            this.students.add(student);
            student.setGroup(this);
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
//3. choose the head
    public void chooseHead(Student student, String password)
            throws DeaneryExceptions.GroupDoesntContainStudentException{
        try {
            Password.checkPassword(password);
            if (!(this.students.contains(student))){
                throw new DeaneryExceptions.GroupDoesntContainStudentException();
            }
            this.head = student;
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
    public void chooseHead(int headId, String password){
        try {
            Password.checkPassword(password);
            if (Student.findStudentinArrayList(this.students, headId) == null) {
                System.out.println("Student "+headId+" is not found in "+this.getTitle());
            }
            else{
                this.head = Student.findStudentinArrayList(this.students, headId);
            }
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
    public void chooseHead(String headFio, String password){
        try {
            Password.checkPassword(password);
            if (Student.findStudentinArrayList(this.students, headFio) == null) {
                System.out.println("Student "+headFio+" is not found in "+this.getTitle());
            }
            else{
                this.head = Student.findStudentinArrayList(this.students, headFio);
            }
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
//4. find student in the group
    public Student findStudent(int id) throws DeaneryExceptions.GroupDoesntContainStudentException{
        if(Student.findStudentinArrayList(this.students, id) == null)
            throw new DeaneryExceptions.GroupDoesntContainStudentException();
        return Student.findStudentinArrayList(this.students, id);
    }
    public Student findStudent(String fio) throws DeaneryExceptions.GroupDoesntContainStudentException{
        if(Student.findStudentinArrayList(this.students, fio) == null)
            throw new DeaneryExceptions.GroupDoesntContainStudentException();
        return Student.findStudentinArrayList(this.students, fio);
    }
//5. getting the middle mark
    public MARKS getMiddleMark(){
        int sumMiddleMarkValue = 0;
        if (this.students.size() == 0)
            return null;
        for (Student student : this.students) {
            if (student.getMarks().size() == 0){
                System.out.println(student.getFio()+" has not marks. "+"Middle mark of "
                                   +this.getTitle()+" wasn't calculated");
                return null;
            }
            sumMiddleMarkValue += MARKS.MARKStoInt(student.getMiddleMark());
        }
        int middleMarkValue = (int) Math.round((double) sumMiddleMarkValue / this.students.size());
        return MARKS.intToMARKS(middleMarkValue);
    }
//6. remove student
    public void removeStudent(Student student, String password){
        try {
            Password.checkPassword(password);
            student.setGroup(null);
            this.students.remove(student);
        } catch (DeaneryExceptions.PasswordException e) {
            Password.printAnswer();
        }
    }
}