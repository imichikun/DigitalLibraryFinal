package library_rest.util;

public class UsersIsAlreadyRegisteredException extends RuntimeException{
    public UsersIsAlreadyRegisteredException(String message) {
        super(message);
    }
}