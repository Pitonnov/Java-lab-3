import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GroupTest {

    @Test
    public void getHead(){
        Group group = new Group("TestGroup");
        Student student = new Student(1, "Kirill Mironov");
        group.addStudent(student);
        group.chooseHead();
        assertEquals(student, group.getHead());
    }

    @Test
    public void avgMarkInGroup(){
        Group group = new Group("TestGroup");
        Student student1 = new Student(1, "Kirill Mironov");
        Student student2 = new Student(2, "Alex Brown");
        group.addStudent(student1);
        group.addStudent(student2);
        student1.addMark(3);
        student1.addMark(3);
        student1.addMark(3);
        student2.addMark(5);
        student2.addMark(5);
        student2.addMark(5);
        assertEquals(4.0, group.avgMarkInGroup(), 0.001);
    }

    @Test
    public void removeStudent(){
        Group group = new Group("TestGroup");
        Student student1 = new Student(1, "Kirill Mironov");
        Student student2 = new Student(2, "Alex Brown");
        group.addStudent(student1);
        group.addStudent(student2);
        group.removeStudent(student2);
        ArrayList<Student> expectedStudents = new ArrayList<Student>();
        expectedStudents.add(student1);
        assertEquals(1, group.students.size());
        assertEquals(expectedStudents, group.students);
    }
}
