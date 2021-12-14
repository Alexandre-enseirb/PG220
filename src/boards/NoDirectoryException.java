package boards;

/**
 * thrown if the folder given as an argument does not exist
 */
class NoDirectoryException extends Exception {
    NoDirectoryException(String message) { super(message); }
}
