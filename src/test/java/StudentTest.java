import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class StudentTest {
    private int mark;
    private int mark1;

    Student st = Student.generate(1000,"Студент");

    public StudentTest(int mark,int mark1) {
        this.mark = mark;
        this.mark1 = mark1;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {5,0},
                {3,1},
                {2,-5},
                {5,6},
                {4,8}
        });
    }
    @Test
    public void getGroup() {
        Group gr = Group.generate("Тест1");
        st.setGroup(gr);
        assertEquals(gr,st.getGroup());
    }

    @Test
    public void testToString() {
        assertEquals("Студент",st.toString());
    }

    @Test
    public void getFio() {
        assertEquals("Студент",st.toString());
    }

    @Test
    public void getId() {
        assertEquals(1000,st.getId());
    }

    @Test
    public void setGroup() {
        Group newgroup = Group.generate("Тест2");
        st.setGroup(newgroup);
        assertEquals("Тест2",st.getGroup().getTitle());
    }

    @Test
    public void addMarks() {
        try{
            st.addMarks(mark);
        }catch (BadMarkException e){
            e.printStackTrace();
        }
        assertNotEquals(0,st.averageMark());
    }

    @Test (expected = BadMarkException.class)
    public void addMarks2() throws BadMarkException{
        st.addMarks(mark1);
    }
    @Test
    public void averageMark() {
        try{
            for (int i =0;i<10;i++){
                st.addMarks(3);
                st.addMarks(2);
            }
        }catch (BadMarkException e){
            e.printStackTrace();
        }
        assertEquals(2.5,st.averageMark(),0.1);
    }
}