import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GroupTest {

    Group gr = Group.generate("Тест1");
    ArrayList<Student> students = new ArrayList<>();

    @BeforeEach
    void setup() {
        for (Integer i = 0; i<10; i++){
            students.add(Student.generate(i,"Студент"+i.toString()));
            gr.addStudent(students.get(i));
        }
    }

   @Test
    void getTitle() {
        assertEquals("Тест1",gr.getTitle());
    }

    @Test
    void addStudent() {
        Student st = Student.generate(10,"Тест");
        assertEquals(11,gr.addStudent(st));
    }
    @Test
    void addStudent2() {
        assertEquals(10,gr.getStudents().size());
    }
    @Test
    void addHead() {
        assertEquals("Студент5 - назначен/на старостой",gr.addHead(students.get(5)));
    }
    @Test
    void addHead2() {
        assertNotEquals("Студент5 - назначен/на старостой",gr.addHead(students.get(4)));
    }

    @ParameterizedTest
    @CsvSource({ "Студент1, 1", "Студент2, 2","Студент3, 3","Студент4, 4","Студент5, 5" })
    void searchStudent(String arg1, int arg2) {
        assertEquals(students.get(arg2),gr.searchStudent(arg1));
        assertEquals(students.get(arg2),gr.searchStudent(arg2));
    }

    @ParameterizedTest
    @CsvSource({ "Студент1, 2", "Студент2, 3","Студент3, 4","Студент4, 5","Студент5, 6" })
    void searchStudent2(String arg1, int arg2) {
        assertNotEquals(students.get(arg2),gr.searchStudent(arg1));
    }

    @Test
    void getStudents() {
        assertEquals(students,gr.getStudents());
    }

    @Test
    void averageMark() {
        for (int i = 0;i<10;i++){
            try {
                students.get(i).addMarks(5);
            }catch (BadMarkException e){
                e.printStackTrace();
            }
        }
        assertEquals(5,gr.averageMark());
    }
}