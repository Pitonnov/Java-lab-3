import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private int id; //идентификационный номер
    private String fio; //фамилия и инициалы
    private Group group; //ссылка на группу (объект Group)
    //@JsonIgnore
    private List<Integer> marks = new ArrayList<Integer>(); //контейнер оценок (ArrayList).

    //Методы

    public Student() {

    }
    //конструктор, с пустым контейнером оценок создание студента с указанием ИД и ФИО


    public Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    //зачисление в группу
    public void enrollment(String group) {
        this.group.setTitle(group);
    }

    //добавление оценки

    public void addGrade(int grade) {
        if ((grade > 5) || (grade < 1)) {
            throw new IllegalStateException("Неверная оценка");
        }
        marks.add(grade);
    }

    //вычисление средней оценки

    public double averageRating() {
        double s = 0;
        for (int i = 0; i < marks.size(); i++) {
            s += marks.get(i);
        }
        return s / (marks.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                fio.equals(student.fio) &&
                group.equals(student.group) &&
                marks.equals(student.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, group, marks);
    }

    public String getFio() {
        return fio;
    }

    // public void setFio(String fio) {
    //   this.fio = fio;
    //  }

    public int getId() {
        return id;
    }

    @JsonIgnore
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }
}
