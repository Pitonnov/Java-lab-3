import java.util.*;

class Group {
    private String title;
    private ArrayList<Student> students = new ArrayList<>();
    private Student head;

    private Group(String title){
        this.title = title;
    }

    public static Group generate(String fio){
        return new Group(fio);
    }

    public String getTitle() {
        return title;
    }

    public int addStudent (Student st){
        this.students.add(st);
        return students.size();
    }

    public String addHead (Student st) throws NoSuchElementException{
        if (students.contains(st)){
            this.head = st;
            return st.getFio() + " - назначен/на старостой";
        }
        else
            throw new NoSuchElementException("Студент не найден!");
    }

    public Student searchStudent (int id)throws NoSuchElementException{
        for (Student i: students){
            if (i.getId()==id)
                return i;
        }
        throw new NoSuchElementException("Студент не найден!");
    }

    public Student searchStudent (String fio)throws NoSuchElementException{
        for (Student i: students){
            if (i.getFio().equals(fio))
                return i;
        }
        throw new NoSuchElementException("Студент не найден!");
    }

    public ArrayList<Student> getStudents (){
        return students;
    }

    public float averageMark (){
        if (students.size()!=0) {
            float Marks = 0;
            for (Student i : students) {
                Marks+=i.averageMark();
            }
            return Marks/students.size();
        }
        else
            return 0;
    }

    public Student ExeceptStudent (int id){
        for (Student i: students){
            if (i.getId()==id) {
                i.setGroup(null);
                students.remove(i);
                return i;
            }
            else
                continue;
        }
        return null;
    }
}
