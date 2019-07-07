import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Deanery {
    protected ArrayList<Group> groupsInDeanery = new ArrayList<Group>();
    protected ArrayList<Student> studentsInDeanery = new ArrayList<Student>();

    public void createStudents() {

        try {
            File f = new File("student.json");
            JSONParser parser = new JSONParser();
            FileReader fr = new FileReader(f);
            Object obj = parser.parse(fr);
            JSONObject js = (JSONObject) obj;
            JSONArray items = (JSONArray) js.get("students");
            JSONArray grp = (JSONArray) js.get("groups");
            for (Object i : grp) {
                Group group = new Group(((JSONObject) i).get("group").toString());
                groupsInDeanery.add(group);
            }
            for (Object i : items) {
                Student student = new Student(Integer.parseInt(((JSONObject) i).get("id").toString()),
                        ((JSONObject) i).get("fio").toString());
                addMarksForStudent(student);
                int randGroup = generateRandomNumber(groupsInDeanery.size());
                student.setGroup(groupsInDeanery.get(randGroup));
                groupsInDeanery.get(randGroup).addStudentToGroup(student);
                studentsInDeanery.add(student);
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addMarksForStudent(Student student) {
        for (int i = 0; i < 10; i++) {
            int mark = generateRandomNumber(6);
            if (mark > 1) {
                student.marks.add(mark);
            }
        }
    }

    public void sendDownStudent() {
        for (int i = studentsInDeanery.size() - 1; i >= 0; i--) {
            if (this.studentsInDeanery.get(i).averageMark() < 3.0) {
                System.out.println(this.studentsInDeanery.get(i).getFio() + " средняя оценка " +
                        this.studentsInDeanery.get(i).averageMark() + " отчислен(а)");
                this.studentsInDeanery.get(i).getGroup().deleteStudentFromGroup(this.studentsInDeanery.get(i));
                this.studentsInDeanery.remove(i);
            }
        }
    }

    public void studentChangedGroup() {

        for (int i = 0; i < studentsInDeanery.size(); i++) {
            if (studentsInDeanery.get(i).averageMark() < 3.5) {
                studentsInDeanery.get(i).getGroup().deleteStudentFromGroup(studentsInDeanery.get(i));
                groupsInDeanery.get(0).addStudentToGroup(studentsInDeanery.get(i));
            } else if (studentsInDeanery.get(i).averageMark() >= 3.5 && studentsInDeanery.get(i).averageMark() < 4) {
                studentsInDeanery.get(i).getGroup().deleteStudentFromGroup(studentsInDeanery.get(i));
                groupsInDeanery.get(1).addStudentToGroup(studentsInDeanery.get(i));
            } else if (studentsInDeanery.get(i).averageMark() >= 4 && studentsInDeanery.get(i).averageMark() <= 5) {
                studentsInDeanery.get(i).getGroup().deleteStudentFromGroup(studentsInDeanery.get(i));
                groupsInDeanery.get(2).addStudentToGroup(studentsInDeanery.get(i));
            }
        }
    }

    public int generateRandomNumber(int diapason) {
        Random random = new Random(System.currentTimeMillis());
        int randomNumber = random.nextInt(diapason);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return randomNumber;
    }

    public void chooseHead() {
        for (Group i : groupsInDeanery) {
            i.setHead(studentsInDeanery.get(generateRandomNumber(i.studentsInGroup.size())));
            System.out.println("Староста группы: " + i.getTitle() + " " + i.getHead().getFio());
        }
    }

    public void printListOfStudent() {
        for (Group x : groupsInDeanery) {
            System.out.println("Список группы : " + x.getTitle());
            for (int i = 0; i < x.studentsInGroup.size(); i++) {
                System.out.println(x.studentsInGroup.get(i).getFio() + " средняя оценка " +
                        x.studentsInGroup.get(i).averageMark());
            }
        }
    }

    public void printAvarageMarkForGroup() {
        for (Group x : groupsInDeanery) {
            System.out.println("Средняя оценка группы : " + x.getTitle() + " " + x.avarageMarkForGroup());
        }
    }

    public void jsonOut() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Student student : this.studentsInDeanery) {
            JSONObject object = new JSONObject();
            object.put("id", student.getId());
            object.put("fio,", student.getFio());
            object.put("group", student.getGroup().getTitle());
            jsonArray.add(object);

        }
        jsonObject.put("students", jsonArray);
        try {
            FileWriter fileWriter = new FileWriter(new File("jsonOut.json"));
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


