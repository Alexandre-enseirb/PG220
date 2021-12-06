package boards;

import utils.IReader;
import utils.IWriter;

import java.util.ArrayList;

public class Algo {
    public static void main(String[] args) {
        String filename1= "src/fichier_XML/fournisseurs.xml";
        String filename2= "src/fichier_XML/clients.xml";
        IReader reader =IReader.InstantiateXMLReader();
        IFactory cf = new ClientFactory();
        IFactory sf = new SupplierFactory();
        ArrayList<ArrayList<Object>> Users =  reader.read(filename2);

        ArrayList<Client> lesclients = ClientFactory.IGenerable2Clients(cf.generatePeople(Users));
        for(Client c : lesclients){
            System.out.println(c.id);
            System.out.println(c.boards.get(0).date);
            System.out.println(c.boards.get(0).length.getValue());
        }

        Users =  reader.read(filename1);
        ArrayList<Supplier> lesSuppliers = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));
        for(Supplier s : lesSuppliers){
            System.out.println(s.id);
            System.out.println(s.boards.get(0).date);
            System.out.println(s.boards.get(0).length.getValue());
        }

        cutTest(lesclients, lesSuppliers);
    }

    static void cutTest(ArrayList<Client> listC, ArrayList<Supplier> listS) {
        int id=0;
        String filename="export.XML";
        IWriter w = IWriter.instantiateXMLWriter(true);
        w.openFile(filename);
        for (Client c : listC) {
            for (Supplier s : listS) {
                Cut cut = new Cut(id++, c, s);
                // tostr modifié
                cut.algo_etape2(); // ce que j'ai rajouté
                w.writeDecoupes(cut);  //ce que j'ai rajouté
            }
        }
        w.closeFile();
    }
}

