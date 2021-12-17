package utils;
import boards.IFactory;
import boards.IGenerable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
class XMLReader implements IReader {
    private XMLStreamReader r;

    /**
     * reads the .XML file given as parameter
     * @param filename name of the file
     * @return a list of lists of values
     */
    @Override
    public ArrayList<ArrayList<Object>> read(String filename) {
        FileInputStream file = null;
        IGenerable rdb;
        ArrayList<ArrayList<Object>> listUsers = new ArrayList<>();
        try {
            file = new FileInputStream(filename);
            r = XMLInputFactory.newInstance().createXMLStreamReader(file);
            String name="";
            while (r.hasNext()) {
                if (r.getEventType()==1) { //XMLStreamConstants.START_ELEMENT
                    name = r.getName().toString();

                    if (r.getName().toString() == "client" || r.getName().toString() == "supplier") {
                        listUsers.add(readUsers(r));
                    } else {
                        r.next();
                    }

                } else {
                    r.next();
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    /**
     * Reads a user
     * @param r an XMLStreamReader used to read the fields
     * @return a list of values read
     */
    public ArrayList<Object> readUsers(XMLStreamReader r) {
        ArrayList<Object> Users = new ArrayList<>();


        Users.add(r.getAttributeValue(0));

        IGenerable c = null;
        String name="";
        try {
            while (r.hasNext()) {

                if (r.next() == XMLStreamConstants.START_ELEMENT)
                {
                    name = r.getName().toString();
                    if (r.getName().toString() == "board"){

                        ArrayList<String> p = readBoard(r);
                        Users.add(p);

                    }else{
                        return Users;
                    }


                }

                // r.next();


            }
        } catch (XMLStreamException e) {

        }

        //c = f.generatePeople(id,listplanche);
        return Users;

    }

    /**
     * reads a board
     * @param r an XMLStreamReader used to read the fields
     * @return a list of values read
     */
    public ArrayList<String> readBoard(XMLStreamReader r){

        ArrayList<String> p = new ArrayList<>();

        int id =Integer.parseInt(r.getAttributeValue(0));
        p.add(r.getAttributeValue(0));                                  // data(0)
        p.add(r.getAttributeValue(1));                                  // data(1)
        p.add(r.getAttributeValue(2));                                  // data(2)
        p.add(r.getAttributeValue(3));                                  // data(3)
        String name ="";
        try {
            while (r.hasNext()){
                if(r.next() == XMLStreamConstants.START_ELEMENT){
                    name=r.getLocalName();
                    if("dim".equalsIgnoreCase(name)){
                        p.add("rect");                                      // data(4)
                        p.add(r.getAttributeValue(0));                   // data(5)
                        p.add(r.getAttributeValue(1));                   // data(6)
                    }
                    if ("p".equalsIgnoreCase(name)){
                        int counter = 0;
                        p.add("poly");                                      // data(4)
                        while (r.hasNext() && r.getName().toString().equals("p")){
                            counter++;
                            p.add(r.getAttributeValue(0));                   // data(5)
                            p.add(r.getAttributeValue(1));                   // data(6)
                        }
                        p.add(Integer.toString(counter));
                        

                    }

                    return p;
                }
                //r.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return p;
    }



    XMLReader() {

    }
}