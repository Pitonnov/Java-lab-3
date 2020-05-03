public class DeaneryExceptions {
    static class PasswordException extends Exception{
        public PasswordException(){
            super("PasswordException. Password.checkPassword get failed");
        }
    }
    static class NullDeaneryException extends Exception{
        public NullDeaneryException(){
            super("NullDeaneryException. Deanery.createNewDeanery created null");
        }
    }
    static class GroupDoesntContainStudentException extends Exception{
        public GroupDoesntContainStudentException(){
            super("GroupDoesntContainStudentException");
        }
    }
}
