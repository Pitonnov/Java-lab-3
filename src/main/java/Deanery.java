import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class Deanery {
    private ArrayList<Student> students;
    private ArrayList<Group> groups;

    public Deanery(){
        students = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public Group getGroup(String title){
        Group result = null;
        for(Group group : groups){
            if(group.getTitle().equals(title)){
                result = group;
                break;
            }
        }
        return result;
    }

    public void setHead(String groupTitle, Integer id){
        Group group = getGroup(groupTitle);
        Student student = getStudent(id);
        if(student.getGroup().equals(group)){
            group.setHead(student);
        } else {
            System.out.println("Студент " + student.getFio() + " не может быть старостой группы "+groupTitle+" т.к. числится за группой "+student.getGroup().getTitle());
        }


    }

    public Student getStudent(Integer id){
        Student result = null;
        for(Student student: students){
            if(student.getId() == id){
                result = student;
                break;
            }
        }
        return result;
    }

    public void studentExplusion(Integer id){
        Student student = getStudent(id);
        student.getGroup().studentExclusion(student.getId());
        int remove = 0;
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).equals(student)){
                remove = i;
                break;
            }
        }
        students.remove(remove);
    }

    //change student group
    public void changeGroup(Student student, Group group){
        Group present = student.getGroup();
        //для студента установить новую группу
        //удалить в старой группе студента,
        //в новую группу добавить студента
        student.setGroup(group);
        present.studentExclusion(student.getId());
        group.addStudent(student);
    }

    public void readJSON() throws IOException, JSONException, ParseException {

        File file = new File("input.json");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        JSONArray stud = (JSONArray)obj;
        Iterator iterator = stud.iterator();
        while(iterator.hasNext()){
            JSONObject person = (JSONObject)iterator.next();
            Integer id = Integer.parseInt(person.get("id").toString());
            String name = person.get("student").toString();
            String groupTitle = person.get("group").toString();
            Group group = getGroup(groupTitle);
            Student student = getStudent(id);
            if (student == null && group == null){
                //1. не нашли студента, не нашли группу
                group = new Group(groupTitle);
                student = new Student(id, name);
                student.setGroup(group);
                group.addStudent(student);
                students.add(student);
                groups.add(group);
            } else if (student == null && group != null){
                //2. не нашли студента, нашли группу
                student = new Student(id, name);
                student.setGroup(group);
                group.addStudent(student);
                students.add(student);
            } else if (student != null && group == null){
                //3. нашли студента, не нашли группу
                group = new Group(groupTitle);
                changeGroup(student, group);
                groups.add(group);
            } else if (student != null && group != null){
                Group present = student.getGroup();
                if(!(present.equals(group))){
                    changeGroup(student, group);
                }
            }
        }
    }

    public void addRandomMarks(Integer count){
        //count - количество оценок для каждого студента
        //все оценки будут добавлены текущей датой
        for(Student student : students){
            //Math.random()%5+1 - оценка
            for(int i = 0; i<count; i++){
                student.addMark(new Random().nextInt(4) + 2);
            }
        }
    }

    public void addRandomMarks(Integer count, Date begin, Date end){
        Long beginms = begin.getTime();
        Long endms = end.getTime();
        for(Student student : students){
            for(int i = 0; i<count; i++){
                Long random = Math.abs(new Random().nextLong());
                Long ms = random % (endms - beginms - 1);
                Date markdate = new Date(beginms + ms);
                student.addMark(new Random().nextInt(4) + 2, markdate);
            }
        }
    }

    public void writeJSON() {
        JSONArray list = new JSONArray();
        for(Student student : students){
            JSONObject obj = new JSONObject();
            obj.put("id", student.getId());
            obj.put("student", student.getFio());
            obj.put("group", student.getGroup().getTitle());
            list.add(obj);
        }
        //try(FileWriter file = new FileWriter("output.json", true)){
        try(FileWriter file = new FileWriter("output.json")){
            file.write(list.toJSONString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //вывод данных
    //вывести список всех групп
    public void printGroups() {
        for(Group group : groups){
            System.out.println(group.getTitle());
        }
    }

    public void printStudents() {
        for(Group group : groups){
            System.out.println(group.getTitle());
            for(Student student: students){
                if(group.equals(student.getGroup())){
                    System.out.println("  "+String.format("%2d %s", student.getId(), student.getFio()));
                }
            }
        }
    }

    public void printStudentsMarks(String groupTitle, Date begin, Date end){
        Group group = getGroup(groupTitle);
        System.out.println(group.getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        for(Student student: students){
            if(group.equals(student.getGroup())){
                System.out.println("  "+String.format("%2d %s", student.getId(), student.getFio()));
                System.out.println("    Дата:      Оценка:");
                ArrayList<Marks> marks = student.getMarks();
                for(Marks mark: marks){
                    if(mark.getGroup().equals(group) && begin.before(mark.getDate()) && end.after(mark.getDate())){
                        System.out.println(String.format("    %s %6d", formatter.format(mark.getDate()), mark.getMark()));
                    }
                }
                System.out.println("    Средняя оценка за период: "+student.getAverageMark(group, begin, end));
            }
        }
    }

}
