import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

public class DeaneryTest {

    Deanery deaneryTest = new Deanery();

    @org.junit.Test
    public void checkGetCountID() {
        assertEquals(0, deaneryTest.getCountID());
    }

    @org.junit.Test
    public void checkSetCountID() {
        deaneryTest.setCountID(555);
        assertEquals(555, deaneryTest.getCountID());
    }


    @org.junit.Test
    public void checkMakeGroupsWithStudents() {
        deaneryTest.setCountID(0);
        assertEquals(9, deaneryTest.makeGroupsWithStudents("test.json"));
        assertEquals(9, deaneryTest.getCountID());
    }

    @org.junit.Test
    public void checkHeadInGroup() {
        deaneryTest.makeGroupsWithStudents("test.json");

        assertEquals(3, (deaneryTest.headInGroup()));
        assertNotEquals(null, deaneryTest.allGroup.get(0).getHead());
        assertNotEquals(null, deaneryTest.allGroup.get(1).getHead());
        assertNotEquals(null, deaneryTest.allGroup.get(2).getHead());
    }

    @org.junit.Test
    public void checkAddMarks() {
        deaneryTest.makeGroupsWithStudents("test.json");

        assertEquals(27, deaneryTest.addMarks(3));
    }

    @org.junit.Test
    public void checkTransferToNextGroup() {
        deaneryTest.makeGroupsWithStudents("test.json");

        deaneryTest.transferToNextGroup("Ананьев Иван Александрович", "Group-3");   //);
        assertEquals(new ArrayList<>(), deaneryTest.allGroup.get(0).searchStudent("Ананьев Иван Александрович"));
        //assertEquals("Ананьев Иван Александрович", deaneryTest.allGroup.get(2).searchStudent("Ананьев Иван Александрович").get(0).getFio());
    }

    @org.junit.Test
    public void checkDeductionStudents() {

        deaneryTest.makeGroupsWithStudents("test.json");

        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(2);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(4);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(0);

        assertEquals(2, deaneryTest.deductionStudents(3.0));
        assertEquals(new ArrayList<>(), ((deaneryTest.allGroup.get(1)).searchStudent("Андреева Вера Борисовна")));
        assertEquals(new ArrayList<>(), ((deaneryTest.allGroup.get(2)).searchStudent("Белов Платон Маркович")));
    }

    @org.junit.Test
    public void checkMarksStatisticsSmart() {
        deaneryTest.makeGroupsWithStudents("test.json");

        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(2);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(4);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(0);

        ArrayList<Student> expStudents = new ArrayList<Student>();
        expStudents.add((deaneryTest.allGroup.get(2)).studentsGroup.get(0));
        expStudents.add((deaneryTest.allGroup.get(0)).studentsGroup.get(1));

        assertEquals(expStudents, deaneryTest.marksStatistics(2,4.8,5.0,true));
    }

    @org.junit.Test
    public void checkMarksStatisticsNotSmart() {
        deaneryTest.makeGroupsWithStudents("test.json");

        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(1)).addAssess(5);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(0)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(0)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(2);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(4);
        ((deaneryTest.allGroup.get(1)).studentsGroup.get(2)).addAssess(5);

        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(0)).addAssess(5);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(1)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(3);
        ((deaneryTest.allGroup.get(2)).studentsGroup.get(2)).addAssess(0);

        ArrayList<Student> expStudents = new ArrayList<Student>();
        expStudents.add((deaneryTest.allGroup.get(1)).studentsGroup.get(1));
        expStudents.add((deaneryTest.allGroup.get(2)).studentsGroup.get(1));

        assertEquals(expStudents, deaneryTest.marksStatistics(2,2.5,3.0,false));
    }

    @org.junit.Test
    public void checkGroupsDataToFile() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        File file=new File("Groups_" + timeStamp + ".json");

        deaneryTest.groupsDataToFile();
        assertEquals(true, file.isFile());
    }

    @org.junit.Test
    public void checkAcademicPerformanceToFile() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        File file=new File("AcademicPerformance_" + timeStamp + ".json");

        deaneryTest.academicPerformanceToFile();
        assertEquals(true, file.isFile());
    }

    @org.junit.Test
    public void checkGroupHeadsToFile() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        File file=new File("headsGroups_" + timeStamp + ".json");

        deaneryTest.groupHeadsToFile();
        assertEquals(true, file.isFile());
    }
}