package boards;

public class TooLateException extends Exception {

    TooLateException(){}
    TooLateException(String message){
        super(message);
    }
}
