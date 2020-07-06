package Deanery;

import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {

    Student student1 = new Student(123, "Шейнберг", "Виктор", "Квазимодович");
    Student student2 = new Student(124, "Вилдерман", "Виктор", "Квазимодович");
    Group group1 = new Group(1,"TestGroup1");
    Group group2 = new Group(2,"TestGroup2");


    @Test
    public void getHeadId() {
        group1.addStud(student1);
        group1.electionHead();
        assertEquals(123, group1.getHeadId());
    }

    @Test
    public void testToString() {
        assertEquals("TestGroup1", group1.toString());
    }

    @Test
    public void expelStudent() {
        group2.addStud(student2);
        group2.expelStudent(student2);
        assertEquals("[]", group2.getStudents().toString());
    }

    @Test
    public void changeTitle() {
        group1.changeTitle("TestGroup3");
        assertEquals("TestGroup3", group1.toString());
    }

    @Test
    public void addStud() {
        group1.addStud(student1);
        assertEquals("[Шейнберг Виктор Квазимодович]", group1.getStudents().toString());
    }

    @Test
    public void electionHead() {
        group1.addStud(student1);
        group1.electionHead();
        assert(student1.isTheHeadman());
    }

    @Test
    public void averageMark() {
        group1.addStud(student1);
        group1.addStud(student2);
        student1.addMark(5);
        student2.addMark(4);
        assertEquals(4.5, group1.averageMark(), 0.1);
    }

    @Test
    public void getTitle() {
        assertEquals("TestGroup1", group1.toString());
    }

    @Test
    public void numberOfStudents() {
        group1.addStud(student1);
        group1.addStud(student2);
        assertEquals(2, group1.numberOfStudents());
    }


    @Test
    public void getHead() {
        group1.addStud(student1);
        group1.electionHead();
        assertEquals(student1, group1.getHead());
    }

    @Test
    public void getId() {
        assertEquals(1, group1.getId());
    }

    @Test
    public void findStudent() {
        group1.addStud(student1);
        group1.addStud(student2);
        assertEquals(student1, group1.findStudent(123));
    }

    @Test
    public void testFindStudent() {
        group1.addStud(student1);
        group1.addStud(student2);
        assertEquals(student2, group1.findStudent("Вилдерман"));
    }

    @Test
    public void compareTo() {
        assertEquals(0,group1.compareTo(group1));
    }
}