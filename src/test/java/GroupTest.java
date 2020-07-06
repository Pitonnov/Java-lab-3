import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(value = Parameterized.class)
public class GroupTest {
    private String expected;
    private int id;
    private int id2;

    Group gr = Group.generate("Тест1");
    ArrayList<Student> students = new ArrayList<>();

    @Before
    public void setup() {
        for (Integer i = 0; i<10; i++){
            students.add(Student.generate(i,"Студент"+i.toString()));
            gr.addStudent(students.get(i));
        }
    }

    public GroupTest(String expected, int id,int id2) {
        this.expected = expected;
        this.id = id;
        this.id2 = id2;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Студент1", 1,2},
                {"Студент2", 2,3},
                {"Студент3", 3,4},
                {"Студент4", 4,5},
                {"Студент5", 5,6},
        });
    }

    @Test
    public void getTitle() {
        assertEquals("Тест1",gr.getTitle());
    }

    @Test
    public void addStudent() {
        Student st = Student.generate(10,"Тест");
        assertEquals(11,gr.addStudent(st));
    }
    @Test
    public void addStudent2() {
        assertEquals(10,gr.getStudents().size());
    }
    @Test
    public void addHead() {
        assertEquals("Студент5 - назначен/на старостой",gr.addHead(students.get(5)));
    }
    @Test
    public void addHead2() {
        assertNotEquals("Студент5 - назначен/на старостой",gr.addHead(students.get(4)));
    }

    @Test
    public void searchStudent() {
        assertEquals(students.get(id),gr.searchStudent(expected));
        assertEquals(students.get(id),gr.searchStudent(id));
    }

    @Test
    public void searchStudent2() {
        assertNotEquals(students.get(id2),gr.searchStudent(expected));
    }

    @Test
    public void getStudents() {
        assertEquals(students,gr.getStudents());
    }

    @Test
    public void averageMark() {
        for (int i = 0;i<10;i++){
            try {
                students.get(i).addMarks(5);
            }catch (BadMarkException e){
                e.printStackTrace();
            }
        }
        assertEquals(5.0,gr.averageMark(),0.01);
    }
}