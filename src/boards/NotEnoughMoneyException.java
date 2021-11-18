package boards;

public class NotEnoughMoneyException extends Exception {

    NotEnoughMoneyException(){}
    NotEnoughMoneyException(String message){
        super(message);
    }
}
