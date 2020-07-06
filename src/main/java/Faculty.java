
public class Faculty {

    public static void main(String[] args) {
        Deanery deanery = Deanery.getInstance("FIT");
        Dialog.deanery = deanery;
        deanery.createStudentsAndGroups();
        Display.write("\nList of Java group's students:");
        deanery.showStudentList("Java");
        Display.write("Check heads of groups:");
        deanery.showGroupHeads();
        Display.write("\nDo election of heads:");
        deanery.doHeadElection();
        Student student = deanery.getGroup("Java").getRandomStudent();
        Display.write("\nTransfer " + student.toString() + " group into Python group");
        deanery.transferring(student, "Python");
        Display.write("\n Add 5 marks to each student:");
        deanery.addRatings(5);
        deanery.showProgress();
        Display.write("\nPoor students (AR < 3): ");
        deanery.showPoorStudent();
        Display.write("\n*SAVE PROGRESS*\n");
        deanery.saveProgress();
        Display.write("\nExpel all poor students:");
        deanery.expelPoorStudents();
        Display.write("\nActual list of students, and their progress:");
        deanery.showProgress();
        Display.write("\n*LOAD PROGRESS*\n");
        deanery.loadProgress();
        Display.write("\nActual list of students, and their progress after the loading:");
        deanery.showProgress();
        //uncomment to enable dialog
        //Dialog.run();
    }
}

