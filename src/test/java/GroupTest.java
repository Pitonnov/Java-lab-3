import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {

    static Deanery deanery = Deanery.getInstance("Test");
    static Student unrolledStudent = deanery.createStudent("unrolledStudent");
    static Student enrolledStudent = deanery.createStudent("enrolledStudent");
    static Group startGroup = deanery.createGroup("startGroup");

    @BeforeClass
    public static void prepare(){
        deanery.enrolling(enrolledStudent, "startGroup");
    }

    @Test
    public void findStudent() {
        assertNull(startGroup.findStudent(null));
        assertNull(startGroup.findStudent(""));
        assertEquals("Nobody is found", startGroup.findStudent("unregisteredStudent"));
        assertEquals(enrolledStudent.toString() + "\n", startGroup.findStudent("roll"));
        assertEquals(enrolledStudent.toString() + "\n", startGroup.findStudent("enrolledStudent"));
    }

    @Test
    public void testFindStudent() {
        assertNull(startGroup.findStudent(0));
        assertNull(startGroup.findStudent(-1));
        assertEquals("Nobody is found", startGroup.findStudent(1000));
        assertEquals(enrolledStudent.toString(), startGroup.findStudent(enrolledStudent.getId()));
    }

    @Test
    public void headElection() {
        Group AGroup = deanery.createGroup("AGroup");
        Student AStudent = deanery.createStudent("AStudent");
        AStudent.enrollTo("AGroup");
        assertEquals(AStudent.toString(), AGroup.findStudent(AStudent.getId()));
        assertEquals(AGroup.toString() + " has no head", AGroup.whoIsHead());
        AGroup.headElection();
        assertEquals("Head of " + AGroup.toString() + " is " + AStudent.toString(), AGroup.whoIsHead());
    }

    @Test
    public void averageRating() {
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
        assertEquals(3, (int)Group123.averageRating());
    }
}