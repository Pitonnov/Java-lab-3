import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class Deanery {
    private static int countID = 0;
    public ArrayList<Group> allGroup;

    public Deanery() {
        allGroup = new ArrayList<Group>();
    }

    public int getCountID() {
        return countID;
    }

    public void setCountID(int newID) {  //only for test
        this.countID=newID;
    }

    public int makeGroupsWithStudents(String jsonFile) {

        JSONParser parser = new JSONParser();
        try {

            InputStream inputStream = getClass().getResourceAsStream(jsonFile);
            String fr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            JSONArray allGr = (JSONArray) parser.parse(fr);

            for (Object oneGr : allGr) {
                JSONObject group = (JSONObject) oneGr;
                Group group1 = new Group((String) group.get("title"));
                allGroup.add(group1);

                JSONArray studentsOfGroup = (JSONArray) group.get("studentGroup");
                for (Object oneSt : studentsOfGroup) {
                    JSONObject student = (JSONObject) oneSt;
                    countID++;
                    group1.addToGroup(new Student((String) student.get("fio"), countID));
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getCountID();
    }

    public int headInGroup() {
        int countHeadGroup = 0;
        for (Group oneGr : allGroup) {
            oneGr.choiceHead();
            countHeadGroup++;
        }

        return countHeadGroup;
    }

    public int addMarks(int marks) {
        int allMarks = 0;
        Random random = new Random(47);
        int j;
        for (Group oneGr : allGroup) {
            for (Student oneSt : oneGr.studentsGroup) {
                for (int i = 0; i < marks; i++) {
                    j = random.nextInt(4) + 2; //for min=2 and max=5
                    oneSt.addAssess(j);
                    allMarks++;
                }
            }
        }
        return allMarks;
    }

    public boolean transferToNextGroup(String fioStudent, String anotherGroup) {
        Group newGroup = null;
        Student studentForChange = null;

        for (Group newGr : allGroup) //group search by title
            if (newGr.getTitle().equals(anotherGroup)) {
                newGroup = newGr;
                break;
            }

        if (newGroup == null)  //protected from NullPointerException
            return false;


        for (Group oneGr : allGroup) {
            if ((oneGr.searchStudent(fioStudent)).size() != 0){
                studentForChange = oneGr.searchStudent(fioStudent).get(0); //student search in a group
                if (oneGr.exclusionFromTheGroup(studentForChange)) { //student expelled from old group
                    if (oneGr.getHead() == studentForChange) //if this student was head in old group
                        oneGr.setHead(null);
                    return newGroup.addToGroup(studentForChange);  //adding a student to a new group
                }
            }
        }

        return false; //if transfer failed
    }


    public int deductionStudents(double ball) {
        int countDeductionSt = 0;

        for (Group oneGr : allGroup) {
            Iterator<Student> itStudent = oneGr.studentsGroup.iterator();
            while (itStudent.hasNext()) {
                Student student = itStudent.next();
                if (student.averageAssess() <= ball) {
                    itStudent.remove();
                    if (oneGr.getHead() == student) //if this student was head in old group
                        oneGr.setHead(null);
                    student = null; //for Garbage collector
                    countDeductionSt++;
                }
            }
        }

        return countDeductionSt;
    }


    public ArrayList<Student> marksStatistics(int students, double minMark, double maxMark, boolean bestORlagging) {
        ArrayList<Student> averMarkSt = new ArrayList<Student>();
        ArrayList<Student> resultList = new ArrayList<Student>();

        for (Group oneGr : allGroup) {
            for (Student oneSt : oneGr.studentsGroup) {
                double averageMark = oneSt.averageAssess();
                if (averageMark >= minMark && averageMark <= maxMark)  //add all students with an average
                    averMarkSt.add(oneSt);                         //grade in the right range

            }
        }
        Collections.sort(averMarkSt); //sort array by average ball, compereTo in class Student

        if (bestORlagging) {//true -> best rated students
            for (int i = averMarkSt.size() - 1; i >= (averMarkSt.size() - students); i--)
                resultList.add(averMarkSt.get(i)); //the first and the best student is the last of sort array
        } else {  //false  -> lagging rated students
            for (int i = 0; i < students; i++)
                resultList.add(averMarkSt.get(i)); //the lagging student is the start of sort array
        }

        return resultList;
    }

    public void groupsDataToConsole() { //for all data
        for (Group oneGr : allGroup) {
            System.out.print(oneGr);
            for (Student oneSt : oneGr.studentsGroup)
                System.out.println(oneSt);
            System.out.println();
        }
    }

    public void groupsDataToConsole(Group oneGr) { //for data of one group
        System.out.print(oneGr);
        for (Student oneSt : oneGr.studentsGroup)
            System.out.println(oneSt);
        System.out.println();
    }

    public void academicPerformanceToConsole() { //for all data
        for (Group oneGr : allGroup) {
            System.out.print(oneGr);
            System.out.println("The average ball of this group = " + oneGr.amountAverageBall());
            for (Student oneSt : oneGr.studentsGroup) {
                System.out.println(oneSt);
                System.out.println(oneSt.getAssess());
                System.out.println("Average ball of this student = " + oneSt.averageAssess() + "\n");
            }
        }
    }

    public void groupHeadsToConsole() { //for all heads
        for (Group oneGr : allGroup)
            System.out.println(oneGr + "Head of this group is " + oneGr.getHead() + "\n");
    }

    public void groupsDataToFile() {

        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        String namef = "Groups_" + timeStamp + ".json"; //for user convenience

        JSONArray allGroupsJson = new JSONArray();
        for (Group oneGr : allGroup) {
            JSONObject objGroupJson = new JSONObject();
            objGroupJson.put("title", oneGr.getTitle());

            JSONObject headJsonObj = new JSONObject();
            if (oneGr.getHead() == null)
                objGroupJson.put("head", null);
            else {
                headJsonObj.put("id", (oneGr.getHead()).getId());
                headJsonObj.put("fio", (oneGr.getHead()).getFio());
                objGroupJson.put("head", headJsonObj);
            }

            JSONArray arrStudentJson = new JSONArray();
            for (Student oneSt : oneGr.studentsGroup) {
                JSONObject objStudentJson = new JSONObject();
                objStudentJson.put("fio", oneSt.getFio());
                objStudentJson.put("id", oneSt.getId());
                arrStudentJson.add(objStudentJson);
            }
            objGroupJson.put("studentsGroup", arrStudentJson);

            allGroupsJson.add(objGroupJson);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(namef))) {
            bw.write(allGroupsJson.toString());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void academicPerformanceToFile() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        String namef = "AcademicPerformance_" + timeStamp + ".json"; //for user convenience

        JSONArray allGroupsJson = new JSONArray();
        for (Group oneGr : allGroup) {
            JSONObject objGroupJson = new JSONObject();
            objGroupJson.put("title", oneGr.getTitle());

            JSONObject headJsonObj = new JSONObject();
            if (oneGr.getHead() == null)
                objGroupJson.put("head", null);
            else {
                headJsonObj.put("id", (oneGr.getHead()).getId());
                headJsonObj.put("fio", (oneGr.getHead()).getFio());
                objGroupJson.put("head", headJsonObj);
            }

            JSONArray arrStudentJson = new JSONArray();
            for (Student oneSt : oneGr.studentsGroup) {
                JSONObject objStudentJson = new JSONObject();
                objStudentJson.put("fio", oneSt.getFio());
                objStudentJson.put("assess", oneSt.getAssess());
                arrStudentJson.add(objStudentJson);
            }
            objGroupJson.put("studentsGroup", arrStudentJson);
            allGroupsJson.add(objGroupJson);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(namef))) {
            bw.write(allGroupsJson.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void groupHeadsToFile() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime()); //for nameFile
        String namef = "headsGroups_" + timeStamp + ".json"; //for user convenience

        JSONArray allGroupsJson = new JSONArray();
        for (Group oneGr : allGroup) {
            JSONObject objGroupJson = new JSONObject();
            objGroupJson.put("title", oneGr.getTitle());

            JSONObject headJsonObj = new JSONObject();
            if (oneGr.getHead() == null)
                objGroupJson.put("head", null);

            else {
                headJsonObj.put("id", (oneGr.getHead()).getId());
                headJsonObj.put("fio", (oneGr.getHead()).getFio());
                objGroupJson.put("head", headJsonObj);
            }

            allGroupsJson.add(objGroupJson);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(namef))) {
            bw.write(allGroupsJson.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
