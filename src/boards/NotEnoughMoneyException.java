package boards;

/**
 * thrown if not enough money
 */
public class NotEnoughMoneyException extends Exception {

    NotEnoughMoneyException(){}
    NotEnoughMoneyException(String message){
        super(message);
    }
}
