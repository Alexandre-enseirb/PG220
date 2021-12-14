package boards;

import utils.IReader;
import utils.IWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class of the package
 */
public class Algo {
    /**
     * Main method
     * @param args first arg has to be the folder where the .xml files are stored
     * @throws NoDirectoryException the folder given as an argument does not exist
     * @throws NotEnoughArgumentsException no argument was given
     */
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

    /**
     * demonstrates how the Cut class works
     * @param listC list of Clients
     * @param listS list of Suppliers
     * @param path path to the files
     */
    static void cutTest(ArrayList<Client> listC, ArrayList<Supplier> listS, String path) {
        int id=0;

        String filenameXML=path + "/export.XML";
        String filenameSVG=path + "/export.SVG";
        Scanner sc = new Scanner(System.in);
        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgw = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgw.openFile(filenameSVG, sc);
        for (Client c : listC) {
            for (Supplier s : listS) {
                Cut cut = new Cut(id++, c, s);
                cut.hasValidCuts();
                ArrayList<IWritable> export = cut.export();
                // tostr modifié
                w.writeToFile(export);
                svgw.writeToFile(export);
                //cut.algo_etape2(); // ce que j'ai rajouté
                //w.writeDecoupes(cut);  //ce que j'ai rajouté
            }
        }
        w.closeFile();
        svgw.closeFile();
        sc.close();
    }
}

