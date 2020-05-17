import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    static Deanery deanery = Deanery.getInstance("Test");
    static Student unrolledStudent = deanery.createStudent("unrolledStudent");
    static Student enrolledStudent = deanery.createStudent("enrolledStudent");
    static Group startGroup = deanery.createGroup("startGroup");

    @BeforeAll
    static void prepare(){
        deanery.enrolling(enrolledStudent, "startGroup");
    }

    @Test
    void findStudent() {
        assertNull(startGroup.findStudent(null));
        assertNull(startGroup.findStudent(""));
        assertEquals("Nobody is found", startGroup.findStudent("unregisteredStudent"));
        assertEquals(enrolledStudent.toString() + "\n", startGroup.findStudent("roll"));
        assertEquals(enrolledStudent.toString() + "\n", startGroup.findStudent("enrolledStudent"));
    }

    @Test
    void testFindStudent() {
        assertNull(startGroup.findStudent(0));
        assertNull(startGroup.findStudent(-1));
        assertEquals("Nobody is found", startGroup.findStudent(1000));
        assertEquals(enrolledStudent.toString(), startGroup.findStudent(enrolledStudent.getId()));
    }

    @Test
    void headElection() {
        Group AGroup = deanery.createGroup("AGroup");
        Student AStudent = deanery.createStudent("AStudent");
        AStudent.enrollTo("AGroup");
        assertEquals(AStudent.toString(), AGroup.findStudent(AStudent.getId()));
        assertEquals(AGroup.toString() + " has no head", AGroup.whoIsHead());
        AGroup.headElection();
        assertEquals("Head of " + AGroup.toString() + " is " + AStudent.toString(), AGroup.whoIsHead());
    }

    @Test
    void averageRating() {
        Group Group123 = deanery.createGroup("Group123");
        Student Student1 = deanery.createStudent("Student1");
        Student Student2 = deanery.createStudent("Student2");
        Student Student3 = deanery.createStudent("Student3");
        Student1.enrollTo("Group123");
        Student2.enrollTo("Group123");
        Student3.enrollTo("Group123");
        Student1.addMark(5);
        Student2.addMark(2);
        Student3.addMark(2);
        assertEquals(3, Group123.averageRating());
    }
}