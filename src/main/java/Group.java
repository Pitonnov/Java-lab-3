import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class Group {
   private String title; //название группы
    private List<Student> students = new ArrayList<Student>();  //массив из ссылок на студентов
    private Student head; //ссылка на старосту (из членов группы)

    //Методы
    // группы с указанием названия
    public Group(String title) {
        this.title = title;
    }
    public Group() {
    }

    // добавление студента
    public void addStudent(Student student) {
        students.add(student);
    }

    //избрание старосты
    public void setHead(Student head) {
        this.head = head;
    }

    // поиск студента по ФИО или ИД
    public Student searchStudent(String name) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getFio().equals(name)) { //функция стринг
                return students.get(i);
            }
        }
        return null;
    }

    public Student searchStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                return students.get(i);
            }
        }
        return null;
    }

    // вычисление соеднего балла в группе
    public double averageRatingGroup() {
        double s = 0;
        for (int i = 0; i < students.size(); i++) {
            s += students.get(i).averageRating();
        }
        return s / students.size();
    }

    // исключение студента из группы
    public void excludeStudent(Student student) {
        students.remove(student);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public Student getHead() {
        return head;
    }

}