import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class DeaneryTest {
    private String expected;
    private int id;
    private int id2;
    private int id3;

    Deanery testDeanary = new Deanery("Testst.json", "Testgr.json");

    @Before
    public void setup() {
        testDeanary.distributeToGroups();
        testDeanary.Exam();
        testDeanary.Statistic();
    }

    public DeaneryTest(String expected, int id,int id2,int id3) {
        this.expected = expected;
        this.id = id;
        this.id2 = id2;
        this.id3 = id3;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Студент1", 100,150,170},
                {"Студент2", 101,190,200}
        });
    }

    @Test
    public void changeGroup() {
        assertEquals(expected, testDeanary.changeGroup("Группа1", "Группа2", id));
    }

    @Test
    public void headchoice() {
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
    public void execeptStudent() {
        List<Group> test = testDeanary.getGroups();
        Student test1 = Student.generate(1000,"Староста");
        test.get(0).addStudent(test1);
        assertEquals(test1,testDeanary.ExeceptStudent(1000));
    }

    @Test (expected = NoSuchElementException.class)
    public void execeptStudent1() throws NoSuchElementException {
        testDeanary.ExeceptStudent(id2);
    }
    @Test (expected = NoSuchElementException.class)
    public void execeptStudent2() throws NoSuchElementException {
        testDeanary.ExeceptStudent(id3);
    }
}
