import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Deanery {
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList <Group> groups = new ArrayList<Group>();
    public void createGroupsWithStudents (String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonGroups = (JSONArray) jsonObject.get("groups");
            int id = 1;
            for (int i = 0; i < jsonGroups.size(); i++){
                JSONObject jsonGroup = (JSONObject) jsonGroups.get(i);
                String title = (String) jsonGroup.get("title");
                Group group = new Group(title);
                JSONArray jsonStudents = (JSONArray) jsonGroup.get("students");
                for (int j = 0; j < jsonStudents.size(); j++){
                    JSONObject jsonStudent = (JSONObject) jsonStudents.get(j);
                    String fio = (String) jsonStudent.get("fio");
                    Student student = new Student(id, fio);
                    id++;
                    student.setGroup(group);
                    group.addStudent(student);
                    students.add(student);
                }
                groups.add(group);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class NoGroupException extends Exception{}
    public Group findGroup(String title) throws NoGroupException {
        Group foundGroup = null;
        for(Group group:groups){
            String groupTitle = group.getTitle();
            if(groupTitle.equals(title))
                foundGroup = group;
        }
        if (foundGroup!=null)
            return foundGroup;
        else throw new NoGroupException();
    }
    private Student getRandomStudent(){
        int minStudents = 1, maxStudents = students.size()-1, diff = maxStudents - minStudents;
        int studentIndex;
        Student randomStudent;
        Random random = new Random();
        studentIndex = minStudents + random.nextInt(diff + 1);
        randomStudent = students.get(studentIndex);
        return randomStudent;
    }
    public void addMarks(){
        students.get(0).addMark(5);
        students.get(0).addMark(4);
        int studentsWithMarks = 0;
        int minMark = 2, maxMark = 5, diffMark = maxMark - minMark;
        int randomMark;
        Random random = new Random();
        while (studentsWithMarks < students.size()-1) {
            Student randomStudent=getRandomStudent();
            if (randomStudent.averageMark() == 0)
                studentsWithMarks += 1;
            randomMark = minMark + random.nextInt(diffMark + 1);
            randomStudent.addMark(randomMark);
        }
    }
    public void sortToMarks () {
        Comparator<Student> markSort = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.averageMark() - o1.averageMark();
            }
        };
        students.sort(markSort);
    }
    public void sortGroups () {
        Comparator<Group> markSort = new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o2.averageMark() - o1.averageMark();
            }
        };
        groups.sort(markSort);
    }
    public void changeGroup(int id, String newGroupTitle) {
        Student student;
        for(int i=0;i<groups.size()-1;i++){
            try {
                student = groups.get(i).findStudent(id);
                student.getGroup().expellStudent(student);
                Group newGroup = findGroup(newGroupTitle);
                student.setGroup(newGroup);
                newGroup.addStudent(student);
                return;
            } catch (Group.NoStudentException e) {
            } catch (NoGroupException e) {
            }
        }
        try {
            student = groups.get(groups.size()-1).findStudent(id);
            student.getGroup().expellStudent(student);
            Group newGroup = findGroup(newGroupTitle);
            student.setGroup(newGroup);
            newGroup.addStudent(student);
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        } catch (NoGroupException e) {
            e.printStackTrace();
        }
    }
    public void changeGroup(String fio, String newGroupTitle){
        Student student;
        for(int i=0;i<groups.size()-1;i++){
            try {
                student = groups.get(i).findStudent(fio);
                student.getGroup().expellStudent(student);
                Group newGroup = findGroup(newGroupTitle);
                student.setGroup(newGroup);
                newGroup.addStudent(student);
                return;
            } catch (Group.NoStudentException e) {
            } catch (NoGroupException e) {
            }
        }
        try {
            student = groups.get(groups.size()-1).findStudent(fio);
            Group newGroup = findGroup(newGroupTitle);
            student.setGroup(newGroup);
        } catch (Group.NoStudentException e) {
            e.printStackTrace();
        } catch (NoGroupException e) {
            e.printStackTrace();
        }
    }
    public void expellStudents (){
        ArrayList<Student>studentsToExpell = new ArrayList<>();
        for (Student student:students) {
            int averageMark = student.averageMark();
            if (averageMark < 3)
                studentsToExpell.add(student);
        }
        for(Student studentToExpell:studentsToExpell){
            Group group = studentToExpell.getGroup();
            group.expellStudent(studentToExpell);
            students.remove(studentToExpell);
            if(studentToExpell.equals(group.getHead()))
                setRandomHead(group);
        }
    }
    private void setRandomHead(Group group){
        while (true) {
            Student randomStudent = getRandomStudent();
            boolean isInGroup = randomStudent.getGroup().equals(group);
            if (isInGroup) {
                group.setHead(randomStudent);
                break;
            }
        }
    }
    public void setHeads() {
        for (Group group : groups) {
            setRandomHead(group);
        }
    }
    public void printGroups(){
        for (Group group:groups) {
            System.out.println("Group: " + group.getTitle());
            System.out.println("    Head: " + group.getHead().getId() + " " + group.getHead().getFio());
            System.out.println("    average mark: " + group.averageMark());
        }
    }
    public void printStudents(){
        for(int i=0; i<students.size(); i++) {
            System.out.println(students.get(i).getId() + " " +students.get(i).getFio());
            System.out.println("    Group: "+students.get(i).getGroup().getTitle());
            students.get(i).printMarks();
            System.out.println("    average mark: " + students.get(i).averageMark());
        }
    }
    public void createJSON(boolean prettyPrint){
        org.json.JSONObject list = new org.json.JSONObject();
        org.json.JSONArray groupsJson = new org.json.JSONArray();
            for(Group group:groups){
                org.json.JSONObject groupJson = new org.json.JSONObject();
                    groupJson.put("title", group.getTitle());
                    org.json.JSONArray studentsJson = new org.json.JSONArray();
                    for(Student student:students){
                        if(student.getGroup().equals(group)){
                            org.json.JSONObject studentJson = new org.json.JSONObject();
                                studentJson.put("id", student.getId());
                                studentJson.put("fio", student.getFio());
                            studentsJson.put(studentJson);
                            }
                        }
                    groupJson.put("students", studentsJson);
                groupsJson.put(groupJson);
            }
        list.put("groups", groupsJson);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("list.json"))){
            if (prettyPrint) {
                bw.write(list.toString(4)+'\n');
            } else {
                bw.write(list.toString()+'\n');
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
