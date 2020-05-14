import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Deanery deanery = new Deanery("Students.json","Groups.json");
        deanery.distributeToGroups();
        deanery.Exam();
        System.out.println(deanery.changeGroup("Ядерная энергетика и теплофизика","Ядерные физика и технологии",100));
        ArrayList<Student> heads = new ArrayList<>();
        heads = deanery.headchoice(4);
        for (Student i: heads){
            System.out.print(i.toString());
            System.out.print(", ");
        }
        System.out.println();
        System.out.println(deanery.ExeceptStudent(130).toString());
        deanery.Statistic();
    }
}
