package University;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import  org.json.*;
public class Deanery {
    private static ArrayList<Student> students;
    private static ArrayList<Group> groups;
    private static ArrayList<String> names, surnames, patronyms;

    static {
        try {
            names = readFileToArray("names.txt");
            surnames = readFileToArray("surnames.txt");
            patronyms = readFileToArray("patronyms.txt");
        } catch (Exception ex) {
        }
    }

    public static ArrayList<String> readFileToArray(String fileName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = Paths.get(classLoader.getResource(fileName).toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> arrayList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            arrayList.add(line);
        }
        return arrayList;
    }
    public  static  ArrayList<Student> getAllStudents()
    {
        return students;
    }
    private static String getRandomString(ArrayList<String> arr) {
        int random = ThreadLocalRandom.current().nextInt(0, arr.size());
        return arr.get(random);
    }
    public static Student createRandomStudent() {
        Student student = new Student(getRandomString(names), getRandomString(surnames), getRandomString(patronyms));
        Random random = new Random();
        return student;
    }
    public static Student createRandomStudentWithMarks() {
        Student student = new Student(getRandomString(names), getRandomString(surnames), getRandomString(patronyms));
        Random random = new Random();
        int marksNumber=5+Math.abs (random.nextInt()%40);
        for(int j=0;j<marksNumber;j++)
            student.addMark(2 + random.nextFloat() *(5 - 2));
        return student;
    }
    public  static void createGroupsFromFile(String groupsFileName) throws  Exception
    {
        groups=new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String jsonString= Files.readString(Paths.get(classLoader.getResource(groupsFileName).toURI()));
        JSONObject jsonObject=new JSONObject(jsonString);
        for(Object element :jsonObject.getJSONArray("Groups"))
        {
            JSONObject currentJsonObject=(JSONObject)element;
            Group group=new Group(currentJsonObject.getString("Title"));
            groups.add(group);
        }
    }

    public static void createStudentsFromFile(String studentsFileName) throws Exception {
        students=new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String jsonString= Files.readString(Paths.get(classLoader.getResource(studentsFileName).toURI()));
        JSONObject jsonObject=new JSONObject(jsonString);
        for(Object element :jsonObject.getJSONArray("Students"))
        {
            JSONObject currentJsonObject=(JSONObject)element;
            Student student=new Student(
                    currentJsonObject.getInt("id"),
                    currentJsonObject.getString("Name"),
                    currentJsonObject.getString("surname"),
                    currentJsonObject.getString("patronym")
            );
            students.add(student);
            for(int i=0;i<groups.size();i++)
            {
                if(groups.get(i).getTitle().equals(currentJsonObject.getString("group")))
                    groups.get(i).addStudent(student);
            }
        }
    }
    public static   void createGroupsWithStudentsFromFile(String fileName) throws  Exception
    {
        groups=new ArrayList<>();
        students=new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String jsonString= Files.readString(Paths.get(classLoader.getResource(fileName).toURI()));
        JSONObject jsonGroupsObject=new JSONObject(jsonString);
        for(Object groupObject :jsonGroupsObject.getJSONArray("Groups")) {
            JSONObject jsonGroupObject=(JSONObject) groupObject;
            Group group=new Group(jsonGroupObject.getString("Title"));
            groups.add(group);
            for (Object element : jsonGroupObject.getJSONArray("Students")) {
                JSONObject currentJsonObject = (JSONObject) element;
                Student student = new Student(
                        currentJsonObject.getInt("id"),
                        currentJsonObject.getString("Name"),
                        currentJsonObject.getString("surname"),
                        currentJsonObject.getString("patronym")
                );
                for(Object mark :currentJsonObject.getJSONArray("marks").toList())
                {
                    student.getMarks().add(Float.parseFloat(mark.toString()));
                }
                students.add(student);
                group.addStudent(student);
            }
        }
    }
    public  static void writeRandomJsonGroupsWithStudents(String outputFileName) throws Exception
    {
        createGroupsFromFile("Groups.json");
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonGroups=new JSONArray();
        jsonObject.put("Groups",jsonGroups);
        for (Group group: groups)
        {
            JSONObject jsonObjectGroup=new JSONObject();
            jsonObjectGroup.put("Title",group.getTitle());
            int studentsNumber=ThreadLocalRandom.current().nextInt(15, 200);
            JSONArray jsonArrayStudents=new JSONArray();
            for(int i=0;i<studentsNumber;i++)
            {
                Student student=createRandomStudentWithMarks();
                group.addStudent(student);
                JSONObject objectStudent=new JSONObject();
                objectStudent.put("Name",student.getName());
                objectStudent.put("surname",student.getSurname());
                objectStudent.put("patronym",student.getPatronym());
                objectStudent.put("id",student.getID());
                objectStudent.put("group",student.getGroup().getTitle());
                objectStudent.put("marks",student.getMarks());
                jsonArrayStudents.put(objectStudent);
            }
            jsonObjectGroup.put("Students",jsonArrayStudents);
            jsonGroups.put(jsonObjectGroup);
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file=Paths.get(classLoader.getResource(outputFileName).toURI()).toFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(jsonObject.toString());
        writer.close();
    }
    public  static void writeCurrentJsonGroupsWithStudents(String outputFileName) throws Exception
    {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonGroups=new JSONArray();
        jsonObject.put("Groups",jsonGroups);
        for (Group group: groups)
        {
            JSONObject jsonObjectGroup=new JSONObject();
            jsonObjectGroup.put("Title",group.getTitle());
            JSONArray jsonArrayStudents=new JSONArray();
            for(Student student:group.getStudents())
            {
                JSONObject objectStudent=new JSONObject();
                objectStudent.put("Name",student.getName());
                objectStudent.put("surname",student.getSurname());
                objectStudent.put("patronym",student.getPatronym());
                objectStudent.put("id",student.getID());
                objectStudent.put("group",student.getGroup().getTitle());
                objectStudent.put("marks",student.getMarks());
                jsonArrayStudents.put(objectStudent);
            }
            jsonObjectGroup.put("Students",jsonArrayStudents);
            jsonGroups.put(jsonObjectGroup);
        }
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //File file=Paths.get(classLoader.getResource(outputFileName).toURI()).toFile();
        File file=new File(outputFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(jsonObject.toString());
        writer.close();
    }
    public static   void addRandomMarks(Student student,int number)
    {
        if(number<2)
            number=1;
        for (int i=0;i<number;i++)
            student.addMark(ThreadLocalRandom.current().nextFloat()*(5-2)+2);
    }
    public static   void transferStudentToAnotherGroup(Student student,Group anotherGroup)
    {
        student.getGroup().excludeStudent(student);
        anotherGroup.addStudent(student);
    }
    public  static  Group getGroup(int index)
    {
        return  groups.get(index);
    }
    public  static  Student getStudent(int index)
    {
        return  students.get(index);
    }
    public  static  int getTotalStudentsNumber(){return students.size();}
    public static void expelStudent(Student student)
    {
        student.getGroup().excludeStudent(student);
        students.remove(student);
    }
    public  static void expelStudentsForBadMarksInAllGroups(float mark)
    {
        for(int i=0;i<students.size();i++)
        {
            if (students.get(i).getAverageMark() < mark)
            {
                expelStudent(students.get(i));
                i--;
            }
        }
    }
    public static  void chooseHeads()
    {
        for(Group group:groups)
        {
            group.chooseHead();
        }
    }
    public static ArrayList<Group> getGroups()
    {
        return groups;
    }
    public  static Student getStudentById(int id)
    {
        for(int i=0;i<students.size();i++)
        {
            if (students.get(i).getID() == id)
                return students.get(i);
        }
        return  null;
    }
    public  static  void addStudent(Student student,Group group)
    {
        students.add(student);
        group.addStudent(student);
    }
    public static Group getGroupById(int id)
    {
        for (Group group : groups)
        {
            if (group.getId() == id)
                return group;
        }
        return null;
    }
}