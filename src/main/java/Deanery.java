import org.apache.commons.io.FileUtils;
import org.decimal4j.util.DoubleRounder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;


class Deanery {

    private List<Student> students = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public Deanery(String Studentsfile, String Groupfile) {
        try {
            URL resource = getClass().getResource(Studentsfile);
            File file = Paths.get(resource.toURI()).toFile();
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONArray people = new JSONArray(content);
            int id = 100;
            for (int i = 0; i < people.length(); i++) {
                this.students.add(Student.generate(id++, people.getJSONObject(i).getString("name")));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL resource = getClass().getResource(Groupfile);
            File file = Paths.get(resource.toURI()).toFile();
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONArray people = new JSONArray(content);
            for (int i = 0; i < people.length(); i++) {
                this.groups.add(Group.generate(people.getJSONObject(i).getString("name")));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Group> getGroups() {
        return groups;
    }

    // распредление по группам
    public void distributeToGroups (){
        if (groups.size() == 1) {
            for (Student i : students) {
                groups.get(0).addStudent(i);
                i.setGroup(groups.get(0));
            }
        } else if (groups.size() > 1) {
            if (students.size() % groups.size() == 0) { // если количество студентов четное
                int count = students.size() / groups.size();
                int size = 0;
                int i = 0;
                int j = 0;
                while (i < students.size() && j < groups.size()) {
                    if (size < count) {
                        groups.get(j).addStudent(students.get(i));
                        students.get(i).setGroup(groups.get(j));
                        i++;
                        size++;
                    } else {
                        size = 0;
                        j++;
                    }
                }
            } else { // если количество студентов нечетное
                int count = students.size() / groups.size();
                int count1 = students.size() % groups.size();
                int size = 0;
                int i = 0;
                int j = 0;
                while (i < students.size() && j < groups.size()) {
                    if (size < count) {
                        groups.get(j).addStudent(students.get(i));
                        students.get(i).setGroup(groups.get(j));
                        i++;
                        size++;
                    } else {
                        size = 0;
                        j++;
                    }
                }
                for(int a = 0; a<count1;a++){ // оставшихся зачисляем в последнюю группу
                    j-=1;
                    groups.get(j).addStudent(students.get(a));
                    students.get(a).setGroup(groups.get(j));
                }
            }
        }
    }

    //  случайные оценки студентам
    public void Exam(){
        int a = 2;
        int b = 5;
        for (Student i: students){
            for (int j = 0; j<15; j++){
                Random r = new Random();
                try{
                    i.addMarks((r.nextInt(5-2 + 1) + 2));
                }catch (BadMarkException e){
                    e.printStackTrace();
                }
            }
        }
    }

    Comparator<Student> comparStudents = new Comparator<Student>(){
        public int compare(Student o1, Student o2) {
            float marks1 = o1.averageMark();
            float marks2 = o2.averageMark();
            if (marks1 < marks2) return 1;
            if (marks1 > marks2) return -1;
            return 0;
        }
    };

    Comparator<Group> comparGroups = new Comparator<Group>(){
        public int compare(Group o1, Group o2) {
            float marks1 = o1.averageMark();
            float marks2 = o2.averageMark();
            if (marks1 < marks2) return 1;
            if (marks1 > marks2) return -1;
            return 0;
        }
    };

    // статистику по успеваемости записываем в json файл
    public void Statistic(){
        Collections.sort(groups,comparGroups);
        JSONArray statisticAll = new JSONArray();
        for (Group i: groups){
            Map<JSONObject, JSONArray> map = new HashMap<>();
            JSONObject statistic1 = new JSONObject();
            statistic1.put(i.getTitle(),DoubleRounder.round(i.averageMark(),2));
            //statisticAll.put(statistic1);
            JSONArray statisticAllst = new JSONArray();
            ArrayList<Student> allstudents = new ArrayList<>();
            allstudents = i.getStudents();
            Collections.sort(allstudents,comparStudents);
            for (Student j: allstudents){
                JSONObject statistictic1 = new JSONObject();
                statistictic1.put(j.getFio()+" id:"+j.getId(),DoubleRounder.round(j.averageMark(),2));
                statisticAllst.put(statistictic1);
            }
            map.put(statistic1,statisticAllst);
            JSONObject statistic = new JSONObject(map);
            statisticAll.put(statistic);
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Statistic.json")))
        {
            bw.write(statisticAll.toString(4));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    // обслуживающий метод для поиска нужной группы по имени
    private Group searchGroup (String name){
        for(Group i: groups){
            if (i.getTitle().equals(name)){
                return i;
            }
            else continue;
        }
        return null;
    }

    // перевод из одной группы в другую
    public String changeGroup (String firstgroup, String secondgroup, int id){
        Group first = searchGroup(firstgroup);
        Group sec = searchGroup(secondgroup);
        if (first!=null&&sec!=null){
            sec.addStudent(first.searchStudent(id));
            first.ExeceptStudent(id);
            sec.searchStudent(id).setGroup(sec);
            return sec.searchStudent(id).toString();
        }
        else throw new NoSuchElementException ("Группа/пы не найдены");
    }

    // инициализация выборов старост
    public ArrayList<Student> headchoice (int numberofgroup){
        ArrayList<Student> head = new ArrayList<>();
        for (Group i: groups){
            ArrayList<Student> allstudent = i.getStudents();
            Collections.sort(allstudent,comparStudents);
            i.addHead(allstudent.get(0));
            head.add(allstudent.get(0));
        }
        return head;
    }

    // отчисление студента
    public Student ExeceptStudent (int id){
        Student st = null;
        for (Group i: groups){
            st = i.ExeceptStudent(id);
            students.remove(st);
            if (st!=null)
                return st;
        }
        throw new NoSuchElementException("Студент не найден!");
    }
}
