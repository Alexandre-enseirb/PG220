package utils;

import boards.IWritable;

import java.util.ArrayList;
import java.util.Scanner;

public interface IWriter {

    /**
     * Opens the file. Asks the user if it's okay to overwrite existing file.
     * @param filename name of the file
     * @param sc scanner to get prompts from the user
     */
    void openFile(String filename, Scanner sc);

    /**
     * writes the data from w to a file
     * @param w IWritable object
     */
    void writeToFile(ArrayList<IWritable> w);
    //public void writeDecoupes(IWritable w);

    /**
     * closes the file
     */
    void closeFile();

    /**
     * Instantiates and returns a XMLWriter object
     * @param append if true, will append data to any already existing file with the same name. If false and file exists, user will be prompted to overwrite it.
     * @return XMLWriter object cast as IWriter
     */
    static IWriter instantiateXMLWriter(boolean append){
        return (IWriter) new XMLWriter(append);
    }

    /**
     * Instantiates and returns a SVGWriter object
     * @return SVGWriter object cast as IWriter
     */
    static IWriter instantiateSVGWriter() { return new SVGWriter(); }
}
