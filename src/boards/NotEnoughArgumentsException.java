package boards;

/**
 * thrown if main is started without an argument
 */
class NotEnoughArgumentsException extends Exception {
    NotEnoughArgumentsException(String message) { super(message); }
}
