import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student st = Student.generate(1000,"Студент");

    @Test
    void getGroup() {
        Group gr = Group.generate("Тест1");
        st.setGroup(gr);
        assertEquals(gr,st.getGroup());
    }

    @Test
    void testToString() {
        assertEquals("Студент",st.toString());
    }

    @Test
    void getFio() {
        assertEquals("Студент",st.toString());
    }

    @Test
    void getId() {
        assertEquals(1000,st.getId());
    }

    @Test
    void setGroup() {
        Group newgroup = Group.generate("Тест2");
        st.setGroup(newgroup);
        assertEquals("Тест2",st.getGroup().getTitle());
    }

    @ParameterizedTest
    @ValueSource(ints = {5,3,2,5,4})
    void addMarks(int args) {
        try{
            st.addMarks(args);
        }catch (BadMarkException e){
            e.printStackTrace();
        }
        assertNotEquals(0,st.averageMark());
    }
    @ParameterizedTest
    @ValueSource(ints = {0,1,-5,6,8})
    void addMarks2(int args) throws BadMarkException{
        assertThrows(BadMarkException.class, () -> st.addMarks(args));
    }
    @Test
    void averageMark() {
        try{
            for (int i =0;i<10;i++){
                st.addMarks(3);
                st.addMarks(2);
            }
        }catch (BadMarkException e){
            e.printStackTrace();
        }
        assertEquals(2.5,st.averageMark());
    }
}