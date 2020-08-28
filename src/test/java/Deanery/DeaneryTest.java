package Deanery;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DeaneryTest {

    Deanery den = new Deanery();

    @BeforeAll
    public static void setup(){
        Deanery den = new Deanery();
        den.getGroups().add(new Group (10001,"АСУ-20-01"));
        den.getGroups().add(new Group (10002,"АСУ-20-02"));
        den.getStudents().add(new Student(1000001, "Янушкене","Богдан","Елисеевич"));
        den.getStudents().add(new Student(1000002, "Соломахина","Лариса","Родионовна"));
        den.getStudents().add(new Student(1000003, "Ерохин","Назар","Артемиевич"));
        den.getStudents().add(new Student(1000004, "Цызырева","Влада","Елизаровна"));
        den.getStudents().add(new Student(1000005, "Дмитриев","Вадим","Андроникович"));
        den.getStudents().add(new Student(1000006, "Богачёв","Гавриил","Карлович"));
        den.getStudents().add(new Student(1000007, "Ямов","Виктор","Валерьянович"));
        den.getStudents().add(new Student(1000008, "Косинова","Полина","Кузьмевна"));
        den.getStudents().add(new Student(1000009, "Ханцев","Федор","Георгиевич"));
        den.getStudents().add(new Student(1000010, "Эсаулова","Мирослава","Андрияновна"));
        den.getStudents().add(new Student(1000011, "Сидоров","Иннокентий","Юриевич"));
    }

    @Test
    public void getStudents() {
        assert(!den.getStudents().isEmpty());
    }

    @Test
    public void getGroups() {
        assertEquals(2, den.getGroups().size());
    }

    @Test
    public void setExamination() {
        den.setExamination(5);
        den.findSt("Цызырева").addMark(3);
        den.findSt("Сидоров").addMark(3);
        assertEquals(0, den.expellingForBadGrades(2));
    }

    @Test
    public void enrollStudents() {
        assertEquals(den.getStudents().size(),den.enrollStudents(8));
    }

    @Test
    public void electionHeadmen() {
        den.electionHeadmen();
        assertEquals(0, den.getGroups().stream().filter(group -> (group.getHead() == null)).count());
    }

    @Test
    public void findSt() {
        assertEquals(1000007, den.findSt(1000007).getId());
    }

    @Test
    public void testFindSt() {
        assertEquals("Ямов Виктор Валерьянович", den.findSt("Ямов").getFIO());
    }

    @Test
    public void findGr() {
        assertEquals(10002, den.findGr(10002).getId());
    }

    @Test
    public void testFindGr() {
        assertEquals("АСУ-20-02", den.findGr("АСУ-20-02").getTitle());
    }

    @Test
    public void fullExpelStudent() {
        int i =  den.getStudents().size();
        den.fullExpelStudent(den.findSt("Соломахина"));
        assertEquals(i-1, den.getStudents().size());
    }

    @Order(0)
    @Test
    public void expellingForBadGrades() {
        int i = den.getStudents().size();
        den.getStudents().forEach(student -> {student.addMark(5);});
        den.findSt("Ямов").addMark(1);
        den.findSt("Косинова").addMark(1);
        den.expellingForBadGrades(4);
        assertEquals(i-2, den.getStudents().size());
    }

    @Test
    public void studentTransfer() {
        den.studentTransfer(den.findSt("Эсаулова"), den.findGr("АСУ-20-01"));
        assertEquals(den.findGr("АСУ-20-01"), den.findSt("Эсаулова").getGroup());
    }

    @Test
    public void exportDataToJASON() {
        File file = new File("testDeanery.json");
        den.exportDataToJASON(file);
        assert(file.exists());
    }
}