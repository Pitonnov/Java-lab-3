import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Deanery {
//fields
    private ArrayList<Student> students;
    private ArrayList<Group> groups;
//constructor
    private Deanery(ArrayList<Student> students, ArrayList<Group> groups){
        this.students = students;
        this.groups = groups;
    }
//factories
    public static Deanery createNewDeanery(String password){
        try {
            Password.checkPassword(password);
            ArrayList<Student> students = new ArrayList<>();
            ArrayList<Group> groups = new ArrayList<>();
            return new Deanery(students, groups);
        } catch (DeaneryExceptions.PasswordException e) {
            System.out.println("Wrong password! Access's denied");
            return null;
        }
    }
//enums
    enum OUTPUTS{GROUPS, STUDENTS, ALL}
    //getters
    ArrayList<Group> getGroups(){ return groups;}
    ArrayList<Student> getStudents(){ return students;}
//methods
//1. creating students from a file
    void createStudentsFromFile(String fileDirection, String password) {
        try {
            Password.checkPassword(password);
            try {
                List<String> lines = Files.readAllLines(Paths.get(fileDirection));
                for (String line : lines) {
                    String[] sublines = line.split(" ", 2);
                    this.students.add(Student.createNewStudent(Integer.parseInt(sublines[0]), sublines[1], password));
                }
            }
            catch (NoSuchFileException e){
                System.out.println("File "+"'"+fileDirection+"'"+" not found");
                System.exit(1);
            }
            catch (InvalidPathException e){
                System.out.println("Wrong direction "+"'"+fileDirection+"'");
                System.exit(1);
            }
            catch (IOException e) {
                System.out.println("File "+"'"+fileDirection+"'"+" reading error");
                System.exit(1);
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            System.out.println("Wrong password! Access's denied");
        }
    }
//2. creating groups from a file
    void createGroupsFromFile(String fileDirection, String password) {
        try {
            Password.checkPassword(password);
            try {
                List<String> lines = Files.readAllLines(Paths.get(fileDirection));
                for (String line : lines) {
                    this.groups.add(Group.createNewGroup(line, password));
                }
            }
            catch (NoSuchFileException e){
                System.out.println("File "+"'"+fileDirection+"'"+" not found");
                System.exit(1);
            }
            catch (InvalidPathException e){
                System.out.println("Wrong direction "+"'"+fileDirection+"'");
                System.exit(1);
            }
            catch (IOException e) {
                System.out.println("File "+"'"+fileDirection+"'"+" reading error");
                System.exit(1);
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//3. add students to random groups
    void addAllStudentsToRandomGroups(String password){
        try {
            Password.checkPassword(password);
            for (Student student : this.students) {
                int randomGroupNumber = (int) (Math.random() * (this.groups.size()));
                Group randomGroup = this.groups.get(randomGroupNumber);
                randomGroup.addStudent(student, password);
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//4. random heads election
    void electHeads(String password){
        try {
            Password.checkPassword(password);
            for (Group group : this.groups) {
                int min = 1;
                int max = group.getStudents().size()-1;
                int diff = max - min;
                Random random = new Random();
                Student head = group.getStudents().get(random.nextInt(diff + 1));
                try {
                    group.chooseHead(head, password);
                }
                catch (DeaneryExceptions.GroupDoesntContainStudentException e){
                    System.out.println("Electing heads get failed");
                }
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//5. add random marks
    void addRandomMarks(int numberOfMarks, String password){
        try {
            Password.checkPassword(password);
            int maxMark = MARKS.MARKStoInt(MARKS.getMaxMark());
            int minMark = MARKS.MARKStoInt(MARKS.getMinMark());
            for (Student student : this.students) {
                for (int i = 1; i <= numberOfMarks ; i++) {
                    int randomMarkValue = (int)(Math.random()*((maxMark-minMark)+1))+minMark;
                    student.addMarks(password, MARKS.intToMARKS(randomMarkValue));
                }
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//6. rating statistics
    void ratingStatistics() {
        Comparator<Group> compGrByMiddleMark = new Comparator<Group>() {
            @Override
            public int compare(Group g1, Group g2) {
                int result = 0;
                result = MARKS.MARKStoInt(g2.getMiddleMark()) - MARKS.MARKStoInt(g1.getMiddleMark());
                return result;
            }
        };
        Comparator<Student> compStByMiddleMark = new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2)  {
                int result = 0;
                result = MARKS.MARKStoInt(s2.getMiddleMark())  - MARKS.MARKStoInt(s1.getMiddleMark());
                return result;
            }
        };
        this.groups.sort(compGrByMiddleMark);
        this.students.sort(compStByMiddleMark);
        for (Group group : this.groups) {
            group.getStudents().sort(compStByMiddleMark);
        }
    }
//7. student rotation around groups
    void changeGroup(String fio, String tittleNewGroup, String password){
        try {
            Password.checkPassword(password);
            Student student = Student.findStudentinArrayList(this.students, fio);
            Group oldGroup = student.getGroup();
            Group newGroup = this.findGroupByTittle(tittleNewGroup);
            oldGroup.removeStudent(student, password);
            newGroup.addStudent(student, password);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
    void changeGroup(int id, String tittleNewGroup, String password){
        try {
            Password.checkPassword(password);
            Student student = Student.findStudentinArrayList(this.students, id);
            Group oldGroup = student.getGroup();
            Group newGroup = this.findGroupByTittle(tittleNewGroup);
            oldGroup.removeStudent(student, password);
            newGroup.addStudent(student, password);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//8. students dismissing
    void removeStudentFromDeanery(Student student, String password){
        try {
            Password.checkPassword(password);
            student.getGroup().removeStudent(student, password);
            this.students.remove(student);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
    void removeStudentFromDeanery(String fio, String password){
        try {
            Password.checkPassword(password);
            Student student = Student.findStudentinArrayList(this.students, fio);
            student.getGroup().removeStudent(student, password);
            this.students.remove(student);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
    void removeStudentFromDeanery(int id, String password){
        try {
            Password.checkPassword(password);
            Student student = Student.findStudentinArrayList(this.students, id);
            student.getGroup().removeStudent(student, password);
            this.students.remove(student);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
    void removeStudentsByStatistics(MARKS limitMark, String password){
        try {
            Password.checkPassword(password);
            this.ratingStatistics();
            for (int i = this.students.size()-1; i >=0 ; i--) {
                if (this.students.get(i).getMiddleMark() == limitMark) {
                    this.removeStudentFromDeanery(this.students.get(i), password);
                }
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//9. output information
    private void output(PrintStream ps, String outputParameter){
        if(outputParameter.equals(OUTPUTS.GROUPS.toString())){
            for (Group group : this.groups) {
            ps.println(group.getTitle());
            }
            ps.println("Number of groups: "+this.groups.size());
        }
        else if(outputParameter.equals(OUTPUTS.STUDENTS.toString())){
            for (Student student : this.students) {
                ps.println(student.getId()+" "+student.getFio());
            }
            ps.println("Number of students: "+this.students.size());
        }
        else if(outputParameter.equals(OUTPUTS.ALL.toString())){
            String format1 = "%1$-3s|%2$-25s|%3$-16s|%4$-15s\n";
            String format2 = "%1$-3d|%2$-25s|%3$-16s|";
            ps.println("ALL INFORMATION");
            ps.println("Number of groups: "+this.groups.size());
            ps.println("Number of students: "+this.students.size());
            ps.println("");
            for (Group group : this.groups) {
                ps.println("Group: "+group.getTitle());
                ps.println("Head: "+group.getHead().getFio());
                ps.println("Number of students: "+group.getStudents().size());
                if(group.getMiddleMark() != null) {
                    ps.println("Group middle mark: " + group.getMiddleMark());
                }
                else{
                    ps.println("Group middle mark: " + "NO MIDDLE MARK");
                }
                ps.format(format1,"ID","FIO","Middle Mark","Marks");
                for (Student student : group.getStudents()) {
                    if(student.getMiddleMark() != null) {
                        ps.format(format2, student.getId(), student.getFio(), student.getMiddleMark());
                        for (MARKS mark : student.getMarks()) {
                            ps.print(MARKS.MARKStoInt(mark) + " ");
                        }
                        ps.println("");
                    }
                    else{
                        ps.format(format2, student.getId(), student.getFio(), "NO MARKS");
                        ps.println("");
                    }
                }
                ps.println("");
            }
        }
    }
//9.1 output to display
    void outputToDisplay(String outputParameter, String password) {
        try {
            Password.checkPassword(password);
            this.output(System.out,outputParameter);
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
//9.2 output to file
    void outputToFile(String fileDirection, String outputParameter, String password) {
        try {
            Password.checkPassword(password);
            try {
                File file = new File(fileDirection);
                PrintStream fileOut = new PrintStream(file);
                this.output(fileOut,outputParameter);
            }
            catch (InvalidPathException e){
                System.out.println("Wrong direction "+"'"+fileDirection+"'");
                System.exit(1);
            }
            catch (IOException e) {
                System.out.println("File "+"'"+fileDirection+"'"+" recording error");
                System.exit(1);
            }
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
// others
// find group by tittle
    Group findGroupByTittle(String tittle){
        Group resultGroup = null;
        for (Group group : this.groups) {
            if (group.getTitle().equals(tittle)){
                resultGroup = group;
            }
        }
        return resultGroup;
    }
}