import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    static int ID1 = 92;
    static String FIO1 = "Сердюк Вера Леонидовна";
    Student testStudent1 = new Student (ID1, FIO1);
    static int ID2 = 93;
    static String FIO2 = "Шарапов Марк Андреевич";
    Student testStudent2 = new Student (ID2, FIO2);
    static String TITLE = "PHP-1";
    Group testGroup = new Group(TITLE);
    ArrayList<Student> testArray = new ArrayList<Student>();

    @Test
    void addStudent() {
        assertEquals(testGroup.studentsArray, testGroup.addStudent(testStudent1));
        System.out.println(testStudent1);
        System.out.println(testGroup.studentsArray);
    }

    @Test
    void addStudent1() {
        assertEquals(testGroup.studentsArray, testGroup.addStudent(testStudent2));
        System.out.println(testStudent2);
        System.out.println(testGroup.studentsArray);
    }

    @Test
    void ellectHead() {
        System.out.println(testStudent1);
        assertEquals(testStudent1, testGroup.ellectHead(testStudent1));
        System.out.println(testGroup.getHead());
    }

    @Test
    void ellectHead1() {
        System.out.println(testStudent2);
        assertEquals(testStudent2, testGroup.ellectHead(testStudent2));
        System.out.println(testGroup.getHead());
    }

    @Test
    void calculateAverageGroupMark() {
        testStudent1.enrolltoGroup(testGroup);
        testStudent2.enrolltoGroup(testGroup);
        testStudent1.addMark(3);
        testStudent2.addMark(5);
        assertEquals(4, testGroup.calculateAverageGroupMark());
    }

    @Test
    void calculateAverageGroupMark1() {
        testStudent1.enrolltoGroup(testGroup);
        testStudent2.enrolltoGroup(testGroup);
        testStudent1.addMark(5);
        testStudent2.addMark(5);
        assertEquals(5, testGroup.calculateAverageGroupMark());
    }

    @Test
    void calculateAverageGroupMark2() {
        testStudent1.enrolltoGroup(testGroup);
        testStudent2.enrolltoGroup(testGroup);
        testStudent1.addMark(4);
        testStudent2.addMark(2);
        assertEquals(3, testGroup.calculateAverageGroupMark());
    }

    @Test
    void expellStudentFromGroup() {
        //Test with method class Group "addStudent"
        testGroup.addStudent(testStudent1);
        testGroup.addStudent(testStudent2);
        testGroup.expellStudentFromGroup(testStudent1);
        assertEquals(testGroup.studentsArray, testGroup.expellStudentFromGroup(testStudent1));
        System.out.println(testStudent2);
        System.out.println(testGroup.studentsArray);
    }

    @Test
    void expellStudentFromGroup1() {
        //Test with method class Student "enrolltoGroup"
        testStudent1.enrolltoGroup(testGroup);
        testStudent2.enrolltoGroup(testGroup);
        testGroup.expellStudentFromGroup(testStudent2);
        assertEquals(testGroup.studentsArray, testGroup.expellStudentFromGroup(testStudent2));
        System.out.println(testStudent1);
        System.out.println(testGroup.studentsArray);
    }
}