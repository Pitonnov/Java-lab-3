import org.junit.Test;
import org.junit.Assert;

public class StudentTest {

    @Test
    public void addMark() {
        Student st1 = new Student(537, "Chuck Norris");
        Student st2 = new Student(538, "Bruce Lee");
        st1.addMark(3);
        st2.addMark(4);
        Integer st1Mark = 3, st2Mark = 4;
        Assert.assertEquals(st1Mark, st1.getMarks().get(0));
        Assert.assertEquals(st2Mark, st2.getMarks().get(0));
    }

    @Test
    public void averageMark() {
        Student st = new Student(537, "Chuck Norris");
        st.addMark(3);
        st.addMark(5);
        Assert.assertEquals(4.0D, st.averageMark(), 0.001D);
    }
}