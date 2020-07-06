public class Task {
    public static void main(String[] args){
        Deanery deanery = new Deanery();
        deanery.importStudents("students.json");
        deanery.importGroup("groups.json");
        deanery.headInitialize();
        deanery.addMarksForAll();
        deanery.transferStudent(3, "Economic");
        deanery.deductStudent();
        deanery.writeToConsole();
        deanery.writeToFile("final.json");
    }
}
