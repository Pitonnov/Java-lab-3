import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

public class Deanery {
    ArrayList<Student> students;
    ArrayList<Group> groups;

    Deanery() {
        this.students = new ArrayList<Student>();
        this.groups = new ArrayList<Group>();
    }

    public void importStudents(String fileName){
        try{
            Object obj = new JSONParser().parse(new FileReader(fileName));
            JSONObject jo = (JSONObject) obj;
            JSONArray stud = (JSONArray) jo.get("students");
            Iterator studItr = stud.iterator();
            int id = 1;
            while(studItr.hasNext()){
                JSONObject buf = (JSONObject)studItr.next();
                Student temp = new Student(id, buf.get("fio").toString());
                students.add(temp);
                id++;
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
    }

    public void importGroup(String fileName){
        try{
            Object obj = new JSONParser().parse(new FileReader(fileName));
            JSONObject jo = (JSONObject) obj;
            JSONArray grp = (JSONArray) jo.get("groups");
            Iterator grpItr = grp.iterator();
            int len = 0;
            int i = 0;
            int j = 0;
            int d = students.size() / grp.size();
            while(grpItr.hasNext()){
                len += d;
                JSONObject buf = (JSONObject)grpItr.next();
                Group temp = new Group(buf.get("title").toString());
                groups.add(temp);
                for (; i < len; i++){
                    groups.get(j).addStudent(students.get(i));
                }
                j++;
            }
        }
        catch(FileNotFoundException e){
        System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
    }

    public void addMarksForAll(){
        int markCount = 10;
        for (Student student: students){
            Random random = new Random();
            for (int i = 0; i < markCount; i++){
                student.addMark(random.nextInt(5) + 1);
            }
        }
    }

    public void headInitialize(){
        for (Group group: groups){
            group.chooseHead();
        }
    }

    public void transferStudent(int id, String newTitle){
        Student tempStud = null;
        for (Group group: groups){
            if (group.searchStudent(id) != null){
                tempStud = group.searchStudent(id);
                group.removeStudent(tempStud);
            }
        }
        for (Group group: groups) {
            if (group.title == newTitle){
                group.addStudent(tempStud);
            }
        }
    }

    public void deductStudent(){
        double minAvgMark = 2.5;
        for (int i =0; i < students.size(); i++){
            if (students.get(i).avgMark() < minAvgMark){
                students.get(i).group.removeStudent(students.get(i));
                students.remove(students.get(i));
                i--;
            }
        }
    }

    public void writeToConsole(){
        for (Group group: groups){
            System.out.println("Title: " + group.getTitle() + "; Average mark is: " + group.avgMarkInGroup() + ";");
            System.out.println("Students in group: ");
            for (Student student: group.getStudents()) {
                System.out.println("Name: " + student.getFio() + ". Average mark is: " + student.avgMark() + ";");
            }
            System.out.println("\nGroup headman is: " + group.getHead().getFio()  + ";\n");
        }
    }

    public void writeToFile(String fileName){
        try{
            FileWriter fwriter = new FileWriter(fileName);
            JSONArray groupsJA = new JSONArray();
            for(Group group: groups){
                JSONObject jo1 = new JSONObject();
                jo1.put("title", group.getTitle());
                jo1.put("groupAvgMark", group.avgMarkInGroup());
                jo1.put("head", group.getHead().getFio());
                JSONArray studentJA = new JSONArray();
                for (Student student: group.getStudents()){
                    JSONObject jo2 = new JSONObject();
                    jo2.put("id", student.getId());
                    jo2.put("fio", student.getFio());
                    jo2.put("studentAvgMark", student.avgMark());
                    studentJA.add(jo2);
                }
                jo1.put("students", studentJA);
                groupsJA.add(jo1);
            }
            fwriter.write(groupsJA.toJSONString());
            fwriter.flush();
            fwriter.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
