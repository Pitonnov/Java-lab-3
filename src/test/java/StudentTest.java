import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;

public class StudentTest {

    @org.junit.Test
    public void checkGetId() {
        Student student1=new Student("Иванов Иван Иванович", 252577);
        assertEquals(252577,student1.getId());
    }

    @org.junit.Test
    public void checkGetFio() {
        Student student3=new Student("Сидоров Сергей Сергеевич", 252579);
        assertEquals("Сидоров Сергей Сергеевич", student3.getFio());
    }

    @org.junit.Test
    public void checkSetFio() {
        Student student2=new Student("Пертов Петр Петрович", 252578);
        student2.setFio("Лукоянов Перт Петрович");
        assertEquals("Лукоянов Перт Петрович",student2.getFio());
    }

    @org.junit.Test
    public void checkGetGroup() {
        Student student2=new Student("Пертов Петр Петрович", 252578);
        Group group1=new Group("GroupForTest");
        group1.addToGroup(student2);
        assertEquals(group1,student2.getGroup());
    }

    @org.junit.Test
    public void checkGetAssess() {
        Student student2=new Student("Пертов Петр Петрович", 252578);
        student2.addAssess(5);//add(5);
        student2.addAssess(4);
        student2.addAssess(5);

        ArrayList<Integer> exp=new ArrayList<>();
        exp.add(5);
        exp.add(4);
        exp.add(5);

        assertEquals(exp ,student2.getAssess());
    }

    @org.junit.Test
    public void checkAddToGroup() {
        Student student3 = new Student("Сидоров Сергей Сергеевич", 252579);
        Group group1 = new Group("GroupForTest");

        student3.addToGroup(group1);
        assertEquals(group1 ,student3.getGroup());
    }

    @org.junit.Test
    public void checkAddAssess() {
        Student student3 = new Student("Сидоров Сергей Сергеевич", 252579);

        student3.addAssess(5);
        assertEquals(5 ,student3.getAssess().get(0));
    }

    @org.junit.Test
    public void checkAverageAssess() {
        Student student3 = new Student("Сидоров Сергей Сергеевич", 252579);

        student3.addAssess(5);
        student3.addAssess(5);
        student3.addAssess(4);

        assertEquals(4.666666666666667 ,student3.averageAssess());
    }

    @org.junit.Test
    public void checkCompareTo() {
        ArrayList<Student> testSt=new ArrayList<Student>();
        ArrayList<Student> expSt=new ArrayList<Student>();

        testSt.add(new Student("Иванов Иван Иванович", 252577));
        testSt.get(0).addAssess(3);
        testSt.get(0).addAssess(4);
        testSt.add(new Student("Пертов Петр Петрович", 252578));
        testSt.get(1).addAssess(3);
        testSt.get(1).addAssess(5);
        testSt.add(new Student("Сидоров Сергей Сергеевич", 252579));
        testSt.get(2).addAssess(0);
        testSt.get(2).addAssess(5);

        expSt.add(testSt.get(2));
        expSt.add(testSt.get(0));
        expSt.add(testSt.get(1));

        Collections.sort(testSt);

        assertEquals(expSt,testSt);
    }

    @org.junit.Test
    public void checkToString() {
        Student student1=new Student("Иванов Иван Иванович", 252577);
        assertEquals("ID:252577 - Иванов Иван Иванович" ,student1.toString());
    }

    @org.junit.Test
    public void checkEquals() {
        Student student5 = new Student("Иванов Иван Иванович", 252580);
        Student student6 = new Student("Иванов Иван Иванович", 252580);
        assertEquals(true,student5.equals(student6));
    }
}