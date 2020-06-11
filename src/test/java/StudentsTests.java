import University.Deanery;
import University.Student;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentsTests {
    @Test
    public void createStudent()
    {
        Student student=Deanery.createRandomStudent();
        student.addMark(2);
        student.addMark(3);
        student.addMark(3);
        student.addMark(5);
        assertEquals((float) (2+3+3+5)/4,student.getAverageMark(),0.01);
    }
}
