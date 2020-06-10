package University;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
public class Group {
    private static int maxId=0;
    private int id;
    public int getId(){return id;}
    private String title;
    public String getTitle() {
        return title;
    }
    private ArrayList<Student> students;
    public ArrayList<Student> getStudents()
    {
        return  students;
    }
    private  Student head;
    public Group(String title)
    {
        id=maxId;
        this.title=title;
        students=new ArrayList<>();
        maxId++;
    }
    public Student getHead()
    {
        return  head;
    }
    public  void addStudent(Student student)
    {
        students.add(student);
        student.enrollment(this);
    }
    public void chooseHead()
    {
        int randomStudent = ThreadLocalRandom.current().nextInt(0, students.size());
        head=students.get(randomStudent);
    }
    public Student getStudentByKey(int id)
    {
        for(int i=0;i<students.size();i++)
        {
            if(students.get(i).getID()==id)
                return students.get(i);
        }
        return null;
    }
    public Student getStudentByKey(String name, String surname, String patronym)
    {
        for(int i=0;i<students.size();i++)
        {
            Student student=students.get(i);
            if(name.equals(student.getName())||name.equals(""))
                if(surname.equals(student.getSurname())||surname.equals(""))
                    if(patronym.equals(student.getPatronym())||patronym.equals(""))
                        return students.get(i);
        }
        return null;
    }
    public float getAverageMark()
    {
        float sum=0;
        for(int i=0;i<students.size();i++)
        {
            sum+=students.get(i).getAverageMark();
        }
        return  sum/students.size();
    }
    public  void excludeStudent(int id)
    {
        for(int i=0;i<students.size();i++)
        {
            if(students.get(i).getID()==id)
            {
                students.get(i).enrollment(null);
                students.remove(i);
            }
        }
    }
    public  void excludeStudent(Student student)
    {
        students.remove(student);
    }
}
