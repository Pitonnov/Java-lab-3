import static org.junit.Assert.*;
import org.junit.Test;

public class GroupTest {

    @org.junit.Test
    public void createNewGroup() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        assertEquals("TestGroup",testGroup.getTitle());
        assertEquals(0,testGroup.getStudents().size());
        assertEquals(null,testGroup.getHead());
    }

    @org.junit.Test
    public void addStudent() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        testGroup.addStudent(testStudent1,"1234");
        assertEquals(1,testGroup.getStudents().size());
        assertEquals("Ivanov Ivan",testGroup.getStudents().get(0).getFio());
        assertEquals("TestGroup",testStudent1.getGroup().getTitle());
    }

    @org.junit.Test
    public void chooseHead1() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        try{
            testGroup.chooseHead(testStudent1,"1234");
            fail();
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){}
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        try{
            testGroup.chooseHead(testStudent1,"1234");
            assertEquals("Ivanov Ivan",testGroup.getHead().getFio());
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){fail();}
    }

    @org.junit.Test
    public void ChooseHead2() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        testGroup.chooseHead("Ivanov Ivan","1234");
        assertEquals(null,testGroup.getHead());
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        testGroup.chooseHead("Ivanov Ivan","1234");
        assertEquals("Ivanov Ivan",testGroup.getHead().getFio());
    }

    @org.junit.Test
    public void ChooseHead3() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        testGroup.chooseHead(777,"1234");
        assertEquals(null,testGroup.getHead());
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        testGroup.chooseHead(777,"1234");
        assertEquals("Ivanov Ivan",testGroup.getHead().getFio());
    }

    @org.junit.Test
    public void findStudent1() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        try{
            assertEquals(testStudent1,testGroup.findStudent(777));
            fail();
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){}
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        try{
            assertEquals(testStudent1,testGroup.findStudent(777));
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){
            fail();
        }
    }

    @org.junit.Test
    public void FindStudent2() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        try{
            assertEquals(testStudent1,testGroup.findStudent("Ivanov Ivan"));
            fail();
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){}
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        try{
            assertEquals(testStudent1,testGroup.findStudent("Ivanov Ivan"));
        }
        catch (DeaneryExceptions.GroupDoesntContainStudentException e){
            fail();
        }
    }

    @org.junit.Test
    public void getMiddleMark() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        assertEquals(null, testGroup.getMiddleMark());
        testStudent1.addMarks("1234",5,1,5,1);
        testStudent2.addMarks("1234",1,5,1,5);
        assertEquals(null, testGroup.getMiddleMark());
        testGroup.addStudent(testStudent1,"1234");
        testGroup.addStudent(testStudent2,"1234");
        assertEquals(MARKS.SATISFACTORILY, testGroup.getMiddleMark());
    }

    @org.junit.Test
    public void removeStudent() {
        Student testStudent1 = Student.createNewStudent(777,"Ivanov Ivan", "1234");
        Student testStudent2 = Student.createNewStudent(33333,"Petrov Pashka", "1234");
        Group testGroup = Group.createNewGroup("TestGroup","1234");
        testGroup.addStudent(testStudent1,"1234");
        testGroup.removeStudent(testStudent1,"1234");
        assertEquals(0,testGroup.getStudents().size());
        assertEquals(null,testStudent1.getGroup());
    }
}
