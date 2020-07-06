import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.junit.Assert.*;

public class DeaneryTest {

    @org.junit.Test
    public void getGroup() throws JSONException, ParseException, IOException {
        Deanery deanery = new Deanery();
        deanery.readJSON();
        Group g = deanery.getGroup("381807м");
        assertEquals(g.getTitle().equals("381807м"), true);

    }

    @org.junit.Test
    public void setHead() throws JSONException, ParseException, IOException {
        Deanery deanery = new Deanery();
        deanery.readJSON();
        assertEquals(deanery.getGroup("381807м").getHead(), null);
        deanery.setHead("381807м", 5);
        assertEquals(deanery.getGroup("381807м").getHead().getFio().equals("Сасько Сергей Артёмович"), true);
    }

    @org.junit.Test
    public void getStudent() throws JSONException, ParseException, IOException {
        /*
        "id": 5,
		"student": "Сасько Сергей Артёмович",
		"group": "381807м"  -->  381904м
         */
        Deanery deanery = new Deanery();
        deanery.readJSON();
        Integer id = 5;
        assertEquals(deanery.getStudent(id).getFio().equals("Сасько Сергей Артёмович"), true);
    }

    @org.junit.Test
    public void studentExplusion() throws JSONException, ParseException, IOException {
        Deanery deanery = new Deanery();
        deanery.readJSON();
        Integer id = 4; //fio = Исакова Оксана Даниловна   group = 381807м
        assertEquals(deanery.getStudent(id).getFio().equals("Исакова Оксана Даниловна"), true);
        deanery.studentExplusion(4);
        assertEquals(deanery.getStudent(id), null);
    }

    @org.junit.Test
    public void changeGroup() throws JSONException, ParseException, IOException {
        /*
        "id": 5,
		"student": "Сасько Сергей Артёмович",
		"group": "381807м"  -->  381904м
         */
        Integer id = 5;
        Deanery deanery = new Deanery();
        deanery.readJSON();
        assertEquals(deanery.getStudent(5).getGroup().getTitle().equals("381807м"), true);
        deanery.changeGroup(deanery.getStudent(5), deanery.getGroup("381904м"));
        assertEquals(deanery.getStudent(5).getGroup().getTitle().equals("381904м"), true);
    }

}