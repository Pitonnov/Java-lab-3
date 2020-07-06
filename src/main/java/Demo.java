public class Demo {
    public Demo() {
    }

    public static void main(String[] args) {
        Deanery deanery = new Deanery();
        deanery.createGroupsWithStudents("groups.json");
        deanery.addMarks();
        deanery.sortToMarks();
        deanery.sortGroups();
        deanery.changeGroup(25, "Java");
        deanery.changeGroup("Веселков Адриан Трофимович", "Python");
        deanery.setHeads();
        deanery.expellStudents();
        System.out.println("Информация о группах: ");
        deanery.printGroups();
        System.out.println("Информация о студентах: ");
        deanery.printStudents();
        deanery.createJSON(true);
    }
}