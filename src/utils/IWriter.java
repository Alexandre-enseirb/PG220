package utils;

import boards.IWritable;

import java.io.IOException;

public interface IWriter {

    void openFile(String filename);
    void writeToFile(IWritable w);
    void closeFile();

    static IWriter instantiateXMLWriter(boolean append){
        return (IWriter) new XMLWriter(append);
    }
    static IWriter instantiateSVGWriter() { return new SVGWriter(); }
}
