package boards;

/**
 * thrown if the delivery arrives after the order date
 */
public class TooLateException extends Exception {

    TooLateException(){}
    TooLateException(String message){
        super(message);
    }
}
