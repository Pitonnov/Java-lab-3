import com.google.gson.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Deanery {
    private static boolean exist;

    private String faculty;
    private int ID = 1;
    private HashMap<Student, StudentControl> students = new HashMap<>();
    private HashMap<String, GroupControl> groups = new HashMap<>();


    private Deanery(String faculty){
        this.faculty = faculty;
        Dialog.deanery = this;
        Display.write("The " + faculty + " faculty has created\n");
    }
    //pattern singleton is used
    public static Deanery getInstance(String faculty){
        if (!exist) {
            exist = true;
            return new Deanery(faculty);
        }
        else {
            Display.write("Error! The Deanery is already exist");
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deanery deanery = (Deanery) o;
        return faculty.equals(deanery.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty);
    }
    //provide unique id for created student
    private int getID(){
        return ID++;
    }

    public Group createGroup(String title){
        if (!isValidName(title)) {
            Display.write("Name '" + title + "' is invalid");
            return null;
        }
        GroupControl groupControl = Group.getInstance(this, title);
        groups.put(groupControl.getGroup().getTitle(), groupControl);
        return groupControl.getGroup();
    }
    public Student createStudent(String name){
        if (!isValidName(name)) {
            Display.write("Name '" + name + "' is invalid");
            return null;
        }
        int id = getID();
        StudentControl studentControl = Student.getInstance(this, name, id);
        students.put(studentControl.getStudent(), studentControl);
        return studentControl.getStudent();
    }

    public Group enrolling(Student student, String title){
        if (student == null)
            return null;
        if (!isValidName(title)){
            Display.write(ErrMassage.enrollingFailed() + ErrMassage.invalidName(title));
            return null;
        }
        if (!isStudentRegistered(student)) {
            Display.write(ErrMassage.enrollingFailed() + ErrMassage.unregisteredStudent(student));
            return null;
        }
        if (isStudentEnrolled(student)){
            Display.write(ErrMassage.enrollingFailed() + ErrMassage.enrolledStudent(student));
            return null;
        }
        if (!isGroupRegistered(title)) {
            Display.write(ErrMassage.enrollingFailed() + ErrMassage.unregisteredGroup(title));
            return null;
        }
        Display.write(student.toString() + " is enrolled into " + title + " group");
        students.get(student).setGroup(groups.get(title).getGroup());
        return groups.get(title).addStudent(student);
    }

    public boolean setStudentRating(Student student, int value){
        if (!isStudentRegistered(student)) {
            Display.write(ErrMassage.ratingFailed() + ErrMassage.unregisteredStudent(student));
            return false;
        }
        if (!isStudentEnrolled(student)){
            Display.write(ErrMassage.ratingFailed() + ErrMassage.unregisteredStudent(student));
            return false;
        }
        if (value < 1 || value >5) {
            Display.write(ErrMassage.ratingFailed() + ErrMassage.invalidMark());
            return false;
        }
        students.get(student).getRating().add(value);
        return true;
    }

    public void addRatings(){
        Random random = new Random();
        for (Map.Entry<Student, StudentControl> student : students.entrySet())
            if (student.getValue().getGroup() != null)
                student.getValue().getRating().add(random.nextInt(5) + 1);
    }
    public void addRatings(int number){
        for (int i = 0; i < number; i++)
            addRatings();
    }
    public void deleteAllMarks(Student student){
        if (students.containsKey(student))
            students.get(student).getRating().clear();
    }

    public boolean transferring(Student student, String title){
        if (title == null || title.isEmpty()){
            Display.write("Invalid title of a group");
            return false;
        }
        if (!isStudentRegistered(student)) {
            Display.write(ErrMassage.changingGroupFailed() + ErrMassage.unregisteredStudent(student));
            return false;
        }
        if (!isStudentEnrolled(student)) {
            Display.write(ErrMassage.changingGroupFailed() + ErrMassage.unenrolledStudent(student));
            return false;
        }
        if (!isGroupRegistered(title)) {
            Display.write(ErrMassage.changingGroupFailed() + ErrMassage.unregisteredGroup(title));
            return false;
        }
        students.get(student).setGroup(groups.get(title).getGroup());
        groups.get(student.sayGroup()).expelling(student);
        groups.get(title).addStudent(student);
        Display.write(student.toString() + " is transferred into " + getGroup(title).toString());
        groups.get(student.sayGroup()).expelling(student);
        return true;
    }

    public boolean expelStudent(Student student){
        if (student == null)
            return false;
        if (!isStudentRegistered(student)) {
            Display.write(ErrMassage.expellingStudentFailed() + ErrMassage.unregisteredStudent(student));
            return false;
        }
        Display.write(student.toString() + " is expelled");
        if (isStudentEnrolled(student))
            groups.get(student.sayGroup()).expelling(student);
        students.remove(student);
        return true;
    }

    public void expelPoorStudents(){
        for (Student student : findPoorStudent())
            expelStudent(student);
    }

    public void doHeadElection(){
        for (Map.Entry<String, GroupControl> group : groups.entrySet()){
            group.getValue().getGroup().headElection();
        }
    }

    public void showStudentList(){
        Display.write("Full list of students :");
        for (Map.Entry<Student, StudentControl> student : students.entrySet()){
            Display.write(student.getKey().toString());
        }
        Display.write("");
    }
    public void showStudentList(String title){
        for (Student student : groups.get(title).getStudents())
            Display.write(student.toString());
        Display.write("");
    }
    public void showGroupList(){
        for (Map.Entry<String, GroupControl> group : groups.entrySet()){
            Display.write(group.getKey());
        }
        Display.write("");
    }
    public void showGroupHeads(){
        for (Map.Entry<String, GroupControl> group : groups.entrySet())
            Display.write(group.getValue().getGroup().whoIsHead());
    }
    public void showPoorStudent(){
        for (Student student : findPoorStudent())
            Display.write(student.toString() + " , AR : " + student.averageRating());
    }
    public void showStudentProgress(Student student){
        Display.write(student.toString() + ", AR = " + student.averageRating() + ", marks: " + student.markList());
    }
    public void showProgress(){
        for (Map.Entry<Student, StudentControl> student : students.entrySet())
            showStudentProgress(student.getKey());
    }
    public boolean findStudent(String name){
        if (name == null || name.isEmpty()){
            Display.write("You must give student name");
            return false;
        }
        String foundStudents = "";
        for (Map.Entry<Student, StudentControl> student : students.entrySet()) {
            if (student.getKey().getName().contains(name))
                foundStudents += (student.getKey().toString() + "\n");
        }
        if (!foundStudents.isEmpty()) {
            Display.write(foundStudents);
            return true;
        }
        else {
            Display.write("Nobody is found");
            return false;
        }
    }
    public boolean findStudent(int id){
        if (id <= 0) {
            Display.write("Invalid id");
            return false;
        }
        for (Map.Entry<Student, StudentControl> student : students.entrySet()) {
            if (student.getKey().getId() == id) {
                Display.write(student.getKey().toString());
                return true;
            }
        }
        Display.write("Nobody is found");
        return false;
    }
    private ArrayList<Student> findPoorStudent(){
        ArrayList<Student> poorStudents = new ArrayList<>();
        for (Map.Entry<Student, StudentControl> student : students.entrySet()){
            if (isStudentEnrolled(student.getKey()) && student.getKey().averageRating() < 3)
                poorStudents.add(student.getKey());
        }
        return poorStudents;
    }
    public Student getStudent(int id){
        if (id <= 0 || id > ID) {
            Display.write("Invalid id");
            return null;
        }
        for (Map.Entry<Student, StudentControl> student : students.entrySet()){
            if (student.getKey().getId() == id)
                return student.getKey();
        }
        Display.write("Student with id '" + id + "' isn't found");
        return null;
    }
    public Group getGroup(String title){
        if (title == null || title.isEmpty()) {
            Display.write("You must give title of group");
            return null;
        }
        if (isGroupRegistered(title))
            return groups.get(title).getGroup();
        else
            Display.write("Group '" + title + "' isn't found");
        return null;
    }
    public int getNumberOfStudents(){
        return students.size();
    }

    private boolean isStudentRegistered(Student student){
        return students.containsKey(student);
    }
    private boolean isStudentEnrolled(Student student){
        return students.get(student).getGroup() != null;
    }
    private boolean isGroupRegistered(String title){
        return groups.containsKey(title);
    }
    private boolean isValidName(String name){
        return name != null && !name.isEmpty();
    }

    //save state in file using JSON
    public void saveProgress(){
        try{
            BufferedWriter bw = Files.newBufferedWriter(Paths.get(this.getClass().getResource("Data").toURI()));
            bw.write(getJSONDeanery());
            bw.write("{\"numberOfStudents\":" + this.students.size() +"}");
            for (Map.Entry<Student, StudentControl> student : students.entrySet()){
                bw.write(getJSONStudent(student.getValue()));
            }
            bw.write("{\"numberOfGroups\":" + this.groups.size() +"}");
            for (Map.Entry<String, GroupControl> group : groups.entrySet()){
                bw.write(getJSONGroup(group.getValue()));
            }
            bw.flush();
            bw.close();
        } catch(URISyntaxException ex) {
            ex.printStackTrace();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private String getJSONDeanery(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("faculty", faculty);
        jsonObject.addProperty("ID", ID);
        return jsonObject.toString();
    }
    private String getJSONGroup(GroupControl group){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", group.getGroup().getTitle());
        int headID;
        if (group.getHead() == null)
            headID = 0;
        else
            headID = group.getHead().getId();
        jsonObject.addProperty("headID", headID);
        ArrayList<Integer> listID = new ArrayList<Integer>();
        for (Student student : group.getStudents())
            listID.add(student.getId());
        jsonObject.add("listID", new GsonBuilder().create().toJsonTree(listID).getAsJsonArray());
        return jsonObject.toString();
    }
    private String getJSONStudent(StudentControl student){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", student.getStudent().getName());
        jsonObject.addProperty("id", student.getStudent().getId());
        jsonObject.add("rating", new GsonBuilder().create().toJsonTree(student.getRating()).getAsJsonArray());
        return jsonObject.toString();
    }
    //load saved state
    public void loadProgress() {
        JsonStreamParser jsonStreamParser = new JsonStreamParser(new InputStreamReader(this.getClass().getResourceAsStream("Data")));
        loadDeanery(jsonStreamParser.next());
        students.clear();
        int numberOfStudents = jsonStreamParser.next().getAsJsonObject().get("numberOfStudents").getAsInt();
        for (int i = 0; i < numberOfStudents; i++){
            loadStudent(jsonStreamParser.next());
        }
        groups.clear();
        int numberOfGroups = jsonStreamParser.next().getAsJsonObject().get("numberOfGroups").getAsInt();
        for (int i = 0; i < numberOfGroups; i++){
            loadGroup(jsonStreamParser.next());
        }
        enrollStudents();
    }
    private void loadDeanery(JsonElement jsonElement){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        faculty = jsonObject.get("faculty").getAsString();
        ID = jsonObject.get("ID").getAsInt();
        Display.write("Deanery loaded " + faculty + ID);
    }
    private void loadStudent(JsonElement jsonElement){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        StudentControl studentControl = Student.getInstance(this, jsonObject.get("name").getAsString(), jsonObject.get("id").getAsInt());
        JsonArray rt = jsonObject.get("rating").getAsJsonArray();
        for (int i = 0; i < rt.size(); i++){
            studentControl.getRating().add(rt.get(i).getAsInt());
        }
//        for (JsonElement mark :jsonObject.get("rating").getAsJsonArray())
//            studentControl.getRating().add(mark.getAsInt());
        students.put(studentControl.getStudent(), studentControl);
    }
    private void loadGroup(JsonElement jsonElement){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        GroupControl groupControl = Group.getInstance(this, jsonObject.get("title").getAsString());
        int headID = jsonObject.get("headID").getAsInt();
        groups.put(groupControl.getGroup().getTitle(), groupControl);
        for (JsonElement studentID : jsonObject.get("listID").getAsJsonArray()) {
            groupControl.addStudent(this.getStudent(studentID.getAsInt()));
            if (studentID.getAsInt() == headID)
                groupControl.setHead(this.getStudent(studentID.getAsInt()));
        }
    }
    private void enrollStudents(){
        for (Map.Entry<String, GroupControl> groupControl : groups.entrySet()){
            for (Student student : groupControl.getValue().getStudents()){
                students.get(student).setGroup(groupControl.getValue().getGroup());
            }
        }
    }
    //creating default set of group and students, and enroll them
    public void createStudentsAndGroups(){
        Display.write("Creating of students, groups. Student enrolling:\n");
        creatingStudents();
        creatingGroups();
    }
    private void creatingStudents(){
        ID = 1;
        students.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Deanery.class.getResourceAsStream("Names")));
            while (br.ready()){
                this.createStudent(br.readLine());
            }
            br.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private void enrollingStudents(ArrayList<String> groupTitle){
        int i = 0;
        for (Map.Entry<Student, StudentControl> student : students.entrySet()){
            enrolling(student.getKey(), groupTitle.get(i++));
            if (i == groupTitle.size())
                i = 0;
        }
    }
    private void creatingGroups(){
        groups.clear();
        ArrayList<String> groupTitle = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Deanery.class.getResourceAsStream("Groups")));
            while (br.ready()){
                groupTitle.add(br.readLine());
                this.createGroup(groupTitle.get(groupTitle.size() - 1));
            }
            br.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        enrollingStudents(groupTitle);
    }
}
