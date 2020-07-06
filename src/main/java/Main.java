import org.json.JSONException;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, JSONException, ParseException, java.text.ParseException {
        Deanery deanery = new Deanery();
        deanery.readJSON();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date begin = format.parse("01.05.2020");
        Date end = format.parse("31.05.2020");
        deanery.addRandomMarks(10, begin, end);
        //dec.printStudents();
        deanery.printStudentsMarks("381807м", begin, end);
        deanery.studentExplusion(4); ////fio = Исакова Оксана Даниловна   group = 381807м
        deanery.changeGroup(deanery.getStudent(5), deanery.getGroup("381904м"));
        System.out.println("");
        deanery.printStudents();
        //результат в файле output.json
        deanery.writeJSON();
    }
}
