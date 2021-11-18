package boards;

public class NotEnoughBoardsException extends Exception{

    NotEnoughBoardsException(){}
    NotEnoughBoardsException(String message){
        super(message);
    }
}
