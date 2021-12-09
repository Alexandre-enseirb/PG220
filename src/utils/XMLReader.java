package utils;
import boards.IFactory;
import boards.IGenerable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
class XMLReader implements IReader {
    private XMLStreamReader r;

    @Override
    public ArrayList<ArrayList<Object>> read(String filename) {
        FileInputStream file = null;
        IGenerable rdb;
        ArrayList<ArrayList<Object>> lesUsers = new ArrayList<>();
        try {
            file = new FileInputStream(filename);
            r = XMLInputFactory.newInstance().createXMLStreamReader(file);

            while (r.hasNext()) {
                if (r.getEventType()==1) { //XMLStreamConstants.START_ELEMENT
                    if (r.getName().toString() == "client" || r.getName().toString() == "fournisseur") {
                        lesUsers.add(readUsers(r));
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
        return lesUsers;
    }

    public ArrayList<Object> readUsers(XMLStreamReader r) {
        ArrayList<Object> Users = new ArrayList<>();


        Users.add(r.getAttributeValue(0));

        IGenerable c = null;

        try {
            while (r.hasNext()) {

                if (r.next() == XMLStreamConstants.START_ELEMENT)
                {

                    if (r.getName().toString() == "planche" || r.getName().toString() == "panneau"){

                        ArrayList<String> p = readPlanche(r);
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

    public ArrayList<String> readPlanche(XMLStreamReader r){

        ArrayList<String> p = new ArrayList<>();

        int id =Integer.parseInt(r.getAttributeValue(0));
        p.add(r.getAttributeValue(0));
        p.add(r.getAttributeValue(1));
        p.add(r.getAttributeValue(2));
        p.add(r.getAttributeValue(3));
        try {
            while (r.hasNext()){
                if(r.next() == XMLStreamConstants.START_ELEMENT){

                    if("dim".equalsIgnoreCase(r.getLocalName())){
                        p.add(r.getAttributeValue(0));
                        p.add(r.getAttributeValue(1));
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