import org.junit.Test;
import org.junit.Assert;

public class GroupTest {

    @Test
    public void addStudent() {
        Group gr = new Group("test");
        Student st = new Student(537, "Chuck Norris");
        gr.addStudent(st);
        Assert.assertTrue(gr.getStudents().contains(st));
        Assert.assertEquals(gr, st.getGroup());
    }

    @Test
    public void setHead() {
        Group gr = new Group("test");
        Student st = new Student(537, "Chuck Norris");
        gr.addStudent(st);
        gr.setHead(st);
        Assert.assertEquals(st, gr.getHead());
    }

    @Test
    public void randSetHead() {
        Group gr = new Group("test");
        Student st = new Student(537, "Chuck Norris");
        gr.addStudent(st);
        gr.setHead();
        Assert.assertNotNull(gr.getHead());
    }

    @Test
    public void searchStudent() {
        Group gr = new Group("test");
        Student st1 = new Student(537, "Chuck Norris");
        Student st2 = new Student(538, "Bruce Lee");
        gr.addStudent(st1);
        gr.addStudent(st2);
        Assert.assertEquals(st1, gr.searchStudent(537));
        Assert.assertEquals(st2, gr.searchStudent(538));
    }

    @Test
    public void averageMarkInGroup() {
        Group gr = new Group("test");
        Student st1 = new Student(537, "Chuck Norris");
        Student st2 = new Student(538, "Bruce Lee");
        st1.addMark(4);
        st2.addMark(5);
        gr.addStudent(st1);
        gr.addStudent(st2);
        double exp = 4.5D;
        Assert.assertEquals(exp, gr.averageMarkInGroup(), 0.001D);
    }

    @Test
    public void removeStudent() {
        Group gr = new Group("test");
        Student st = new Student(537, "Chuck Norris");
        gr.addStudent(st);
        gr.removeStudent(537);
        Assert.assertTrue(gr.getStudents().isEmpty());
    }
}