import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {
    private Group group = new Group("GroupOfBlood");
    private Student student = new Student(1,"Gandzha Denis Sergeevich");

    @Test
    public void addStudentToGroup() {
        group.addStudentToGroup(student);
        assertEquals("Gandzha Denis Sergeevich",group.studentsInGroup.get(0).getFio());
        assertEquals(1,group.studentsInGroup.get(0).getId());
    }

    @Test
    public void findStudentById(){
        Student student = new Student(3,"A");
        Student student1 = new Student(4,"B");
        group.addStudentToGroup(student);
        group.addStudentToGroup(student1);
        assertEquals(3,group.findStudentById(3).getId());
        assertEquals(4,group.findStudentById(4).getId());
    }

    @Test
    public void findStudentByFio(){
        Student student = new Student(3,"A");
        Student student1 = new Student(4,"B");
        group.addStudentToGroup(student);
        group.addStudentToGroup(student1);
        assertEquals("A",group.findStudentByFio("A").getFio());
        assertEquals("B",group.findStudentByFio("B").getFio());
    }

    @Test
    public void deleteStudentFromGroup(){
        Student student = new Student(1, "Gandzha");
        Student student1 = new Student(2, "Martynov");
        Student student2 = new Student(3, "Petrov");
        Student student3 = new Student(4, "Pivovar");
        group.addStudentToGroup(student);
        group.addStudentToGroup(student1);
        group.addStudentToGroup(student2);
        group.addStudentToGroup(student3);
        group.deleteStudentFromGroup(student1);
        group.deleteStudentFromGroup(student2);
        assertEquals(2,group.studentsInGroup.size());
    }
}