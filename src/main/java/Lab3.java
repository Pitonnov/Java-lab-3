public class Lab3 {
    public static void main(String[] args) {
        try {
            String groupsFileDirection;
            String studentsFileDirection;
            String outputFileDirection;
            if (args.length != 0) {
                studentsFileDirection = args[0];
                groupsFileDirection = args[1];
                outputFileDirection = args[2];
            }
            else {
                studentsFileDirection = "./src/main/resources/students.txt";
                groupsFileDirection = "./src/main/resources/groups.txt";
                outputFileDirection = "./src/main/resources/out.txt";
            }
            Password.changePassword("1234","777");
            Deanery deanery1 = Deanery.createNewDeanery("777");
            if (deanery1 == null) {
                throw new DeaneryExceptions.NullDeaneryException();
            }
            deanery1.createStudentsFromFile(studentsFileDirection, "777");
            deanery1.createGroupsFromFile(groupsFileDirection, "777");
            deanery1.addAllStudentsToRandomGroups("777");
            deanery1.electHeads("777");
            deanery1.addRandomMarks(20,"777");
            deanery1.outputToDisplay("ALL","777");
            deanery1.ratingStatistics();
            deanery1.removeStudentsByStatistics(MARKS.UNSATISFACTORILY,"777");
            deanery1.outputToFile(outputFileDirection, "ALL", "777");
        }
        catch (DeaneryExceptions.NullDeaneryException e){
            System.out.println("Deanery wasn't created");
        }
    }
}