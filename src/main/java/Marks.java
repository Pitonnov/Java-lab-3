import java.util.ArrayList;
import java.util.Date;

public class Marks {
    private Student student;
    private Date date;
    private Group group; //студент мог переходить из группы в группу
    private Integer mark;

    public Marks(Student student, Date date, Integer mark){
        this.student = student;
        this.date = date;
        this.group = student.getGroup();
        this.mark = mark;
    }

    public Integer getMark(){
        return this.mark;
    }

    public Group getGroup(){
        return this.group;
    }

    public Date getDate(){
        return this.date;
    }
}
