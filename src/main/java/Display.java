import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
//this class is used for input and output
public class Display {
    public static void write(String massage){
        System.out.println(massage);
    }
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static String read(){
        try {
            return br.readLine();
        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
//provide pattern of error massage
class ErrMassage{
    public static String unregisteredStudent(Student student){
        return student.toString() + " is unregistered";
    }
    public static String enrollingFailed(){
        return "Enrolling failed! ";
    }
    public static String unregisteredGroup(String title){
        return title + " group doesn't exist";
    }
    public static String enrolledStudent(Student student){
        return student.toString() + " is already enrolled into " + student.sayGroup() + "group";
    }
    public static String unenrolledStudent(Student student){
        return student.toString() + " isn't enrolled into any group";
    }
    public static String ratingFailed(){
        return "Rating failed! ";
    }
    public static String invalidMark(){
        return "Invalid mark! Possible values : 1, 2, 3, 4, 5";
    }
    public static String invalidName(String name) { return  "Name '" + name + "' is invalid";}
    public static String changingGroupFailed(){
        return "Changing group failed! ";
    }
    public static String expellingStudentFailed(){
        return "Expelling student failed! ";
    }
}
//do parsing commands
class Dialog{
    public static Deanery deanery;
    private static Student student;
    private static Group group;
    private static ROLES role = ROLES.DEANERY;

    public static void run(){
        while (command(Display.read()));
    }

    public static boolean command(String command){
        if (!command.isEmpty())
            return execute(command.split("\\s+"));
        else {
            Display.write("You must write a command");
            return true;
        }
    }

    public static boolean execute(String[] params){
        COMMANDS cmnd = COMMANDS.EMPTY;
        boolean continuation = true;
        for (COMMANDS command : COMMANDS.values()){
            if (params[0].equalsIgnoreCase(command.toString()))
                cmnd = command;
        }

        if (cmnd == COMMANDS.EXIT) {
            deanery.saveProgress();
            return false;
        }
        if (changingRole(cmnd,params))
            return true;
        switch(role){
            case DEANERY: deaneryCommands(cmnd, params); break;
            case GROUP: groupCommands(cmnd, params); break;
            case STUDENT: studentCommands(cmnd, params); break;
        }
        return true;
    }

    private static boolean changingRole(COMMANDS cmnd, String[] params){
        switch(cmnd){
            case CRS:
                if ((student = deanery.getStudent(checkNumber(params))) != null) {
                    role = ROLES.STUDENT;
                    Display.write("Your role is " + student.toString());
                }
                return true;
            case CRG:
                if ((group = deanery.getGroup(makeName(params))) != null) {
                    role = ROLES.GROUP;
                    Display.write("Your role is " + group.toString());
                }
                return true;
            case CRD:
                role = ROLES.DEANERY;
                return true;
        }
        return false;
    }

    private static void deaneryCommands(COMMANDS cmnd, String[] params){
        switch (cmnd) {
            case CS:
                deanery.createStudent(makeName(params));
                break;
            case CG:
                deanery.createGroup(makeName(params));
                break;
            case FS:
                if (params.length >= 2) {
                    if (checkNumber(params) != -1)
                        deanery.findStudent(checkNumber(params));
                    else
                        deanery.findStudent(makeName(params));
                }
                else
                    Display.write("You must give name or id of student");
                break;
            case GM:
                deanery.addRatings();
                break;
            case ES:
                deanery.expelStudent(deanery.getStudent(checkNumber(params)));
                break;
            case EPS:
                deanery.expelPoorStudents();
                break;
            case HE:
                deanery.doHeadElection();
                break;
            case SS:
                deanery.showStudentList();
                break;
            case SG:
                deanery.showGroupList();
                break;
            case SH:
                deanery.showGroupHeads();
                break;
            case SPS:
                deanery.showPoorStudent();
                break;
            case NS:
                Display.write("Number of students : " + deanery.getNumberOfStudents());
                break;
            case TR:
                if (params.length >= 3){
                    deanery.transferring(deanery.getStudent(checkNumber(params)), makeName(Arrays.copyOfRange(params, 1, params.length)));
                } else
                    Display.write("You must give student id and group title");
                break;
            case SP:
                deanery.saveProgress();
                break;
            case LP:
                deanery.loadProgress();
                break;
            case CDS:
                deanery.createStudentsAndGroups();
                break;
            case HELP:
                Display.write("CS 'name' : create student\n" +
                              "CG 'title' : create group\n" +
                              "FS 'name / id' : find student\n" +
                              "GM : give marks to all students\n" +
                              "ES 'id' : expel student\n" +
                              "EPS : expel poor students" +
                              "HE : run head election of all groups\n" +
                              "SS : show list of all students\n" +
                              "SG : show list of all groups\n" +
                              "SPS : show poor students" +
                              "NS : show number of students\n" +
                              "TR 'id' 'title' : transfer student to another group" +
                              "SP : save progress" +
                              "LP : load progress" +
                              "CRG 'title' : change role to a Group\n" +
                              "CRS 'id' : change role to a student\n" +
                              "EXIT : exit\n");
                break;
            default:
                Display.write("Deanery : Unknown command \"" + params[0] + "\" enter HELP for help");
                break;
        }
    }

    private static void groupCommands(COMMANDS cmnd, String[] params){
        switch (cmnd){
            case FS:
                if (params.length >= 2) {
                    if (checkNumber(params) != -1)
                        Display.write(group.findStudent(checkNumber(params)));
                    else
                        Display.write(group.findStudent(makeName(params)));
                }
                else
                    Display.write("You must give name or id of student");
                break;
            case HE:
                group.headElection();
                break;
            case GT:
                Display.write(group.getTitle());
                break;
            case WH:
                group.whoIsHead();
                break;
            case AR:
                Display.write("Average group rating = " + group.averageRating());
                break;
            case HELP:
                Display.write("FS 'name / id' : find student\n" +
                              "HE : run head election of the group\n" +
                              "GT : show title of the group\n" +
                              "WH : show who is the head of the group\n" +
                              "AR : show average rating for the group\n" +
                              "CRD : change role to the Deanery\n" +
                              "CRS 'id' : change role to a student\n" +
                              "EXIT : exit\n");
                break;
            default:
                Display.write("Group : Unknown command \"" + params[0] + "\" enter HELP for help");
                break;
        }
    }

    private static void studentCommands(COMMANDS cmnd, String[] params){
        switch (cmnd){
            case ET:
                student.enrollTo(makeName(params));
                break;
            case AM:
                student.addMark(checkNumber(params));
                break;
            case SN:
                Display.write("My name is " + student.getName());
                break;
            case ID:
                Display.write("My id : " + student.getId());
                break;
            case SG:
                Display.write("I'm into " + student.sayGroup() + "group");
                break;
            case AR:
                Display.write("My average rating = " + student.averageRating());
                break;
            case SM:
                Display.write(student.markList());
                break;
            case TR:
                student.transferTo(makeName(params));
                break;
            case HELP:
                Display.write("ET 'title' : enroll the student into a group\n" +
                              "AM 'mark' : add mark to the student\n" +
                              "SN : show name of the student\n" +
                              "ID : show id of the student\n" +
                              "SG : show group of the student\n" +
                              "AR : show average rating of the student\n" +
                              "SM : show all marks of the student\n" +
                              "CRD : change role to the Deanery\n" +
                              "CRG 'title' : change role to a Group\n" +
                              "CRS 'id' : change role to a student\n" +
                              "EXIT : exit\n");
            default:
                Display.write("Student : Unknown command \"" + params[0] + "\" enter HELP for help");
                break;
        }
    }

    private static String makeName(String[] params){
        if (params.length < 2)
            return null;
        String name = "";
        for (int i = 1; i < params.length; i++)
            name += (" " + params[i]);
        return name.trim();
    }
    private static int checkNumber(String[] params){
        if (params.length < 2)
            return 0;
        try{
            return Integer.parseInt(params[1]);
        } catch(NumberFormatException ex) {
            return -1;
        }
    }
}

//types of command
enum COMMANDS {
    EMPTY, EXIT, CS, CG, FS, GM, ES, EPS, HE, SS, SG, SH, SPS, NS, GT, WH, AR, ET, AM, SN, ID, SM, TR, SP, LP, CDS, CRS, CRG, CRD, HELP
}
//types of roles
enum ROLES {
    DEANERY, GROUP, STUDENT
}


