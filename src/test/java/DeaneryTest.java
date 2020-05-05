import Deanery.Deanery;
import Deanery.Group;
import Deanery.MARKS;
import Deanery.Student;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Test;

public class DeaneryTest {

    @org.junit.Test
    public void createNewDeanery() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        assertEquals(0,testDeanery.getGroups().size());
        assertEquals(0,testDeanery.getStudents().size());
    }

    @org.junit.Test
    public void createStudentsFromFile() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        assertEquals(60,testDeanery.getStudents().size());
    }

    @org.junit.Test
    public void createGroupsFromFile() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        assertEquals(3,testDeanery.getGroups().size());
    }

    @org.junit.Test
    public void createGroupsFromJSONfile(){
        Deanery testDeanery = Deanery.createNewDeanery("1234");
		try{
            testDeanery.createGroupsFromJSONfile("./src/main/resources/Groups and students.json","1234");
            assertEquals(3,testDeanery.getGroups().size());
            assertEquals(60,testDeanery.getStudents().size());
		}
		catch (org.json.JSONException e){}
    }


    @org.junit.Test
    public void addAllStudentsToRandomGroups() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        for (Group group : testDeanery.getGroups()) {
            assertNotEquals(0, group.getStudents().size());
        }
        for (Student student : testDeanery.getStudents()) {
            assertNotEquals(null, student.getGroup());
        }
    }

    @org.junit.Test
    public void electHeads() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        testDeanery.electHeads("1234");
        for (Group group : testDeanery.getGroups()) {
            assertNotEquals(null, group.getHead());
            assertTrue(group.getStudents().contains(group.getHead()));

        }
    }

    @org.junit.Test
    public void addRandomMarks() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addRandomMarks(10,"1234");
        ArrayList<MARKS> markList = new ArrayList<>(Arrays.asList(MARKS.values()));
        boolean allMarksAreTheSame = true;
        for (Student student : testDeanery.getStudents()) {
            assertEquals(10,student.getMarks().size());
            for (int i = 0; i < student.getMarks().size()-1; i++) {
                assertTrue(markList.contains(student.getMarks().get(i)));
                if( ! (student.getMarks().get(i+1).equals(student.getMarks().get(i)))){
                    allMarksAreTheSame = false;
                    break;
                }
            }
            assertFalse(allMarksAreTheSame);
        }
    }

    @org.junit.Test
    public void ratingStatistics() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        testDeanery.addRandomMarks(10,"1234");
        testDeanery.ratingStatistics();
        for (int i = 0; i < testDeanery.getGroups().size()-1; i++) {
            assertTrue(MARKS.MARKStoInt(testDeanery.getGroups().get(i).getMiddleMark())
                    >= MARKS.MARKStoInt(testDeanery.getGroups().get(i+1).getMiddleMark()));
        }
        for (int i = 0; i < testDeanery.getStudents().size()-1; i++) {
            assertTrue(MARKS.MARKStoInt(testDeanery.getStudents().get(i).getMiddleMark())
                    >= MARKS.MARKStoInt(testDeanery.getStudents().get(i+1).getMiddleMark()));
        }
    }

    @org.junit.Test
    public void changeGroup() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        Student testStudent = null;
        Group oldGroup = null;
        Group newGroup = testDeanery.getGroups().get(0);
        for (Student student : testDeanery.getStudents()) {
            if ( ! (student.getGroup().getTitle().equals(newGroup.getTitle()))){
                testStudent = student;
                oldGroup = student.getGroup();
                testDeanery.changeGroup(testStudent.getFio(),newGroup.getTitle(),"1234");
                break;
            }
        }
        assertTrue(newGroup.getStudents().contains(testStudent));
        assertFalse(oldGroup.getStudents().contains(testStudent));
        assertEquals(newGroup, testStudent.getGroup());
    }

    @org.junit.Test
    public void removeStudentFromDeanery() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        Student testStudent = testDeanery.getStudents().get(7);
        Group testGroup = testStudent.getGroup();
        testDeanery.removeStudentFromDeanery(testStudent,"1234");
        assertFalse(testDeanery.getStudents().contains(testStudent));
        assertFalse(testGroup.getStudents().contains(testStudent));
        assertEquals(null,testStudent.getGroup());
    }

    @org.junit.Test
    public void removeStudentsByStatistics() {
        Deanery testDeanery = Deanery.createNewDeanery("1234");
        testDeanery.createGroupsFromFile("./src/main/resources/groups.txt","1234");
        testDeanery.createStudentsFromFile("./src/main/resources/students.txt","1234");
        testDeanery.addAllStudentsToRandomGroups("1234");
        testDeanery.addRandomMarks(10,"1234");
        Student testStudent = null;
        Group testGroup = null;
        for (Student student : testDeanery.getStudents()) {
            if(student.getMiddleMark().equals(MARKS.UNSATISFACTORILY)){
                testStudent = student;
                testGroup = student.getGroup();
                break;
            }
        }
        testDeanery.removeStudentsByStatistics(MARKS.UNSATISFACTORILY,"1234");
        assertFalse(testDeanery.getStudents().contains(testStudent));
        assertFalse(testGroup.getStudents().contains(testStudent));
        assertEquals(null,testStudent.getGroup());
    }
}