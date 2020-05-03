import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public enum MARKS {
//elements (marks)
    PERFECTLY(5),
    WELL(4),
    SATISFACTORILY(3),
    UNSATISFACTORILY(2),
    BADLY(1);

//fields
    private int markValue;
//constructor
    MARKS(int markValue){ this.markValue = markValue; }
//methods
    static int MARKStoInt(MARKS mark){
        return mark.markValue;
    }
    static MARKS intToMARKS(int value){
        ArrayList<MARKS> allMarks = new ArrayList<>(Arrays.asList(MARKS.values()));
        Comparator<MARKS> compMARKS = (m1, m2) -> {
            int result = m1.markValue - m2.markValue;
            return result;
        };
        allMarks.sort(compMARKS);
        MARKS resultMARK = BADLY;
        for (int i = 0; i < allMarks.size(); i++) {
            if (value <= allMarks.get(i).markValue){
                resultMARK = allMarks.get(i);
                break;
            }
            else{
                if(i == allMarks.size()-1){
                    resultMARK = allMarks.get(i);
                    break;
                }
            }
        }
        return resultMARK;
    }

    static MARKS getMaxMark(){
        int maxMarkValue = 0;
        for (MARKS mark : MARKS.values()) {
            if (MARKS.MARKStoInt(mark) > maxMarkValue){
                maxMarkValue = MARKS.MARKStoInt(mark);
            }
        }
        return MARKS.intToMARKS(maxMarkValue);
    }
    static MARKS getMinMark(){
        int minMarkValue = MARKS.MARKStoInt(MARKS.getMaxMark());
        for (MARKS mark : MARKS.values()) {
            if (MARKS.MARKStoInt(mark) < minMarkValue){
                minMarkValue = MARKS.MARKStoInt(mark);
            }
        }
        return MARKS.intToMARKS(minMarkValue);
    }
}
