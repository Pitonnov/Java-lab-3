public class Main {
    public static void main(String[] args) {
        Deanery dnr = new Deanery();
        dnr.createStudents("students.json");
        dnr.createGroups("groups.json");
        dnr.fillGroups();
        dnr.electHeads();
        dnr.generateMarks();

        System.out.println();

        dnr.printMarks();
        dnr.printGroupsData();
        dnr.moveStudent(dnr.searchStudentInDeanery("1"), dnr.getGroups().get(1));

        System.out.println();

        dnr.changeHead("19", "АA");
        dnr.printGroupsData();

        System.out.println();

        dnr.expelStudents("3.3");
        dnr.printStat();
        dnr.studentsStatToJSON("studentsNew.json");
        dnr.groupsStatToJSON("groupsNew.json");
    }
}
