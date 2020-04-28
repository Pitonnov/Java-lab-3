import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int randomGroup = 0;
        randomGroup = (int) (Math.random() * 2);
        Deanery dekanat = new Deanery("Groups.json");
        ArrayList<Group> groups = dekanat.getGroups();
        ArrayList<Student> students = dekanat.getStudents();
        Student newStudent = dekanat.createStudent("Разгильдяев Станислав Петрович");
        Student newStudent1 = dekanat.createStudent("Ерророва Стека Оверфлоевна");
        newStudent.enrolltoGroup(groups.get(randomGroup));
        newStudent1.enrolltoGroup(groups.get(randomGroup));
        System.out.println(groups);
        dekanat.addMarks();
        newStudent.getGroup().findStudentByFio(newStudent.getFio());
        newStudent.getGroup().findStudentById(newStudent.getId());
        newStudent1.getGroup().findStudentByFio(newStudent1.getFio());
        newStudent1.getGroup().findStudentById(newStudent1.getId());
        //The  newStudent1 decided to retire
        newStudent1.getGroup().expellStudentFromGroup(newStudent1);
        for (int i = 0; i < groups.size(); i++) {
            while (randomGroup == i) {
                randomGroup = (int) (Math.random() * 2);
            }
            int randomStudent = (int) (Math.random() * 90);
            dekanat.getAverageMarkInTheGroup(groups.get(i));
            dekanat.holdElection(groups.get(i));
            dekanat.tranferToAhotherGroup(students.get(randomStudent), groups.get(randomGroup));
        }
        dekanat.getTheWorsetStudentsInGroup();
        dekanat.expellTheWorsetStudents(dekanat.getTheWorsetStudentsInGroup());
        //This method forms new list of students who passed their session successfully
        dekanat.createNewJSONfile("NewGroups.json");
    }
}
