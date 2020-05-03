import static org.junit.Assert.*;
import org.junit.Test;

public class MARKSTest {
	
    @org.junit.Test
    void MARKStoInt() {
        assertEquals(1,MARKS.MARKStoInt(MARKS.BADLY));
        assertEquals(2,MARKS.MARKStoInt(MARKS.UNSATISFACTORILY));
        assertEquals(3,MARKS.MARKStoInt(MARKS.SATISFACTORILY));
        assertEquals(4,MARKS.MARKStoInt(MARKS.WELL));
        assertEquals(5,MARKS.MARKStoInt(MARKS.PERFECTLY));
    }

    @org.junit.Test
    void intToMARKS() {
        assertEquals(MARKS.BADLY,MARKS.intToMARKS(1));
        assertEquals(MARKS.UNSATISFACTORILY,MARKS.intToMARKS(2));
        assertEquals(MARKS.SATISFACTORILY,MARKS.intToMARKS(3));
        assertEquals(MARKS.WELL,MARKS.intToMARKS(4));
        assertEquals(MARKS.PERFECTLY,MARKS.intToMARKS(5));
        assertEquals(MARKS.BADLY,MARKS.intToMARKS(0));
        assertEquals(MARKS.PERFECTLY,MARKS.intToMARKS(6));
    }

    @org.junit.Test
    void getMaxMark() {
        assertEquals(MARKS.PERFECTLY,MARKS.getMaxMark());
    }

    @org.junit.Test
    void getMinMark() {
        assertEquals(MARKS.BADLY,MARKS.getMinMark());
    }
}