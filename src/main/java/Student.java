import java.util.ArrayList;

class Student {
    private int id;
    private String name;
    private Group group;
    private ArrayList<Integer> marks;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    void printStudentData(){
        System.out.println("[ " + id + " ]" + " " + name + ", " + group.getTitle());
    }

    void printMarksForStudent() {
        System.out.print("Marks: ");
        for (Integer mark: marks) {
            System.out.print(mark + " ");
        }
        System.out.println();
    }

    void printAverageMarkForStudent(){
        System.out.printf("Average mark is %.2f\n", averageMark());
    }

    Group getGroup() {
        return group;
    }

    void setGroup(Group group) {
        this.group = group;
    }

    void addMark(Integer mark) {
        if ((mark >= 0) && (mark <= 5)) {
            marks.add(mark);
        }
    }

    Double averageMark() {
        Double sum = 0.0;
        for (Integer mark: marks) {
            sum += mark;
        }
        return sum / marks.size();
    }
}

