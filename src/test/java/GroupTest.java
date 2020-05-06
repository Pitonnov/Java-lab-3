import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void testEquals() {
        Group group1 = new Group("Java");
        Group group2 = new Group("Java");
        boolean ifTrue = group1.equals(group2);
        assertTrue(ifTrue);
    }
    @Test
    public void testNotEquals() {
        Group group1 = new Group("Java");
        Group group2 = new Group("C++");
        boolean ifFalse = group1.equals(group2);
        assertFalse(ifFalse);
    }
    @Test
    public void findStudentId() {
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        Student student2 = new Student(2, "Ярина Людмила Василиевна");
        Group group = new Group("Java");
        group.addStudent(student1);
        group.addStudent(student2);
        try {
            Student foundStudent = group.findStudent(2);
            boolean ifTrue = foundStudent.equals(student2);
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void findStudentFio() {
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        Student student2 = new Student(2, "Ярина Людмила Василиевна");
        Group group = new Group("Java");
        group.addStudent(student1);
        group.addStudent(student2);
        try {
            Student foundStudent = group.findStudent("Ярина Людмила Василиевна");
            boolean ifTrue = foundStudent.equals(student2);
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testFindStudentIdException() {
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        Student student2 = new Student(2, "Ярина Людмила Василиевна");
        Group group = new Group("Java");
        group.addStudent(student1);
        group.addStudent(student2);
        try {
            Student foundStudent = group.findStudent(5);
        } catch (Group.NoStudentException e) {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }
    @Test
    public void testFindStudentFioException() {
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        Student student2 = new Student(2, "Ярина Людмила Василиевна");
        Group group = new Group("Java");
        group.addStudent(student1);
        group.addStudent(student2);
        try {
            Student foundStudent = group.findStudent("Ярина Л.В.");
        } catch (Group.NoStudentException e) {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }

    @Test
    public void averageMark() {
        Group group = new Group("Java");
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        student1.addMark(5);
        group.addStudent(student1);
        Student student2 = new Student(2, "Цуцкиха Ефросиния Романовна");
        student2.addMark(5);
        group.addStudent(student2);
        Student student3 = new Student(3, "Яскин Эмиль Эмилевич");
        student3.addMark(4);
        group.addStudent(student3);
        assertEquals(5, group.averageMark());
    }

    @Test
    public void expellStudent() {
        Group group = new Group("Java");
        Student student1 = new Student(1, "Баскакова Владлена Василиевна");
        group.addStudent(student1);
        Student student2 = new Student(2, "Цуцкиха Ефросиния Романовна");
        group.addStudent(student2);
        Student student3 = new Student(3, "Яскин Эмиль Эмилевич");
        group.addStudent(student3);
        group.expellStudent(student2);
        try {
            group.findStudent(2);
        } catch (Group.NoStudentException e) {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }
}