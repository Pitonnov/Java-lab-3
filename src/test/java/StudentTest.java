import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void getPersonalData(){
        Student student = new Student(1, "Kirill Mironov");
        assertEquals(1, student.getId());
        assertEquals("Kirill Mironov", student.getFio());
    }

    @Test
    public void addMark(){
        Student student = new Student(1, "Kirill Mironov");
        student.addMark(3);
        student.addMark(4);
        student.addMark(5);
        student.addMark(2);
        ArrayList<Integer> expectedMarks = new ArrayList<Integer>();
        expectedMarks.add(3);
        expectedMarks.add(4);
        expectedMarks.add(5);
        expectedMarks.add(2);
        assertEquals(expectedMarks, student.marks);
    }

    @Test
    public void avgMark(){
        Student student = new Student(1, "Kirill Mironov");
        student.addMark(2);
        student.addMark(4);
        student.addMark(5);
        student.addMark(2);
        assertEquals(3.25, student.avgMark(), 0.001);
    }


}
