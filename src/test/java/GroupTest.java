import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    Group groupTest=new Group("GroupForTest");
    Student student1=new Student("Иванов Иван Иванович", 252577);
    Student student2=new Student("Пертов Петр Петрович", 252578);
    Student student3=new Student("Сидоров Сергей Сергеевич", 252579);

    @org.junit.Test
    public void checkGetTitle() {
        assertEquals("GroupForTest",groupTest.getTitle());
    }

    @org.junit.Test
    public void checkSetTitle() {
        groupTest.setTitle("GroupForTest-2");
        assertEquals("GroupForTest-2",groupTest.getTitle());
    }

    @org.junit.Test
    public void checkGetHeadNull() {
        assertEquals(null,groupTest.getHead());
    }

    @org.junit.Test
    public void checkSetHead() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        groupTest.setHead(student2);
        assertEquals(student2,groupTest.getHead());
    }

    @org.junit.Test
    public void checkAddToGroupBoolean() {
        assertEquals(true,groupTest.addToGroup(student1));
    }

    @org.junit.Test
    public void checkAddToGroupStudent() {
        groupTest.addToGroup(student1);
        int index=groupTest.studentsGroup.size()-1; //this the last student in the group
        assertEquals(student1,groupTest.studentsGroup.get(index));
    }

    @org.junit.Test
    public void checkAddToGroupGroup() {
        groupTest.addToGroup(student1);
        assertEquals(groupTest,student1.getGroup());
    }

    @org.junit.Test
    public void checkChoiceHead() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        groupTest.choiceHead();
        assertEquals(true,(groupTest.getHead()!=null)); //this method choices head randomly
    }

    @org.junit.Test
    public void checkSearchStudentId() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        assertEquals(student2,groupTest.searchStudent(252578));
    }

    @org.junit.Test
    public void checkSearchStudentIdNull() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        assertEquals(null,groupTest.searchStudent(345623));
    }

    @org.junit.Test
    public void checkSearchStudentFio1() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        ArrayList<Student> expStudent = new ArrayList<Student>();
        expStudent.add(student3);

        assertEquals(expStudent,groupTest.searchStudent("Сидоров Сергей Сергеевич"));
    }

    @org.junit.Test
    public void checkSearchStudentSameFio() {

        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2); //"Пертов Петр Петрович" too
        groupTest.addToGroup(student3);
        Student student4=new Student("Пертов Петр Петрович", 252533);
        groupTest.addToGroup(student4);

        ArrayList<Student> expStudent = new ArrayList<Student>();
        expStudent.add(student2);
        expStudent.add(student4);

        assertEquals(expStudent,groupTest.searchStudent("Пертов Петр Петрович"));
    }

    @org.junit.Test
    public void checkSearchStudentFioNull() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        // groupTest has no "Федоров Федор Федорович"
        assertEquals(0,(groupTest.searchStudent("Федоров Федор Федорович")).size());
    }

    @org.junit.Test
    public void checkAmountAverageBall() {

        student1.addAssess(5);//4.5
        student1.addAssess(4);

        student2.addAssess(4); //3.5
        student2.addAssess(3);

        student3.addAssess(5);//4.5
        student3.addAssess(4);

        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        assertEquals(4.166666666666667,groupTest.amountAverageBall());
    }

    @org.junit.Test
    public void checkExclusionFromTheGroup() {

        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        assertEquals(true,groupTest.exclusionFromTheGroup(student2));
        assertEquals(null,groupTest.searchStudent(252578));
    }

    @org.junit.Test
    public void checkToString() {
        groupTest.addToGroup(student1);
        groupTest.addToGroup(student2);
        groupTest.addToGroup(student3);

        assertEquals("GroupForTest:\nNumber of students - 3\n" ,groupTest.toString());
    }
}