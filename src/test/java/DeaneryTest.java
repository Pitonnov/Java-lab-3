import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeaneryTest {

    static Deanery deanery = Deanery.getInstance("Test");
    static Student unrolledStudent = deanery.createStudent("unrolledStudent");
    static Student enrolledStudent = deanery.createStudent("enrolledStudent");
    static Group startGroup = deanery.createGroup("startGroup");

    @BeforeAll
    static void prepare(){
        deanery.enrolling(enrolledStudent, "startGroup");
    }


    @Test
    void createGroup() {
        assertNull(deanery.createGroup(null));
        assertNull(deanery.createGroup(""));
        assertEquals("testGroup", deanery.createGroup("testGroup").getTitle());
        assertEquals("testGroup", deanery.getGroup("testGroup").getTitle());
    }

    @Test
    void createStudent() {
        assertNull(deanery.createStudent(null));
        assertNull(deanery.createStudent(""));
        assertEquals("testName", deanery.createStudent("testName").getName());
        assertTrue(deanery.findStudent("testName"));
    }

    @Test
    void enrolling(){
        assertNull(deanery.enrolling(null, "testGroup"));
        assertNull(deanery.enrolling(unrolledStudent, ""));
        assertNull(deanery.enrolling(unrolledStudent, null));
        assertNull(deanery.enrolling(enrolledStudent,"testGroup"));
        assertNull(deanery.enrolling(unrolledStudent, "unregisteredGroup"));
        assertEquals("testGroup", deanery.enrolling(unrolledStudent, "testGroup").getTitle());
        deanery.expelStudent(unrolledStudent);
    }

    @Test
    void setStudentRating(){
        assertFalse(deanery.setStudentRating(unrolledStudent, 3));
        assertFalse(deanery.setStudentRating(enrolledStudent, 6));
        assertTrue(deanery.setStudentRating(enrolledStudent, 5));
    }

    @Test
    void addRating(){
        deanery.deleteAllMarks(enrolledStudent);
        deanery.deleteAllMarks(unrolledStudent);
        assertTrue(unrolledStudent.averageRating() == 0);
        assertTrue(enrolledStudent.averageRating() == 0);
        deanery.addRatings();
        assertTrue(unrolledStudent.averageRating() == 0);
        assertTrue(enrolledStudent.averageRating() != 0);
    }

    @Test
    void expelStudent(){
        Student testStudent = deanery.createStudent("testStudent");
        deanery.enrolling(testStudent, "startGroup");
        assertTrue(deanery.findStudent("testStudent"));
        assertEquals(testStudent.toString() + "\n", deanery.getGroup("startGroup").findStudent("testStudent"));
        deanery.expelStudent(testStudent);
        assertFalse(deanery.findStudent("testStudent"));
        assertEquals("Nobody is found", deanery.getGroup("startGroup").findStudent("testStudent"));
    }

    @Test
    void transferring(){
        deanery.createGroup("anotherGroup");
        assertFalse(deanery.transferring(enrolledStudent, null));
        assertFalse(deanery.transferring(enrolledStudent, ""));
        assertFalse(deanery.transferring(enrolledStudent, "nonexistentGroup"));
        assertFalse(deanery.transferring(unrolledStudent, "anotherGroup"));
        assertEquals("startGroup", enrolledStudent.sayGroup());
        deanery.transferring(enrolledStudent, "anotherGroup");
        assertEquals("anotherGroup", enrolledStudent.sayGroup());
    }

    @Test
    void doHeadElection(){
        Group aGroup = deanery.createGroup("aGroup");
        Student aStudent = deanery.createStudent("aStudent");
        deanery.enrolling(aStudent, "aGroup");
        assertEquals("aGroup group has no head", aGroup.whoIsHead());
        deanery.doHeadElection();
        assertEquals("Head of " + aGroup.toString() + " is " + aStudent.toString(), aGroup.whoIsHead());
        Student bStudent = deanery.createStudent("bStudent");
        deanery.enrolling(bStudent, "aGroup");
        deanery.expelStudent(aStudent);
        assertEquals("Head of " + aGroup.toString() + " is " + bStudent.toString(), aGroup.whoIsHead());
    }

    @Test
    void expelPoorStudents(){
        Student Student1 = deanery.createStudent("Student1");
        Student Student2 = deanery.createStudent("Student2");
        Student Student3 = deanery.createStudent("Student3");
        deanery.enrolling(Student1, "startGroup");
        deanery.enrolling(Student2, "startGroup");
        deanery.enrolling(Student3, "startGroup");
        deanery.setStudentRating(Student1, 1);
        deanery.setStudentRating(Student2, 2);
        deanery.setStudentRating(Student3, 3);
        deanery.setStudentRating(enrolledStudent, 5);
        assertTrue(deanery.findStudent("Student1"));
        assertTrue(deanery.findStudent("Student2"));
        assertTrue(deanery.findStudent("Student3"));
        deanery.expelPoorStudents();
        assertFalse(deanery.findStudent("Student1"));
        assertFalse(deanery.findStudent("Student2"));
        assertTrue(deanery.findStudent("Student3"));
    }

    @Test
    void findStudent(){
        assertTrue(deanery.findStudent("roll"));
        assertTrue(deanery.findStudent("enrolledStudent"));
        assertFalse(deanery.findStudent("nonexistentStudent"));
        assertFalse(deanery.findStudent(-1));
        assertFalse(deanery.findStudent(0));
        assertTrue(deanery.findStudent(1));
    }

    @Test
    void getStudent(){
        Student idStudent = deanery.createStudent("idStudent");
        int id = idStudent.getId();
        assertNotSame(idStudent, deanery.getStudent(enrolledStudent.getId()));
        assertSame(idStudent, deanery.getStudent(id));
    }

    @Test
    void getGroup(){
        Group newGroup = deanery.createGroup("newGroup");
        assertNotSame(newGroup, deanery.getGroup("startGroup"));
        assertNotSame(newGroup, deanery.getGroup("nonexistentGroup"));
        assertSame(newGroup, deanery.getGroup("newGroup"));
    }
}