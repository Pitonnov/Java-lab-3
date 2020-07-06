import java.util.ArrayList;

class Student {
    private int id;
    private String fio;
    private Group group;
    private ArrayList <Integer> marks = new ArrayList<>();

    private Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return fio;
    }

    public static Student generate(int id, String fio){
        return new Student(id,fio);
    }

    public String getFio() {
        return fio;
    }

    public int getId() {
        return id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void addMarks(Integer marks)throws BadMarkException{
            if (marks>=2&&marks<=5)
                this.marks.add(marks);
            else throw new BadMarkException("Не соотвествует диапазону значений 2-5");
    }
    public float averageMark() {
        float result = 0;
        for (Integer i: marks) {
            result+=i;
        }
        return result/marks.size();
    }
}
