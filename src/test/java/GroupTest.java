import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
public class GroupTest
{
    @Test
    public void testEquals()
    {
        Group group1 = new Group("A1");
        Group group2 = new Group("A1");
        boolean ifTrue = group1.equals(group2);
        assertTrue(ifTrue);
    }
    @Test
    public void findStudentId()
    {
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(2, "Лобанов Илья Алексеевич");
        Group group = new Group("A1");
        group.addStudent(student1);
        group.addStudent(student2);
        try
        {
            Student foundStudent = group.findStudent(2);
            boolean ifTrue = foundStudent.equals(student2);
        }
        catch (Group.NoStudentException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void findStudentFio()
    {
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(2, "Лобанов Илья Алексеевич");
        Group group = new Group("A1");
        group.addStudent(student1);
        group.addStudent(student2);
        try
        {
            Student foundStudent = group.findStudent("Лобанов Илья Алексеевич");
            boolean ifTrue = foundStudent.equals(student2);
        }
        catch (Group.NoStudentException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindStudentIdException()
    {
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(2, "Лобанов Илья Алексеевич");
        Group group = new Group("A1");
        group.addStudent(student1);
        group.addStudent(student2);
        try
        {
            Student foundStudent = group.findStudent(5);
        }
        catch (Group.NoStudentException e)
        {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }
    @Test
    public void testFindStudentFioException()
    {
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(2, "Лобанов Илья Алексеевич");
        Group group = new Group("A1");
        group.addStudent(student1);
        group.addStudent(student2);
        try
        {
            Student foundStudent = group.findStudent("Лобанов И. А.");
        }
        catch (Group.NoStudentException e)
        {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }
    @Test
    public void averageMark()
    {
        Group group = new Group("A1");
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        student1.addMark(3);
        group.addStudent(student1);
        Student student2 = new Student(2, "Топоров Зураб Олегович");
        student2.addMark(4);
        group.addStudent(student2);
        Student student3 = new Student(3, "Глазов Николай Петрович");
        student3.addMark(5);
        group.addStudent(student3);
        assertEquals(4, group.averageMark());
    }
    @Test
    public void expellStudent()
    {
        Group group = new Group("A1");
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        group.addStudent(student1);
        Student student2 = new Student(2, "Топоров Зураб Олегович");
        group.addStudent(student2);
        group.expellStudent(student2);
        try
        {
            group.findStudent(2);
        }
        catch (Group.NoStudentException e)
        {
            boolean exceptionThrown = true;
            assertTrue(true);
        }
    }
}
