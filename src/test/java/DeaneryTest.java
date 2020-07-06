import org.junit.Test;

import static org.junit.Assert.*;

public class DeaneryTest {

    @Test
    public void createGroups() {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try {
            Group group = deanery.findGroup("Java");
            assertEquals("Java", group.getTitle());
        } catch (Deanery.NoGroupException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createStudents() {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try {
            Student student = deanery.findGroup("Java").findStudent("Грефа Маргарита Федоровна");
            assertEquals("Грефа Маргарита Федоровна", student.getFio());
        } catch (Deanery.NoGroupException e) {
            e.printStackTrace();
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findGroup() {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try {
            Group foundGroup = deanery.findGroup("C++");
            boolean ifTrue = foundGroup.getTitle().equals("C++");
        } catch (Deanery.NoGroupException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findGroupException() {
        Deanery deanery = new Deanery();
        Group group1 = new Group("Java");
        Group group2 = new Group("C++");
        Group group3 = new Group("Python");
        try {
            Group foundGroup = deanery.findGroup("C");
        } catch (Deanery.NoGroupException e) {
            boolean exceptionThrown=true;
            assertTrue(exceptionThrown);
        }
    }

    @Test
    public void changeGroupId() {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try {
            Student student = deanery.findGroup("Java").findStudent(1);
            deanery.changeGroup(1,"C++");
            assertEquals("C++", student.getGroup().getTitle());
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        } catch (Deanery.NoGroupException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void changeGroupFio() {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try {
            Student student = deanery.findGroup("Java").findStudent("Грефа Маргарита Федоровна");
            deanery.changeGroup("Грефа Маргарита Федоровна","C++");
            assertEquals("C++", student.getGroup().getTitle());
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        } catch (Deanery.NoGroupException e) {
            e.printStackTrace();
        }
    }
}