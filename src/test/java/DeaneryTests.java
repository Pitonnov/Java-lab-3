import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotSame;

import University.Deanery;
import University.Student;
import University.Group;



public class DeaneryTests {
    public  void readGroupsFile()  throws  Exception
    {
        Deanery.createGroupsWithStudentsFromFile("GeneratedGroups.json");
    }
    @Test
    public void transferStudent() throws Exception
    {
        readGroupsFile();
        Student student=Deanery.getStudent(10);
        Group nextGroup=Deanery.getGroup(6);
        Deanery.transferStudentToAnotherGroup(student,nextGroup);
        assertEquals(nextGroup,student.getGroup());
    }
    @Test
    public  void expelation() throws Exception
    {
        int index=20;
        readGroupsFile();
        Student student=Deanery.getStudent(index);
        Group group=student.getGroup();
        Deanery.expelStudent(student);
        assertNotSame(student,Deanery.getStudent(index));
        assertNotSame(student,group.getStudentByKey(student.getID()));
    }
    @Test
    public  void expelStudentsWithBadMarks() throws  Exception
    {
        readGroupsFile();
        for(float k=2;k<=4.5;k++)
        {
            float lowerMark=k+0.5f;
            Student student = new Student("A", "B", "C");
            for (int i = 0; i < 100; i++)
                student.addMark(k + i * 0.004f);
            Deanery.addStudent(student,Deanery.getGroup(5));
            Deanery.expelStudentsForBadMarksInAllGroups(lowerMark);
            assertEquals(null, Deanery.getStudentById(student.getID()));
            for(int i=0;i<Deanery.getTotalStudentsNumber();i++)
            {
                System.out.println("Lower mark = "+lowerMark+" S : "+Deanery.getStudent(i).getAverageMark());
                assertTrue(Deanery.getStudent(i).getAverageMark() >= lowerMark);
            }
        }
    }
}
