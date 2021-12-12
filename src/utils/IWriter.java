package utils;

import boards.IWritable;

import java.util.Scanner;

public interface IWriter {

    void openFile(String filename, Scanner sc);
    void writeToFile(IWritable w);
    //public void writeDecoupes(IWritable w);
    void closeFile();

    static IWriter instantiateXMLWriter(boolean append){
        return (IWriter) new XMLWriter(append);
    }
    static IWriter instantiateSVGWriter() { return new SVGWriter(); }
}
