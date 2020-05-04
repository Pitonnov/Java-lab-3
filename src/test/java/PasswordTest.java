import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordTest {

    @Test
    public void checkPassword() {
        try{
            Password.checkPassword("777"); //password is 1234, not 777
            fail();
        }
        catch (DeaneryExceptions.PasswordException e){
            assertEquals("PasswordException. Password.checkPassword get failed", e.getMessage());
        }
    }

    @Test
    public void changePassword() throws DeaneryExceptions.PasswordException {
        Password.changePassword("1234","777");
        Password.checkPassword("777");
    }
}
