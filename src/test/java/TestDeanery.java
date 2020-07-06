import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDeanery {

    Deanery deanery = new Deanery();

    @Before
    public void before() {
        deanery.groupsCreation();
        deanery.studentsCreation();
    }

    @Test
    public void testGroupsCreation() {
        assertEquals("Ядерная медицина", deanery.groups.get(0).getTitle());
    }

    @Test
    public void testStudentsCreation() {
        assertEquals("Белоусова Юна Григорьевна", deanery.groups.get(0).getStudents().get(0).getFio());
    }

    @Test
    public void testAddGradeDeanery() {
        deanery.addGradeDeanery();
        int s = 1;
        if (deanery.getStudent().get(0).averageRating() == 0) {
            s = 0;
        }
        ;
        assertEquals(1, s);
    }

    @Test
    public void testChangeStudentGroup() {
        deanery.changeStudentGroup("Исакова Валерия Платоновна", "Атомная теплофизика");
        assertEquals("Атомная теплофизика", deanery.getGroup().get(1).searchStudent("Исакова Валерия Платоновна").getGroup().getTitle());
        assertEquals(null, deanery.getGroup().get(0).searchStudent("Исакова Валерия Платоновна"));
    }

    @Test
    public void testExcludeStudentGroup() {
        deanery.excludeStudentGroup();
        int k = 1;
        for (int i = 0; i < deanery.students.size(); i++) {
            if (deanery.students.get(i).averageRating() < 3) {
                k = 0;
            }
            assertEquals(1, k);
        }
    }

    @Test
    public void testChooseHead() {
        deanery.chooseHead();
        int k = 1;
        for (int i = 0; i < deanery.getGroup().size(); i++) {
            if (deanery.getGroup().get(i).getHead() == null) {
                k = 0;
            }
            assertEquals(1, k);
        }
    }

}
