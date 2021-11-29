package utils;

import boards.IFactory;
import boards.IGenerable;

import java.util.ArrayList;

public interface IReader {
    public ArrayList<IGenerable> read(IFactory f, String filename);
    static IReader InstantiateXMLReader(){
        return  new XMLReader();
    }

}
