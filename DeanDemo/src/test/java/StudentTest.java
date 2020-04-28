import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    static int ID = 91;
    static String FIO = "Джавин Иван Андреевич";
    Student testStudent = new Student (ID, FIO);

    @Test
    public void getId() {
        assertEquals(ID, testStudent.getId());
    }

    @Test
    void getFio() {
        assertEquals(FIO, testStudent.getFio());
    }

    @Test
    void addMark() {
        testStudent.addMark(4);
        ArrayList<Integer> marks = new ArrayList<Integer>();
        marks.add(4);
        assertEquals(marks,  testStudent.getMark());
        testStudent.addMark(5);
        marks.add(5);
        assertEquals(marks,  testStudent.getMark());
    }

    @Test
    void addMarkInvalidValues() {
        testStudent.addMark(3);
        ArrayList<Integer> marks = new ArrayList<Integer>();
        marks.add(3);
        assertEquals(marks,  testStudent.getMark());
        testStudent.addMark(6);
        assertEquals(marks,  testStudent.getMark());
        testStudent.addMark(0);
        assertEquals(marks,  testStudent.getMark());
    }

    @Test
    void calculateAverageMark() {
        testStudent.addMark(3);
        assertEquals(3, testStudent.calculateAverageMark());
        testStudent.addMark(5);
        assertEquals(4, testStudent.calculateAverageMark());
    }
}