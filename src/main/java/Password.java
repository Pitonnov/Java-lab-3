import java.awt.*;

public class Password {
//fields
    private static String password = "1234";
    private static String answer = "Wrong password! Access's denied";
//methods
//print answer
    public static void printAnswer(){
        System.out.println(answer);
    }
//check password
    public static void checkPassword(String password) throws DeaneryExceptions.PasswordException {
        if(!(password.equals(Password.password))){
            throw new DeaneryExceptions.PasswordException();
        }
    }
//change password
    public static void changePassword (String oldPassword, String newPassword) {
        try {
            Password.checkPassword(oldPassword);
            Password.password = newPassword;
        }
        catch (DeaneryExceptions.PasswordException e){
            Password.printAnswer();
        }
    }
}
