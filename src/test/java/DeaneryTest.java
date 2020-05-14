import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeaneryTest {

    Deanery testDeanary = new Deanery("Testst.json","Testgr.json");

    @BeforeEach
    void setup1(){
        testDeanary.distributeToGroups();
        testDeanary.Exam();
        testDeanary.Statistic();
    }


    @ParameterizedTest
    @CsvSource({ "Студент1, 100", "Студент2, 101" })
    void changeGroup(String argument, int arg) {
        assertEquals(argument,testDeanary.changeGroup("Группа1","Группа2",arg));
    }


    @Test
    void headchoice() {
        List<Group> test = testDeanary.getGroups();
        ArrayList<Student> heads = new ArrayList<>();
        Student test1 = Student.generate(1000,"Староста");
        try{
            test1.addMarks(5);
        }catch (BadMarkException e){
            e.printStackTrace();
        }
        for (Group i: test){
            i.addStudent(test1);
            heads.add(test1);
        }
        assertEquals(heads.toString(),testDeanary.headchoice(2).toString());
    }

    @Test
    void execeptStudent() {
        List<Group> test = testDeanary.getGroups();
        Student test1 = Student.generate(1000,"Староста");
        test.get(0).addStudent(test1);
        assertEquals(test1,testDeanary.ExeceptStudent(1000));
    }

    @ParameterizedTest
    @ValueSource(ints = { 150, 170,190,200 })
    void execeptStudent1(int arg) throws NoSuchElementException {
        assertThrows(NoSuchElementException.class, () -> testDeanary.ExeceptStudent(arg));
    }

}