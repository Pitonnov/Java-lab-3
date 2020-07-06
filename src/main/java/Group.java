import java.util.ArrayList;
import java.util.Random;

public class Group {
    private static int countGroup = 0;
    private String title;
    public ArrayList<Student> studentsGroup;
    private Student head;

    public Group(String nameGroup) {
        this.title = nameGroup;
        studentsGroup = new ArrayList<Student>();
        head = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Student getHead(){
        return head;
    }

    public void setHead(Student st) {
        this.head = st;
    }

    public boolean addToGroup(Student student) {
        studentsGroup.add(student); //add student to group
        student.addToGroup(this); //student has a link to the group

        return (studentsGroup.get(studentsGroup.size() - 1)) == student;
    }

    public Student choiceHead() {
        Random random = new Random(47);
        int j;
        if (this.head == null) {
            j = random.nextInt(studentsGroup.size())-1; //because index of students 0...(group.size()-1)
            head = studentsGroup.get(j);
        }

        return head;
    }

    public Student searchStudent(int id) {
        for (Student st : studentsGroup)
            if (st.getId() == id)
                return st;

        return null;
    }

    public ArrayList<Student> searchStudent(String fio) {
        ArrayList<Student> resultSearch = new ArrayList<Student>();
        for (Student st : studentsGroup)
            if (st.getFio().equals(fio))
                resultSearch.add(st);

        return resultSearch;
    }

    public double amountAverageBall() {
        double ball = 0;
        for (Student st : studentsGroup) {
            if((st.getAssess()).size()==0) //if student doesn't have marks
                ball +=0;
            else //if student have marks
                ball += st.averageAssess();
        }

        return (double)(ball/(double)studentsGroup.size());
    }

    public boolean exclusionFromTheGroup(Student studentForExclusion) {
        int before = studentsGroup.size();
        for (Student st : studentsGroup)
            if (st == studentForExclusion) { //address comparison
                studentForExclusion.addToGroup(null);
                studentsGroup.remove(studentForExclusion);
                if(head==studentForExclusion)
                    head=null;
                break;
            }

        return (before - studentsGroup.size()) == 1;
    }

    @Override
    public String toString(){
        return (title+":\nNumber of students - "+studentsGroup.size()+"\n");
    }

}
