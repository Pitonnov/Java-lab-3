import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestStudent {

   Deanery deanery = new Deanery();
   List<Student> students = new ArrayList<Student>();
   Student student = new Student(20, "Тарасова Надежда Викторовна");

    @Before
    public void before() {
        deanery.groupsCreation();
        deanery.studentsCreation();
    }


    @Test
    public void testStudent() {
        assertEquals(20, student.getId());
        assertEquals("Тарасова Надежда Викторовна", student.getFio());
    }

    @Test
    public void testEnrollment() {
        deanery.getStudent().get(15).enrollment("Ядерная медицина");
        assertEquals("Ядерная медицина", deanery.getStudent().get(15).getGroup().getTitle());
    }

    @Test
    public void testAddGrade() {
        deanery.getStudent().get(15).addGrade(2);
        deanery.getStudent().get(15).addGrade(4);
        assertEquals("3.0", String.valueOf(deanery.getStudent().get(15).averageRating()));
    }

}
