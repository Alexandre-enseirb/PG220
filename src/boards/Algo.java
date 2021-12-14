package boards;

import utils.IReader;
import utils.IWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println(c.boards.get(0).price.getValue());
        }

        Users =  reader.read(filename1);

        ArrayList<Supplier> listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));
        for(Supplier s : listS){
            System.out.println(s.id);
            System.out.println(s.boards.get(0).date);
            System.out.println(s.boards.get(0).length.getValue());

        }

        ArrayList<BoardData> lesboards = ClientBoard.ordonneBoard(listC);
        ClientBoard.sort(lesboards);
        for(BoardData d:lesboards){
            System.out.println(d.getOwnerId()+" length is "+ d.getLength().getValue()+" "+d.getWidth().getValue());
        }
        cutTest(listC, listS, path.toString());
    }

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
                // tostr modifié
                w.writeToFile(cut);
                svgw.writeToFile(cut);
                //cut.algo_etape2(); // ce que j'ai rajouté
                //w.writeDecoupes(cut);  //ce que j'ai rajouté
            }
        }
        w.closeFile();
        svgw.closeFile();
        sc.close();
    }

    static void etap3_opti(ArrayList<BoardData> ClientsDemand, ArrayList<BoardData> SupplierDemand,ArrayList<Client> listC, ArrayList<Supplier> listS, String path){
        int id=0;
        boolean enligne = false;
        String filenameXML=path + "/export.XML";
        String filenameSVG=path + "/export.SVG";
        Scanner sc = new Scanner(System.in);
        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgw = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgw.openFile(filenameSVG, sc);

        for (BoardData sb:SupplierDemand){
            Supplier s = listS.get(sb.ownerId);
            double Ls = sb.length.getValue();
            double Ws = sb.width.getValue();

            for(BoardData cb:ClientsDemand){ // 遍历array cb
                Client c = listC.get(cb.ownerId);
                if(cb.getLength().getValue() > Ls && cb.getWidth().getValue() > Ws){
                    // cb.length > sb.length or cb.width > sb.width;
                    continue;
                }
//                else {
//                    enligne = true;
//                    Ls = Ls - cb.getLength().getValue();
//
//                }
                else if(Ls >= cb.getLength().getValue()){
                    enligne = true;
                }
                else if(Ls < cb.getLength().getValue()){
                    enligne = false;
                    if(Ws > cb.getWidth().getValue()){

                    }
                }

            }
        }





    }
}

