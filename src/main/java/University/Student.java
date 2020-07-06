package University;

import java.util.ArrayList;

public class Student {
    private  static int studentsNumber=0;
    private int id;
    private String name,surname,patronym;
    private Group group;
    private ArrayList<Float> marks;
    public  Student(String name,String surname,String patronym)
    {
        id=studentsNumber++;
        this.name=name;
        this.surname=surname;
        this.patronym=patronym;
        marks=new ArrayList<>();
    }
    public  Student(int id,String name,String surname,String patronym)
    {
        this(name,surname,patronym);
        this.id=id;
    }
    public void enrollment(Group group)
    {
        this.group=group;
    }
    public  void addMark(float mark)
    {
        marks.add(mark);
    }
    public  float getAverageMark()
    {
        float sum=0;
        for(int i=0;i<marks.size();i++)
        {
            sum+=marks.get(i);
        }
        return sum/marks.size();
    }
    public int getID()
    {
        return  id;
    }
    public  String getName(){return name;}
    public  String getSurname(){return surname;}
    public  String getPatronym(){return patronym;}
    public  String getFIO()
    {
        return name+" "+surname+" "+patronym;
    }
    public  ArrayList<Float> getMarks(){return marks;}
    public  Group getGroup(){ return  group;}
}
