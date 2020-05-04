import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Test;

public class StudentTest {

    @org.junit.Test
    public void addMarks1() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        ArrayList<MARKS> testMarkList = new ArrayList(Arrays.asList(MARKS.PERFECTLY,MARKS.BADLY));
        testStudent1.addMarks("1234", MARKS.PERFECTLY, MARKS.BADLY);
        assertEquals(testMarkList,testStudent1.getMarks());
        testMarkList.add(MARKS.WELL);
        testStudent1.addMarks("1234","WELL");
        assertEquals(testMarkList,testStudent1.getMarks());
    }

    @org.junit.Test
    public void AddMarks2() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        ArrayList<MARKS> testMarkList = new ArrayList<>(Arrays.asList(MARKS.PERFECTLY,MARKS.BADLY));
        testStudent1.addMarks("1234", 5, 1);
        assertEquals(testMarkList,testStudent1.getMarks());
        testMarkList.add(MARKS.WELL);
        testStudent1.addMarks("1234",MARKS.WELL);
        assertEquals(testMarkList,testStudent1.getMarks());
    }

    @org.junit.Test
    public void AddMarks3() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        ArrayList<MARKS> testMarkList = new ArrayList<>(Arrays.asList(MARKS.PERFECTLY,MARKS.BADLY));
        testStudent1.addMarks("1234", "PERFECTLY", "BADLY");
        assertEquals(testMarkList,testStudent1.getMarks());
        testMarkList.add(MARKS.WELL);
        testStudent1.addMarks("1234",4);
        assertEquals(testMarkList,testStudent1.getMarks());
    }

    @org.junit.Test
    public void getMiddleMark1() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        testStudent1.addMarks("1234", 5, 1);
        assertEquals(MARKS.SATISFACTORILY, testStudent1.getMiddleMark());
    }

    @org.junit.Test
    public void getMiddleMark2() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        assertEquals(null, testStudent1.getMiddleMark());
    }

    @org.junit.Test
    public void testEquals() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent3 = Student.createNewStudent(33333,"Ivanov Ivan", "1234");
        Student testStudent4 = Student.createNewStudent(777,"Petrov Pashka", "1234");
        assertFalse(testStudent1.equals("Ivanov Ivan"));
        assertTrue(testStudent1.equals(testStudent2));
        assertFalse(testStudent1.equals(testStudent3));
        assertFalse(testStudent1.equals(testStudent4));
    }

    @org.junit.Test
    public void equalID() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        assertTrue(testStudent1.equalID(777));
        assertFalse(testStudent1.equalID(33333));
    }

    @org.junit.Test
    public void equalFIO() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        assertTrue(testStudent1.equalFIO("Ivanov Ivan"));
        assertFalse(testStudent1.equalFIO("Petrov Pashka"));
    }

    @org.junit.Test
    public void findStudentinArrayList1() {
        Student testStudent1 = Student.createNewStudent(33333,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(777,"Petrov Pashka", "1234");
        ArrayList<Student> testStudentList = new ArrayList<>(Arrays.asList(testStudent1,testStudent2));
        assertEquals(testStudent1,Student.findStudentinArrayList(testStudentList,33333));
        assertEquals(testStudent2,Student.findStudentinArrayList(testStudentList,"Petrov Pashka"));
    }
}
