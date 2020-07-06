package Deanery;

public class DeaneryExceptions {
    public static class PasswordException extends Exception{
        public PasswordException(){
            super("PasswordException. Password.checkPassword get failed");
        }
    }
    public static class NullDeaneryException extends Exception{
        public NullDeaneryException(){
            super("NullDeaneryException. Deanery.createNewDeanery created null");
        }
    }
    public static class GroupDoesntContainStudentException extends Exception{
        public GroupDoesntContainStudentException(){
            super("GroupDoesntContainStudentException");
        }
    }
}
