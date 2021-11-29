package utils;

import boards.IFactory;
import boards.IGenerable;

import java.util.ArrayList;


public interface IReader {
    public ArrayList<ArrayList<Object>> read(String filename);
    static IReader InstantiateXMLReader(){
        return  new XMLReader();
    }



}
