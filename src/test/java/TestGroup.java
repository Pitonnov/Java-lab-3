import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestGroup {

    Group group = new Group("Ядерная медицина");
    List<Student> students = new ArrayList<Student>();
    Student student = new Student(3,"Червона Полина Петровна");
    Student student2 = new Student(7,"Громов Платон Вадимович");

    Deanery deanery = new Deanery();

    @Before
    public void before() {
        deanery.groupsCreation();
        deanery.studentsCreation();
    }


    @Test
    public void testGroup() {
        assertEquals("Ядерная медицина", group.getTitle());
    }

    @Test
    public void testAddStudent() {
        group.addStudent(student);
        group.addStudent(student2);
        assertEquals("Червона Полина Петровна", group.getStudents().get(0).getFio());
    }

    @Test
    public void testSetHead() {
        group.setHead(student);
        assertEquals(3, group.getHead().getId());
    }

    @Test
    public void testSearchStudent() {
        assertEquals(7, deanery.getGroup().get(0).searchStudent("Громов Платон Вадимович").getId());
        assertEquals(null, deanery.getGroup().get(0).searchStudent(123));
    }

    @Test
    public void testAverageRatingGroup() {
        for (int i = 0; i < deanery.getGroup().get(0).getStudents().size(); i++) {
            deanery.getGroup().get(0).getStudents().get(i).addGrade(5);
        }
        assertEquals("5.0", String.valueOf(deanery.getGroup().get(0).averageRatingGroup()));
    }

    @Test
    public void testExcludeStudent() {
        deanery.getGroup().get(0).excludeStudent(deanery.getGroup().get(0).getStudents().get(0));
       assertEquals(2, deanery.getGroup().get(0).getStudents().get(0).getId());
    }
}
