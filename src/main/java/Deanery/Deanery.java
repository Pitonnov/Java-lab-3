package Deanery;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Deanery {
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static int idStud;
    private static int idGroup;

    public Deanery() {
        idStud = 1000001;
        idGroup = 10001;
    }

    public Deanery (String studFile, String groupFile){
        idStud = 1000001;
        idGroup = 10001;
        this.settingStudents(studFile);
        this.settingGroups(groupFile);
    }

    public int settingStudents(String fileName) { //загрузка студентов
        int i =0;
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(fileName);
            Object obj = parser.parse(fileReader);
            JSONArray studentsArr = (JSONArray) obj;
            Iterator studIter = studentsArr.iterator();
            while (studIter.hasNext()) {
                JSONObject stud = (JSONObject) studIter.next();
                String surname = stud.get("surname").toString();
                String name = stud.get("name").toString();
                String middle_name = stud.get("middle_name").toString();
                students.add(new Student(idStud++, surname, name, middle_name));
                i++;
            }
            fileReader.close();
            parser.reset();
            studentsArr.clear();

        } catch (RuntimeException | ParseException | IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int settingGroups(String fileName) { // загрузка групп
        int i =0;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(fileName));
            JSONArray groupsArr = (JSONArray) obj;
            Iterator groupIter = groupsArr.iterator();
            while (groupIter.hasNext()) {
                JSONObject group = (JSONObject) groupIter.next();
                String title = group.get("title").toString();
                groups.add(new Group(idGroup++, title));
                i++;
            }
        } catch (RuntimeException | ParseException | IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ArrayList<Student> getStudents(){ //возвращает arraylist всех студентов
        return students;
    }

    public ArrayList<Group> getGroups(){ //возвращает arraylist всех групп
        return groups;
    }

    public void setExamination(int numberOfExams){ // проведение экзаменов
        for (Student st: students){
            for (int i=0; i<numberOfExams; i++) {
                Random rndm = new Random();
                int assessment = rndm.nextInt(4) +2;
                if ((rndm.nextInt()%100) < 1){
                    assessment = 5;
                }
                st.addMark(assessment);
            }
        }
    }

    public int enrollStudents(int maxStudentsInGroup) { // зачисление студентов в группы
        int studentsEnrolled = 0;
        int entrants = (int) students.stream().filter(st -> st.getGroup() == null).count();
        for (Student st: students){
            int k = 0;
            while (st.getGroup() == null) {
                Random rndm = new Random();
                int i = rndm.nextInt(groups.size());
                if (groups.get(i).numberOfStudents() < maxStudentsInGroup) {
                    st.changeGroup(groups.get(i));
                    groups.get(i).addStud(st);
                    studentsEnrolled++;
                }
                k++;
                if (k > groups.size()*2){
                    System.out.println("There are students who are not enrolled :"+(entrants-studentsEnrolled));
                    return studentsEnrolled;
                }
            }
        }
        return studentsEnrolled;
    }

    public void electionHeadmen(){ // выборы старосты
        for (Group group: groups){
            if (group.getHead() == null) {
                group.electionHead();
            }
        }
    }

    public Student findSt(int id){ // поиск студента
        for (Student student: students){
            if (student.getId()== id){
                return student;
            }
        }
        return null;
    }

    public Student findSt(String fio){ // поиск студента
        for (Student student: students){
            if (student.getFIO().equals(fio) || student.getSurname().equals(fio) || student.getName().equals(fio) || student.getMiddle_name().equals(fio)) {
                return student;
            }
        }
        return null;
    }

    public Group findGr(int id){ // поиск группы
        for (Group group: groups){
            if (group.getId()== id){
                return group;
            }
        }
        return null;
    }

    public Group findGr(String title){ // поиск группы
        for (Group group: groups){
            if ( title.equals(group.getTitle())){
                return group;
            }
        }
        return null;
    }

    public void fullExpelStudent (Student student){ // полное отчисление студента
        for (Group gr: groups){
            gr.expelStudent(student);
        }
        students.remove(student);
    }

    public int expellingForBadGrades (double minLevel){ // отчсиление за плохие оценки
        int numberOfStudents = 0;
        for (int i =0; i < students.size(); i++){
            if (students.get(i).averageMark() < minLevel){
                fullExpelStudent(students.get(i));
                numberOfStudents++;
                i--;
            }
        }
        return numberOfStudents;
    }

    public String studentTransfer (Student student, Group group){ // перевод студента в поределенную группу
        if (student == null || group == null){
            return "Sorry, but student or group does not exist";
        }
        if (student.getGroup() == group){
            return "This student is already in this group";
        }
        else{
            student.changeGroup(group);
            return "This student was transferred to the specified group";
        }
    }

    Comparator<Group> comparGroupsByMark = (g1, g2) -> {return Double.compare(g2.averageMark(), g1.averageMark());};

    Comparator<Student> comparStudentsByMark = (s1, s2) -> {return Double.compare(s2.averageMark(), s1.averageMark());};

    public void printAllInfo(String howToSortMarkOrName){ // вывод на экран
        if (howToSortMarkOrName == "mark" || howToSortMarkOrName == "Mark"){
            groups.sort(comparGroupsByMark);
        }
        else {
            Collections.sort(groups);
        }
        for(Group gr: groups){
            if (howToSortMarkOrName == "mark" || howToSortMarkOrName == "Mark"){
                gr.getStudents().sort(comparStudentsByMark);
            }
            else{
                Collections.sort(gr.getStudents());
            }
            System.out.println("\n_____________________________________________________________________");
            System.out.println("------------------------------"+gr.getTitle()+"------------------------------");
            System.out.println("|---Number of students: "+gr.getStudents().size());
            System.out.println("|---Average mark for group: "+gr.averageMark());
            System.out.println("|---Headman: "+gr.getHead().toString());
            System.out.println("|---Students: ");
            for (Student st: gr.getStudents()){
                System.out.println("|------------ "+st.toString()+"\t\t\tAverage mark: "+st.averageMark());
            }
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public void exportDataToJASON (File file) { // слепок деканата

        JSONArray deanery = new JSONArray();
        for (Group gr: groups){
            JSONObject group = new JSONObject();
            group.put("id", gr.getId());
            group.put("title", gr.getTitle());
            group.put("headId", gr.getHeadId());
            JSONArray students = new JSONArray();
            for(Student st: gr.getStudents()){
                JSONObject student = new JSONObject();
                student.put("id", st.getId());
                student.put("fio", st.getFIO());
                student.put("marks", st.getMarks());
                students.add(student);
            }
            group.put("students", students);
            deanery.add(group);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(deanery.toString());
        } catch (IOException | RuntimeException e) {
            System.out.println("File not saved");
        }

    }
}
