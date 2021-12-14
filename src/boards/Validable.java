package boards;

/**
 * Interface to facilitate the verification of a few values about the boards
 */
public interface Validable {

    /**
     * tests if the Validable is valid. Each Validable depends on their own condition
     * @return true if it is valid, false else
     */
    public boolean isValid();
}
