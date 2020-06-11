//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import University.Deanery;
import University.Group;
import University.Student;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;



public class GroupsTests {
    ArrayList<Student> students;
    @BeforeAll
    public void prepare()
    {
        students=new ArrayList<>();
    }
    public void fillGroup(Group group)
    {
        for(int i=0;i<20;i++)
            group.addStudent(Deanery.createRandomStudent());
    }
    @Test
    public void enroll() {
        Group group=new Group("IS 27");
        Student student=new Student("Pavel","Petrov","Alberrtovich");
        group.addStudent(student);
        assertEquals(group,student.getGroup());
    }
    @Test
    public void findByName() {
        Group group=new Group("IS 27");
        fillGroup(group);
        Student student=new Student("Pavel","Petrov","Alberrtovich");
        group.addStudent(student);
        fillGroup(group);
        assertEquals(student,group.getStudentByKey("Pavel","",""));
    }
    @Test
    public void findBySurname() {
        Group group=new Group("IS 27");
        fillGroup(group);
        Student student=new Student("Pavel","Petrov","Alberrtovich");
        group.addStudent(student);
        fillGroup(group);
        assertEquals(student,group.getStudentByKey("","Petrov",""));
    }
    @Test
    public void findByPatronym() {
        Group group=new Group("IS 27");
        fillGroup(group);
        Student student=new Student("Pavel","Petrovich","Albertovich");
        group.addStudent(student);
        fillGroup(group);
        assertEquals(student,group.getStudentByKey("","","Albertovich"));
    }
    @Test
    public void findByFIO() {
        Group group=new Group("IS 27");
        fillGroup(group);
        Student student=new Student("Pavel","Petrovich","Albertovich");
        group.addStudent(student);
        fillGroup(group);
        assertEquals(student,group.getStudentByKey("Pavel","Petrovich","Albertovich"));
    }
    @Test
    public void findByID() {
        Group group=new Group("IS 27");
        fillGroup(group);
        Student student=new Student(10012,"Pavel","Petrovich","Albertovich");
        group.addStudent(student);
        fillGroup(group);
        assertEquals(student,group.getStudentByKey(10012));
    }
    @Test
    public void averageMark() {
        Group group=new Group("IS 27");
        float sum=0;
        for(int i=0;i<50;i++)
        {
            Student student=Deanery.createRandomStudent();
            group.addStudent(student);
            for(int j=0;j<100;j++) {
                float mark=2+ThreadLocalRandom.current().nextFloat() * (5 - 2);
                student.addMark(mark);
                sum+=mark;
            }
        }
        assertEquals(sum/(100*50),group.getAverageMark(),0.01);
    }
    @Test
    public void exclude() {
        Group group=new Group("IS 27");
        fillGroup(group);
        int studentId=10015;
        Student student=new Student(studentId,"Victor","Zaytsev","Ivanovich");
        group.addStudent(student);
        group.excludeStudent(studentId);
        assertEquals(null,group.getStudentByKey(studentId));
    }
}