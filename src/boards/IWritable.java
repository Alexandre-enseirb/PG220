package boards;

import java.util.ArrayList;

/**
 * Interface to give the cut to utils/IWriter
 */
public interface IWritable {

    /**
     * Exports the IWritable object as an ArrayList of strings
     * @return an ArrayList of strings describing the object
     */
    ArrayList<String> toStr();
}
