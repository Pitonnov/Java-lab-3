import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Random;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Deanery {
    private ArrayList<Student> students; // массив из ссылок на студентов
    private ArrayList<Group> groups; // массив групп

    public Deanery() {
        students = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void createStudents(String fileName) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject object = (JSONObject)parser.parse(reader);
            JSONArray studentsArr = (JSONArray) object.get("students");
            Iterator studentsIterator = studentsArr.iterator();
            Long id;
            String name;
            while (studentsIterator.hasNext()) {
                JSONObject st = (JSONObject) studentsIterator.next();
                id = (Long) st.get("id");
                name = (String) st.get("name");
                Student student = new Student (id.intValue(), name);
                students.add(student);
            }
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createGroups(String fileName) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)){
            JSONObject object = (JSONObject)parser.parse(reader);
            JSONArray groupsArr = (JSONArray) object.get("groups");
            Iterator groupsIterator = groupsArr.iterator();
            String title;
            while (groupsIterator.hasNext()) {
                JSONObject gr = (JSONObject) groupsIterator.next();
                title = (String) gr.get("title");
                Group group = new Group (title);
                groups.add(group);
            }
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void fillGroups() {
        if (students.isEmpty() || groups.isEmpty()) {
            return;
        }
        int amount = students.size() / groups.size(); // amount of students is group
        Iterator<Student> iterator = students.iterator();
        int i = 0; // student
        int j = 0; // group
        boolean isTail = false;
        while (iterator.hasNext()) {
            Student next = iterator.next();
            if (j >= groups.size()) {
                j = 0;
                isTail = true;
            }
            groups.get(j).addStudent(next); // add next student to the group
            if (!isTail){
                i++;
                if (amount == i) {
                    i = 0;
                    j++;
                }
            } else {
                j++;
            }
        }
    }

    public void generateMarks() {
        if (students.isEmpty()) {
            return;
        }
        Random random = new Random();
        Random randomMark = new Random();
        int numberOfMarks = 0;
        for (Student student: students) {
            numberOfMarks = random.nextInt(19) + 2; // amount of marks for each student
            for (int j = 0; j < numberOfMarks; j++) {
                int nextMark = randomMark.nextInt(4) + 2;
                student.addMark(nextMark);
            }
        }
    }

    public void moveStudent(Student student, Group group) {
        if (student.getGroup() != null) {
            student.getGroup().removeStudent(student.getId());
        }
        group.addStudent(student);
        System.out.println("Student " + student.getName() + " was moved to the group " +
                student.getGroup().getTitle());
    }

    public void expelStudents(String minMark) { // expel all students with average mark < minMark
        double minimumAverageMark = Double.parseDouble(minMark);
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student nextStudent = iterator.next();
            if (nextStudent.averageMark() < minimumAverageMark){
                System.out.print("Student " + nextStudent.getName() + " was expelled from the group " +
                        nextStudent.getGroup().getTitle() + " with average mark ");
                System.out.printf("%.2f\n", nextStudent.averageMark());
                nextStudent.getGroup().removeStudent(nextStudent.getId()); // remove student from group
                iterator.remove(); // remove student from deanery
            }
        }
    }

    public void electHeads() {
        if (students.isEmpty() || groups.isEmpty()) {
            return;
        }
        for (Group group: groups) {
            group.setHead();
            System.out.println("The elected head of " + group.getTitle() + " is " + group.getHead().getName());
        }
    }

    public void changeHead(String id, String groupTitle) {
        if (!students.contains(this.searchStudentInDeanery(id))) {
            System.out.println("Wrong student's ID");
            return;
        }
        if (!groups.contains(this.searchGroupInDeanery(groupTitle))) {
            System.out.println("Wrong group's title");
            return;
        }
        if (this.searchGroupInDeanery(groupTitle).getStudents().isEmpty()) {
            System.out.println("The group is empty");
            return;
        }
        if (!this.searchStudentInDeanery(id).getGroup().getTitle().equals(groupTitle)) {
            System.out.println("There is no such student with id " + id + " in this group");
            return;
        }
        Group group = this.searchGroupInDeanery(groupTitle);
        Student newHead = this.searchStudentInDeanery(id);
        group.setHead(newHead);
    }

    public Student searchStudentInDeanery(String studentId) {
        int id = Integer.parseInt(studentId);
        for(Student student: students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Group searchGroupInDeanery(String title) {
        for(Group group: groups) {
            if (group.getTitle().equals(title)) {
                return group;
            }
        }
        return null;
    }

    public void printMarks() {
        for (Student student: students) {
            student.printStudentData();
            student.printMarksForStudent();
        }
    }

    public void printGroupsData() {
        for (Group group: groups) {
            group.printGroupData();
        }
    }

    public void printStat() {
        for (Group group: groups) {
            group.printGroupData();
            group.printAverageMarkForGroup();
            group.printAverageMarkForEachStudentInGroup();
        }
    }

    public void studentsStatToJSON(String studentsFileName) {
        if (students.isEmpty() || groups.isEmpty()) {
            return;
        }
        JSONObject object = new JSONObject();
        JSONArray studs = new JSONArray();
        for (Student student: students){
            JSONObject st = new JSONObject();
            st.put("id", student.getId());
            st.put("name", student.getName());
            st.put("group", student.getGroup().getTitle());
            st.put("markAv",  String.format("%.2f", student.averageMark()));
            JSONArray marks = new JSONArray();
            marks.addAll(student.getMarks());
            st.put("marks", marks);

            studs.add(st);
        }
        object.put("students", studs);

        try (FileWriter writer = new FileWriter(studentsFileName)) {
            writer.write(object.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void groupsStatToJSON(String groupsFileName) {
        if (students.isEmpty() || groups.isEmpty()) {
            return;
        }
        JSONObject object = new JSONObject();
        JSONArray groupsArray = new JSONArray();
        for (Group group: groups){
            JSONObject gr = new JSONObject();
            gr.put("Title", group.getTitle());
            if (null == group.getHead()){
                gr.put("Head", "not elected yet");
            }
            else{
                gr.put("Head", group.getHead().getName());
            }
            gr.put("Amount", group.getStudents().size());
            gr.put("markAv",  String.format("%.2f", group.averageMarkInGroup()));
            groupsArray.add(gr);
        }
        object.put("students", groupsArray);

        try (FileWriter writer = new FileWriter(groupsFileName)) {
            writer.write(object.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}