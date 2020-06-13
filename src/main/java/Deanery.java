import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Deanery {

    List<Student> students = new ArrayList<Student>(); //массив студентов, либо свой, либо из группы
    List<Group>  groups = new ArrayList<Group>(); //массив групп
    ObjectMapper mapper = new ObjectMapper(); //класс, к-й будет считывать json
    Random random = new Random();
    File file;

    //Методы

    //создание групп на основе данных из файла

    public Deanery() {
        URL resource = getClass().getClassLoader().getResource("Students.json");
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void groupsCreation () {
        try {
            groups =  mapper.readValue(file, mapper.getTypeFactory()
                    .constructCollectionType(List.class, Group.class));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //создание студентов на основе данных из файла
    public void studentsCreation () {
        for (int i = 0; i < groups.size(); i++) {
            students.addAll(groups.get(i).getStudents());
            List<Student> students2 = groups.get(i).getStudents();
            for (int j = 0; j < students2.size(); j++) {
                students2.get(j).setGroup(groups.get(i));
            }
        }
    }

    //  добавление случайных оценок студентам
    public void addGradeDeanery () {
        for (int i = 0; i<students.size(); i++) {
            for (int j = 0; j < 6; j++) {
                students.get(i).addGrade(random.nextInt(5) + 1);
            }
            for (int j = 0; j < 4; j++) {
                students.get(i).addGrade(random.nextInt(3) + 3);
            }
        }
    }

    //  накопление статистики по успеваемости студентов и групп - 5 самых успевающих/неуспевающих/лучшая группа/худщая
    public void advancedFiveStudents() {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o2.averageRating() > o1.averageRating()) {
                    return 1;
                }
                if (o2.averageRating() < o1.averageRating()) {
                    return -1;
                }
                return 0;
            }
        });
        System.out.println("5 самых успевающих студентов");
        for (int i=0; i<5; i++) {
            System.out.println(students.get(i).getFio()+" "+students.get(i).averageRating());
        }
        System.out.println();
        System.out.println("5 самых отстающих студентов");
        for (int i=students.size()-5; i<students.size(); i++) {
            System.out.println(students.get(i).getFio()+" "+students.get(i).averageRating());
        }
        System.out.println();
    }
    public void advancedGroup() {
        groups.sort(new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                if (o2.averageRatingGroup()>o1.averageRatingGroup()) {
                    return 1;
                }
                if (o2.averageRatingGroup()<o1.averageRatingGroup()) {
                    return -1;
                }
                return 0;
            }
        });
        System.out.println("Самая успевающая группа");
        System.out.println(groups.get(0).getTitle()+" "+groups.get(0).averageRatingGroup());
        System.out.println();
    }

    //  перевод студентов из группы в группу через мэин
    public void changeStudentGroup(String fio, String titleTo) {
        Student student = null;
        for (int i = 0; i < groups.size(); i++) {
            student = groups.get(i).searchStudent(fio);
            if (student != null) {
                groups.get(i).excludeStudent(student);
                if (groups.get(i).getHead()==student){
                    chooseHead(groups.get(i));
                }
                break;
            }
        }
        if (student != null) {
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getTitle().equals(titleTo)) {
                    groups.get(i).addStudent(student);
                    student.setGroup( groups.get(i));
                    break;
                }
            }
        }
    }

    //  отчисление студентов за неуспеваемость
    public void excludeStudentGroup() {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.averageRating() < 3) {
                student.getGroup().excludeStudent(student);
                student.setGroup(null);
            }
        }
    }

    //  сохранение обновленных данных в файлах
    public void changeFile() {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(file, groups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // инициация выборов старост в группах
    public void chooseHead () {
        for (int i = 0; i < groups.size(); i++) {
            List<Student> students2 = groups.get(i).getStudents();
            int studentHead = random.nextInt(students2.size());
            groups.get(i).setHead(students2.get(studentHead));
        }
    }
    public void chooseHead (Group group){
        List<Student> students2 = group.getStudents();
        int studentHead = random.nextInt(students2.size());
        group.setHead(students2.get(studentHead));
    }

    //  вывод данных на консоль
    public void print() {
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Группа - "+ groups.get(i).getTitle());
            System.out.println("Средний балл группы " + groups.get(i).averageRatingGroup());
            System.out.println("Страроста группы - " + groups.get(i).getHead().getFio());
            List<Student> students2 = groups.get(i).getStudents();
            for (int j = 0; j < students2.size(); j++) {
                System.out.println(students2.get(j).getFio()+" "+students2.get(j).averageRating());
            }
            System.out.println();
        }
    }

    public List<Student> getStudent () {
        return students;
    }

    public List<Group> getGroup () {
        return groups;
    }


    public static void main(String[] args) {
        Deanery deanery = new Deanery();
        deanery.groupsCreation();
        deanery.studentsCreation();
        deanery.chooseHead();
        deanery.addGradeDeanery();
        deanery.changeStudentGroup("Кудрявцев Евгений Иванович", "Атомная теплофизика");
        deanery.advancedFiveStudents();
        deanery.advancedGroup();
        deanery.print();
        deanery.excludeStudentGroup();
        deanery.print();
        deanery.changeFile();


    }
}




