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
    public void transferToAnotherGroup() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(0));
        newStudent1.enrolltoGroup(groups.get(1));
        deanery.transferToAnotherGroup(newStudent, groups.get(1));
        assertEquals(newStudent.getGroup(), newStudent1.getGroup());
    }

    @Test
    public void transferToAnotherGroup1() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(1));
        deanery.transferToAnotherGroup(newStudent1, groups.get(2));
        assertEquals(newStudent1.getGroup(), newStudent.getGroup());
    }

    @Test
    public void transferToAnotherGroup2() {
        Student newStudent = deanery.createStudent("Ерроров Стек Оверфлоевич");
        Student newStudent1 = deanery.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(2));
        newStudent1.enrolltoGroup(groups.get(2));
        deanery.transferToAnotherGroup(newStudent, groups.get(0));
        deanery.transferToAnotherGroup(newStudent1, groups.get(0));
        assertEquals(newStudent.getGroup(), newStudent1.getGroup());
    }

    public Deanery getBaseDeanery(){
        Deanery testD = new Deanery();
        String fios[] = {"Ерроров Стек Оверфлоевич", "Ерророва Стека Оверфлоевна",
                "Джавин Иван Андреевич", "Собакина Марфа Петровна"};
        ArrayList<Student> students = new ArrayList<Student>();
        for(String fio: fios){
            students.add(testD.createStudent(fio));
        }
        Group testGr = new Group("PHP-1");
        testD.addGroup(testGr);
        Group testGr1 = new Group("Go-1");
        testD.addGroup(testGr1);

        for (int i =0; i< students.size()/2; i++){
            testGr.addStudent(students.get(i));
        }

        for (int i =students.size()/2 ; i< students.size(); i++){
            testGr1.addStudent(students.get(i));
        }
        testD.addMarks();

        return testD;
    }

    @Test
    public void getTheWorstStudentsInGroup() {
        Deanery testD = getBaseDeanery();
        Student theWorstStudent = testD.createStudent("Худшов Харитон Артёмович");
        for (int i = 0;i <10; i++){
            theWorstStudent.addMark(1);
        }
        ArrayList<Group> groups = testD.getGroups();
        groups.get(0).addStudent(theWorstStudent);
        ArrayList<Student> awfullStudents1 = testD.getTheWorstStudentsInGroup();
        System.out.println(awfullStudents1);
        assertEquals(awfullStudents1.contains(theWorstStudent), true);
    }

    @Test
    public void expellTheWorstStudents() {
        Deanery testD = getBaseDeanery();
        Student theWorstStudent = testD.createStudent("Худшов Харитон Артёмович");
        for (int i = 0;i <10; i++){
            theWorstStudent.addMark(1);
        }
        ArrayList<Group> groups = testD.getGroups();
        groups.get(0).addStudent(theWorstStudent);
        ArrayList<Student> awfullStudents = testD.getTheWorstStudentsInGroup();
        ArrayList<Student> successfullStudents = testD.expellTheWorsetStudents(awfullStudents);
        System.out.println(successfullStudents);
        assertFalse(successfullStudents.contains(theWorstStudent));
    }

}