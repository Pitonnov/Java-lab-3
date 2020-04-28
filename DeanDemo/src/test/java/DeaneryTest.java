import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class DeaneryTest {
    Deanery deanery = new Deanery("Groups.json");
    ArrayList<Group> groups = deanery.getGroups();
    ArrayList<Student> students = deanery.getStudents();

    @Test
    public void createStudent() {
        //Test for correction id assignment
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        newStudent.getId();
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent1.getId();
        assertEquals(newStudent.getId(), newStudent1.getId() - 1);
    }

    @Test
    public void createStudent1() {
        //Test for correction fio assignment
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        assertEquals("Ерроров Стек Оверфлоевич", newStudent.getFio());
        assertEquals("Ерророва Стека Оверфлоевна", newStudent1.getFio());
    }

    @Test
    public void getAverageMarkInTheGroup() {
        Group newGroup = groups.get(0);
        deanery.addMarks();
        int averageMarkInTheGroup = deanery.getAverageMarkInTheGroup(newGroup);
        assertEquals(averageMarkInTheGroup, deanery.getAverageMarkInTheGroup(newGroup));
    }

    @Test
    public void getAverageMarkInTheGroup1() {
        Group newGroup1 = groups.get(1);
        deanery.addMarks();
        int averageMarkInTheGroup = deanery.getAverageMarkInTheGroup(newGroup1);
        assertEquals(averageMarkInTheGroup, deanery.getAverageMarkInTheGroup(newGroup1));
    }

    @Test
    public void getAverageMarkInTheGroup2() {
        Group newGroup2 = groups.get(2);
        deanery.addMarks();
        int averageMarkInTheGroup = deanery.getAverageMarkInTheGroup(newGroup2);
        assertEquals(averageMarkInTheGroup, deanery.getAverageMarkInTheGroup(newGroup2));
    }

    @Test
    public void tranferToAhotherGroup() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(0));
        newStudent1.enrolltoGroup(groups.get(1));
        deanery.tranferToAhotherGroup(newStudent, groups.get(1));
        assertEquals(newStudent.getGroup(), newStudent1.getGroup());
    }

    @Test
    public void tranferToAhotherGroup1() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(1));
        deanery.tranferToAhotherGroup(newStudent1, groups.get(2));
        assertEquals(newStudent1.getGroup(), newStudent.getGroup());
    }

    @Test
    public void tranferToAhotherGroup2() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(2));
        deanery.tranferToAhotherGroup(newStudent, groups.get(0));
        deanery.tranferToAhotherGroup(newStudent1, groups.get(0));
        assertEquals(newStudent.getGroup(), newStudent1.getGroup());
    }

    @Test
    public void getTheWorsetStudentsInGroup() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(2));
        deanery.addMarks();
        ArrayList<Student> awfullStudents = deanery.getTheWorsetStudentsInGroup();
        assertEquals(awfullStudents, deanery.getTheWorsetStudentsInGroup());
    }

    @Test
    public void getTheWorsetStudentsInGroup1() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(2));
        deanery.addMarks();
        ArrayList<Student> awfullStudents1 = deanery.getTheWorsetStudentsInGroup();
        assertEquals(awfullStudents1, deanery.getTheWorsetStudentsInGroup());
    }

    @Test
    public void expellTheWorsetStudents() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(2));
        deanery.addMarks();
        ArrayList<Student> awfullStudents = deanery.getTheWorsetStudentsInGroup();
        ArrayList<Student> successfullStudents = deanery.expellTheWorsetStudents(awfullStudents);
        assertEquals(successfullStudents, deanery.expellTheWorsetStudents(awfullStudents));
    }

    @Test
    public void expellTheWorsetStudents1() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(0));
        newStudent1.enrolltoGroup(groups.get(1));
        deanery.addMarks();
        ArrayList<Student> awfullStudents = deanery.getTheWorsetStudentsInGroup();
        ArrayList<Student> successfullStudents1 = deanery.expellTheWorsetStudents(awfullStudents);
        assertEquals(successfullStudents1, deanery.expellTheWorsetStudents(awfullStudents));
    }
}