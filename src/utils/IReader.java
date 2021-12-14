package utils;

import boards.IFactory;
import boards.IGenerable;

import java.util.ArrayList;

/**
 * Interface to handle the readers
 */
public interface IReader {
    /**
     * reads a list of lists of values from a file
     * @param filename name of the file
     * @return a list of lists of values
     */
    public ArrayList<ArrayList<Object>> read(String filename);

    /**
     * Instantiates a XML Reader
     * @return an XMLReader
     */
    static IReader InstantiateXMLReader(){
        return  new XMLReader();
    }



}
