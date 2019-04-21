import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {
    private Student student = new Student(1,"Gandzha Denis Sergeevich");
    @Test
    public void getId() {
        assertEquals(1,student.getId());
    }

    @Test
    public void getFio() {
        assertEquals("Gandzha Denis Sergeevich",student.getFio());
    }

    @Test
    public void avarageMark(){
        assertEquals(0,student.marks.size());
        student.addMark(5);
        student.addMark(5);
        assertEquals(2,student.marks.size());
        assertEquals(5,student.averageMark(),001);
    }

}