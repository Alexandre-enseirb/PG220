package boards;

public class NotBigEnoughException extends Exception{

    NotBigEnoughException(){}

    NotBigEnoughException(String message){
        super(message);
    }
}
