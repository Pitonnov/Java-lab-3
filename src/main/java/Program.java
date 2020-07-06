public class Program {
    public static void main(String[] args) {

        Deanery deanery = new Deanery();

        deanery.makeGroupsWithStudents("deanery.json");
        deanery.groupsDataToConsole(); //print to console

        deanery.headInGroup();
        deanery.groupHeadsToConsole();

        deanery.transferToNextGroup("Алехина София Савельевна", "Group-3");
        deanery.groupsDataToConsole(); //print to console

        deanery.addMarks(5);
        deanery.academicPerformanceToConsole();


        deanery.marksStatistics(5, 4.0, 5.0, true);
        deanery.marksStatistics(3, 0, 2.99, false);


        deanery.deductionStudents(2.99);
        deanery.groupsDataToConsole(); //print to console

        deanery.transferToNextGroup("Барашков Мирон Викторович", "Group-2");
        deanery.groupsDataToConsole(); //print to console

        deanery.groupsDataToFile();
        deanery.academicPerformanceToFile();
        deanery.groupHeadsToFile();
    }
}
