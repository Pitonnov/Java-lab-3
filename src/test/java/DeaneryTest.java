import org.junit.Test;

import static org.junit.Assert.*;

public class DeaneryTest {

    @Test
    public void headInitialize(){
        Deanery deanery = new Deanery();
        Group group1 = new Group("TestGroup1");
        Group group2 = new Group("TestGroup2");
        Group group3 = new Group("TestGroup3");
        Student student1 = new Student(1, "Kirill Mironov");
        Student exp1 = student1;
        Student student2 = new Student(2, "Saveliy Morozov");
        Student exp2 = student2;
        Student student3 = new Student(3, "Anton Talinov");
        Student exp3 = student3;
        group1.addStudent(student1);
        group2.addStudent(student2);
        group3.addStudent(student3);
        deanery.groups.add(group1);
        deanery.groups.add(group2);
        deanery.groups.add(group3);
        deanery.headInitialize();
        assertEquals(exp1, group1.getHead());
        assertEquals(exp2, group2.getHead());
        assertEquals(exp3, group3.getHead());
    }

    @Test
    public void addMarksForAll(){
        Deanery deanery = new Deanery();
        Student student1 = new Student(1, "Kirill Mironov");
        Student student2 = new Student(2, "Saveliy Morozov");
        Student student3 = new Student(3, "Anton Talinov");
        deanery.students.add(student1);
        deanery.students.add(student2);
        deanery.students.add(student3);
        deanery.addMarksForAll();
        assertNotNull(student1.marks);
        assertNotNull(student2.marks);
        assertNotNull(student3.marks);
    }

    @Test
    public void transferStudent(){
        Deanery deanery = new Deanery();
        Group group1 = new Group("TestGroup1");
        Group group2 = new Group("TestGroup2");
        Group group3 = new Group("TestGroup3");
        Student student1 = new Student(1, "Kirill Mironov");
        Student student2 = new Student(2, "Saveliy Morozov");
        Student student3 = new Student(3, "Anton Talinov");
        group1.addStudent(student1);
        group2.addStudent(student2);
        group3.addStudent(student3);
        deanery.groups.add(group1);
        deanery.groups.add(group2);
        deanery.groups.add(group3);
        deanery.transferStudent(3, "TestGroup2");
        assertEquals(1, deanery.groups.get(0).students.size());
        assertEquals(2, deanery.groups.get(1).students.size());
        assertEquals(0, deanery.groups.get(2).students.size());
    }
}
