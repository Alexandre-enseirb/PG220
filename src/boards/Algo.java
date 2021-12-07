package boards;

import utils.IReader;
import utils.IWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Algo {
    public static void main(String[] args) throws NoDirectoryException, NotEnoughArgumentsException{
        String folder = "debug value. Please do not mind.";
        try {
            folder = args[0];
        }catch(ArrayIndexOutOfBoundsException e) {
            throw new NotEnoughArgumentsException("Please indicate the directory containing your XML files as an argument.");
        }
        Path path = Paths.get(folder);


        String filename1= path.toString() + "/fournisseurs.xml";
        String filename2= path.toString() + "/clients.xml";
        IReader reader =IReader.InstantiateXMLReader();
        IFactory cf = new ClientFactory();
        IFactory sf = new SupplierFactory();
        ArrayList<ArrayList<Object>> Users =  reader.read(filename2);

        ArrayList<Client> listC = ClientFactory.IGenerable2Clients(cf.generatePeople(Users));
        for(Client c : listC){
            System.out.println(c.id);
            System.out.println(c.boards.get(0).date);
            System.out.println(c.boards.get(0).length.getValue());
        }

        Users =  reader.read(filename1);
        ArrayList<Supplier> listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));
        for(Supplier s : listS){
            System.out.println(s.id);
            System.out.println(s.boards.get(0).date);
            System.out.println(s.boards.get(0).length.getValue());
        }

        cutTest(listC, listS, path.toString());
    }

    static void cutTest(ArrayList<Client> listC, ArrayList<Supplier> listS, String path) {
        int id=0;
        String filename=path + "/export.XML";
        IWriter w = IWriter.instantiateXMLWriter(false);
        w.openFile(filename);
        for (Client c : listC) {
            for (Supplier s : listS) {
                Cut cut = new Cut(id++, c, s);
                cut.hasValidCuts();
                w.writeToFile(cut);
            }
        }
        w.closeFile();
    }
}

