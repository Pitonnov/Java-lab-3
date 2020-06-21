import Deanery.*;

import java.io.File;

public class Lab3 {
    public static void main(String[] args) {
        Deanery den = new Deanery();
        den.settingStudents("/students.json");
        den.settingGroups("/groups.json");
        den.enrollStudents(27);
        den.setExamination(13);
        den.electionHeadmen();
        System.out.println("Отчислено "+den.expellingForBadGrades(4)+" студентов");
        den.printAllInfo("mark");
        den.exportDataToJASON(new File("deanery.json"));
    }
}