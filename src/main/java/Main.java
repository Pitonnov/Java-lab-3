public class Main {
    public static void main(String[] args) {
        Deanery deanery = new Deanery();
        deanery.createStudents();
        deanery.studentChangedGroup();
        deanery.sendDownStudent();
        deanery.chooseHead();
        deanery.printListOfStudent();
        deanery.printAvarageMarkForGroup();
        deanery.jsonOut();
    }
}
