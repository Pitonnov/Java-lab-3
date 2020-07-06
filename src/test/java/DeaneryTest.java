import org.junit.Assert;
import org.junit.Test;

public class DeaneryTest {
    public DeaneryTest() {
    }

    @Test
    public void createStudents() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        Assert.assertEquals(30, dnr.getStudents().size());
    }

    @Test
    public void createGroups() {
        Deanery dnr = new Deanery();
        dnr.createGroups("groups.json");
        Assert.assertEquals(3, dnr.getGroups().size());
    }

    @Test
    public void fillGroups() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        Assert.assertEquals(10, (dnr.getGroups().get(0)).getStudents().size());
        Assert.assertEquals(10, (dnr.getGroups().get(1)).getStudents().size());
        Assert.assertEquals(10, (dnr.getGroups().get(2)).getStudents().size());
    }

    @Test
    public void moveStudent() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        dnr.moveStudent(dnr.searchStudentInDeanery("7"), dnr.getGroups().get(0));
        Assert.assertEquals("AA", dnr.searchStudentInDeanery("7").getGroup().getTitle());
        dnr.moveStudent(dnr.searchStudentInDeanery("28"), dnr.getGroups().get(2));
        Assert.assertEquals("CC", dnr.searchStudentInDeanery("28").getGroup().getTitle());
    }

    @Test
    public void expelStudents() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        dnr.generateMarks();
        double minMark = 3.3D;
        dnr.expelStudents("3.3");
        for (Student nextStudent : dnr.getStudents()) {
            Assert.assertTrue(nextStudent.averageMark() >= minMark);
        }
    }

    @Test
    public void electHeads() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        dnr.electHeads();
        for (int i = 0; i < dnr.getGroups().size(); i++) {
            Assert.assertNotNull((dnr.getGroups().get(i)).getHead());
        }
    }

    @Test
    public void changeHead() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        dnr.electHeads();
        dnr.changeHead("15", "BB");
        Assert.assertEquals(15, (dnr.getGroups().get(1)).getHead().getId());
        dnr.changeHead("28", "CC");
        Assert.assertEquals(28, (dnr.getGroups().get(2)).getHead().getId());
    }

    @Test
    public void searchStudentInDeanery() {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        Student st = dnr.getStudents().get(0);
        Assert.assertSame(st, dnr.searchStudentInDeanery("1"));
        st = dnr.getStudents().get(29);
        Assert.assertSame(st, dnr.searchStudentInDeanery("30"));
    }

    @Test
    public void searchGroupInDeanery() {
        Deanery dnr = new Deanery();
        dnr.createGroups("groups.json");
        Group gr = dnr.getGroups().get(0);
        Assert.assertSame(gr, dnr.searchGroupInDeanery("AA"));
        gr = dnr.getGroups().get(2);
        Assert.assertSame(gr, dnr.searchGroupInDeanery("CC"));
    }
}