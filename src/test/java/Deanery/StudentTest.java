package Deanery;

import static org.junit.Assert.assertEquals;

public class StudentTest {

    Student student = new Student(123, "Шейнберг", "Виктор", "Квазимодович");
    Student student1 = new Student(124, "Шейнберг", "Виктор", "Квазимодович");
    Group group = new Group(1,"TestGroup");

    @org.junit.Test
    public void testToString() {
        assertEquals("Шейнберг Виктор Квазимодович",student.toString());

    }

    @org.junit.Test
    public void changeGroup() {
        student.changeGroup(group);
        assertEquals(group, student.getGroup());
    }

    @org.junit.Test
    public void averageMark() {
        student.addMark(5);
        student.addMark(4);
        assertEquals(4.5,student.averageMark(), 0.1);
    }

    @org.junit.Test
    public void setAsHead() {
        student.setAsHead();
        assert(student.isTheHeadman());
    }

    @org.junit.Test
    public void getMarks() {
        student.addMark(5);
        student.addMark(4);
        assertEquals("[5, 4]",student.getMarks().toString());
    }

    @org.junit.Test
    public void getFIO() {
        assertEquals("Шейнберг Виктор Квазимодович",student.getFIO());
    }

    @org.junit.Test
    public void getId() {
        assertEquals(123,student.getId());
    }

    @org.junit.Test
    public void getSurname() {
        assertEquals("Шейнберг",student.getSurname());
    }

    @org.junit.Test
    public void getName() {
        assertEquals("Виктор",student.getName());
    }

    @org.junit.Test
    public void getMiddle_name() {
        assertEquals("Квазимодович",student.getMiddle_name());
    }

    @org.junit.Test
    public void compareTo() {
        assertEquals(0,student.compareTo(student1));
    }
}