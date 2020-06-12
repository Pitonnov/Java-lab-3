import org.junit.Test;
import static org.junit.Assert.*;
public class DeaneryTest
{
    @Test
    public void createGroups()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try
        {
            Group group = deanery.findGroup("A1");
            assertEquals("A1", group.getTitle());
        } catch (Deanery.NoGroupException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void createStudents()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try
        {
            Student student = deanery.findGroup("A1").findStudent("Баскова Даздраперма Никитишна");
            assertEquals("Баскова Даздраперма Никитишна", student.getFio());
        } catch (Deanery.NoGroupException | Group.NoStudentException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void findGroup()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try
        {
            Group foundGroup = deanery.findGroup("B1");
            boolean ifTrue = foundGroup.getTitle().equals("B1");
        } catch (Deanery.NoGroupException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void findGroupException()
    {
        Deanery deanery = new Deanery();
        Group group1 = new Group("A1");
        Group group2 = new Group("B1");
        Group group3 = new Group("C1");
        try
        {
            Group foundGroup = deanery.findGroup("B1");
        } catch (Deanery.NoGroupException e)
        {
            boolean exceptionThrown=true;
            assertTrue(exceptionThrown);
        }
    }
    @Test
    public void changeGroupId()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try
        {
            Student student = deanery.findGroup("A1").findStudent(1);
            deanery.changeGroup(1,"B1");
            assertEquals("B1", student.getGroup().getTitle());
        }
        catch (Group.NoStudentException | Deanery.NoGroupException e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void changeGroupFio()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        try
        {
            Student student = deanery.findGroup("A").findStudent("Баскова Даздраперма Никитишна");
            deanery.changeGroup("Баскова Даздраперма Никитишна","B1");
            assertEquals("B1", student.getGroup().getTitle());
        }
        catch (Group.NoStudentException | Deanery.NoGroupException e)
        {
            e.printStackTrace();
        }
    }
}