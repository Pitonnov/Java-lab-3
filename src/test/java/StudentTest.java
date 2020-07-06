import org.junit.Test;
import static org.junit.Assert.*;
public class StudentTest
{
    @Test
    public void testEquals()
    {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(1, "Десктопов Сергей Михайлович");
        boolean ifTrue = student1.equals(student2);
        assertTrue(ifTrue);
    }
    @Test
    public void testNotEquals()
    {
        Student student1 = new Student(1, "Десктопов Сергей Михайлович");
        Student student2 = new Student(2, "Лобанов Илья Алексеевич");
        boolean ifFalse = student1.equals(student2);
        assertFalse(ifFalse);
    }
    @Test
    public void averageMark5()
    {
        Student student = new Student(1, "Десктопов Сергей Михайлович");
        student.addMark(5);
        student.addMark(4);
        assertEquals(5, student.averageMark());
    }
    @Test
    public void averageMark4()
    {
        Student student = new Student(1, "Десктопов Сергей Михайлович");
        student.addMark(5);
        student.addMark(4);
        student.addMark(4);
        assertEquals(4, student.averageMark());
    }
}