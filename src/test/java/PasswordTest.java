import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordTest {

    @Test
    void checkPassword() {
        try{
            Password.checkPassword("777"); //password is 1234, not 777
            fail();
        }
        catch (DeaneryExceptions.PasswordException e){
            assertEquals("PasswordException. Password.checkPassword get failed", e.getMessage());
        }
    }

    @Test
    void changePassword() throws DeaneryExceptions.PasswordException {
        Password.changePassword("1234","777");
        Password.checkPassword("777");
    }
}
