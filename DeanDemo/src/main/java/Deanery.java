import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Deanery {
    private static Integer lastId = 0;
    private JSONObject deanary;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
    ;

    public Deanery(String fileName) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileName).getFile()
            );
            String stringOfList = FileUtils.readFileToString(file, "utf-8");
            deanary = new JSONObject(stringOfList);

            Iterator groupsIterator = deanary.keys();
            while (groupsIterator.hasNext()) {
                String group_name = (String) groupsIterator.next();
                Group newGroup = new Group(group_name);
                groups.add(newGroup);
                JSONArray studentsArray = (JSONArray) deanary.get(group_name);
                for (int i = 0; i < studentsArray.length(); i++) {
                    JSONObject studentJSON = (JSONObject) studentsArray.get(i);
                    String studentName = (String) studentJSON.get("name");
                    Student newStudent = new Student(getNextId(), studentName);
                    students.add(newStudent);
                    newStudent.enrolltoGroup(newGroup);
                }
            }

        } catch (IOException ex) {
            System.out.println("Error during reading " + fileName + ex.getMessage());
        }
    }

    protected static Integer getNextId() {
        Integer lastId1 = lastId;
        lastId++;
        return lastId1;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student createStudent(String fio) {
        Student newStudent = new Student(getNextId(), fio);
        students.add(newStudent);
        System.out.println("The student " + newStudent.getFio() + " is created");
        System.out.println("The student " + newStudent.getFio() + " is assignet id №" + newStudent.getId());
        return newStudent;
    }

    public void addMarks() {
        int newMark = 1;
        for (int i = 0; i < students.size(); i++) {
            int j = 0;
            while (j < 10) {
                newMark = (int) (Math.random() * 5 + 2);
                students.get(i).addMark(newMark);
                j++;
            }

        }
    }

    public int getAverageMarkInTheGroup(Group group) {
        int averageMarkInTheGroup = 0;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i) == group ) {
                averageMarkInTheGroup = groups.get(i).calculateAverageGroupMark();
            }
        }
        group.toString();
        System.out.println("The average mark in group in " + group + " is " + averageMarkInTheGroup);
        return averageMarkInTheGroup;
    }

    public int getAverageStudentMark(Student student) {
        int averageStudentMark = 0;
            if (students.contains(student)) {
                averageStudentMark = student.calculateAverageMark();
            } else {
                System.out.println("There isn`t such student in groups!");
            }
            student.toString();
        System.out.println("The average mark of student " + student.getFio() + " is " + averageStudentMark);
        return averageStudentMark;
    }

    public Student transferToAnotherGroup(Student student, Group newGgroup) {
        student.getGroup().expellStudentFromGroup(student);
        student.enrolltoGroup(newGgroup);
        return student;
    }


    public ArrayList<Student> getTheWorsetStudentsInGroup() {
        int averageMarkInTheGroup = 0;
        int averageStudentMark = 0;
        ArrayList<Student> theWorstStudens = new ArrayList();
        for (int i = 0; i < groups.size(); i++) {
            ArrayList<Student> groupStudents = groups.get(i).getStudentsArray();
            averageMarkInTheGroup = getAverageMarkInTheGroup(groups.get(i));
            for (int j = 0; j < groupStudents.size(); j++) {
                averageStudentMark = getAverageStudentMark(groupStudents.get(j));
                if ( averageStudentMark < averageMarkInTheGroup || averageStudentMark <=  2 ) {
                }
            }
            theWorstStudens.add(groupStudents.get(i));
        }
        return theWorstStudens;
    }

    public ArrayList expellTheWorsetStudents(ArrayList theWorstStudens) {
       for  (int i = 0; i < students.size(); i++) {
           for (int j = 0; j < theWorstStudens.size(); j++) {
               if (students.contains(theWorstStudens.get(j))) {
                   students.get(i).getGroup().expellStudentFromGroup(students.get(i));
                   students.remove(students.get(i));
                   System.out.println("The student " + students.get(i).getFio() + " is expelled because of academic failure");
               }
           }
       }
        return students;
    }

    public Group holdElection (Group group) {
        int grSize = group.studentsArray.size();
        int randomI = (int) (Math.random() * grSize);
        group.ellectHead(students.get(randomI));
        System.out.println("The student " + students.get(randomI).getFio() + " is ellected the head of group " + group);
        return group;
    }


    public void createNewJSONfile (String fileName) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        JSONObject newGroup = new JSONObject();
        for (int i = 0; i < groups.size(); i++) {
            ArrayList<Student> students_array = groups.get(i).getStudentsArray();
            JSONArray studentsInGroup = new JSONArray();
            for (int j = 0; j < students_array.size(); j++) {
                studentsInGroup.put(students_array.get(j).getJsonObject());
            }
            newGroup.put(groups.get(i).getTitle(), studentsInGroup);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("NewGroups.json"))) {
            bw.write(newGroup.toString(4));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }



}
