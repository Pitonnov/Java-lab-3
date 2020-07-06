import java.util.ArrayList;

public class Student implements Comparable<Student> {
    private int id;
    private String fio;
    private Group group;
    private ArrayList<Integer> assess;

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio){
        this.fio=fio;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Integer> getAssess(){
        return assess;
    }

    public Student(String fio, int id){
        this.fio=fio;
        this.id=id;
        assess=new ArrayList<Integer>();
    }

    public Group addToGroup(Group grade){
        this.group=grade;
        return group;
    }

    public boolean addAssess(int number){
        assess.add(number);
        return (assess.get(assess.size()-1))==number;
    }

    public double averageAssess(){
        int ammount=0;
        for(Integer i : assess)
            ammount+=i;

        return (double)ammount/assess.size();
    }

    @Override
    public int compareTo(Student st){
        int result;
        double resultOfDouble=averageAssess()-(st.averageAssess());

        if (resultOfDouble>0)
            result=1;
        else if(resultOfDouble<0)
            result=-1;
        else     //if ==
            result=0;

        return result;
    }

    @Override
    public String toString(){
        return "ID:"+id+" - "+fio;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Student))
            return false;
        Student student=(Student)object;

        return id==student.id && fio.equals(student.fio) && group==student.group;
    }

}
