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
    public ArrayList<IGenerable> read(IFactory f, String filename) {
        FileInputStream file = null;
        ArrayList<IGenerable> lesUsers = new ArrayList<>();
        try {
            file = new FileInputStream(filename);
            r = XMLInputFactory.newInstance().createXMLStreamReader(file);
            //int evt = 0;
            while (r.hasNext()) {
                if (r.getEventType()==1) { //XMLStreamConstants.START_ELEMENT
                    if (r.getName().toString() == "client") {
                        lesUsers.add(readClient(f,r));
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

    public IGenerable readClient(IFactory f, XMLStreamReader r) {
        int id = Integer.parseInt(r.getAttributeValue(0));
        ArrayList<IGenerable> listplanche = new ArrayList<>();
        IGenerable c = null;
        System.out.println("reading Client"+id);
        try {
            while (r.hasNext()) {

                if (r.next() == XMLStreamConstants.START_ELEMENT)
                {
                    // System.out.println("reading Client"+id);
                    if (r.getName().toString() == "planche"){

                        IGenerable p = readPlanche(f,r);
                        listplanche.add(p);

                    }else{
                        System.out.println("my id is "+id);
                        c = f.generatePeople(id,listplanche);
                        return c;
                    }


                }

                // r.next();


            }
        } catch (XMLStreamException e) {

        }

        c = f.generatePeople(id,listplanche);
        return c;

    }

    public IGenerable readPlanche(IFactory f,XMLStreamReader r){
        int id =Integer.parseInt(r.getAttributeValue(0));
        int nbr = Integer.parseInt(r.getAttributeValue(1));
        String date = r.getAttributeValue(2);
        double prix = Double.parseDouble(r.getAttributeValue(3));
        ArrayList<IGenerable> dim = null;
        IGenerable p = null;
        //System.out.println("reading planche"+ id);
        try {
            while (r.hasNext()){
                if(r.next() == XMLStreamConstants.START_ELEMENT){

                    if("dim".equalsIgnoreCase(r.getLocalName())){
                        dim = readDim(f,r);
                    }
                    p = f.generatePlanche(id,nbr,date,prix,dim.get(0),dim.get(1));
                    return p;
                }
                //r.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        p = f.generatePlanche(id,nbr,date,prix,dim.get(0),dim.get(1));
        return p;
    }

    ArrayList<IGenerable> readDim(IFactory f,XMLStreamReader r){
        double len = Double.parseDouble(r.getAttributeValue(0));
        double wid = Double.parseDouble(r.getAttributeValue(1));
        IGenerable c1 =  f.generateDim(len);
        IGenerable c2 = f.generateDim(wid);
        ArrayList<IGenerable> dim = new ArrayList<>();
        dim.add(c1);
        dim.add(c2);
        return dim;
    }


    XMLReader() {

    }
}