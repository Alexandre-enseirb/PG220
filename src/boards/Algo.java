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
            System.out.println(c.boards.get(0).price.getValue());
        }

        Users =  reader.read(filename1);

        ArrayList<Supplier> listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));
        for(Supplier s : listS){
            System.out.println(s.id);
            System.out.println(s.boards.get(0).date);
            System.out.println(s.boards.get(0).length.getValue());

        }

        ArrayList<BoardData> lesboards = ClientBoard.ordonneBoard2(listC);
        ClientBoard.sort(lesboards);
        for(BoardData d:lesboards){
            System.out.println(d.getOwnerId()+" length is "+ d.getLength().getValue()+" "+d.getWidth().getValue()+"no: "+d.getNumber());
        }
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        ArrayList<BoardData> lespanneaux = SupplierBoard.ordonneBoard2(listS);
        SupplierBoard.sort(lespanneaux);
        for(BoardData d:lespanneaux){
            System.out.println(d.getOwnerId()+" length is "+ d.getLength().getValue()+" "+d.getWidth().getValue()+"no: "+d.getNumber());
        }
        //cutTest(listC, listS, path.toString());
       // etap3_opti(lesboards,lespanneaux,path.toString());
        etape3_1er(lesboards,lespanneaux,path.toString());
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

    static void etap3_opti(ArrayList<BoardData> ClientsDemand, ArrayList<BoardData> SupplierDemand, String path){
        String filenameXML=path + "/export.XML";
        String filenameSVG=path + "/export.SVG";
        Scanner sc = new Scanner(System.in);
        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgw = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgw.openFile(filenameSVG, sc);
        ArrayList<IWritable> cuts = new ArrayList<>();
        int flags = -1;
        for (BoardData sb:SupplierDemand){

            double Ls = sb.length.getValue();
            double Ws = sb.width.getValue();
            double x = 0.00;
            double y = 0.00;
            double premierL=0.00;
            System.out.println(ClientsDemand.get(ClientsDemand.size()-1).getLength().getValue());
                while (Ws  > ClientsDemand.get(ClientsDemand.size()-1).getLength().getValue() && flags != 0){
                    // comparer avec la length min
                    flags = 0;
                    for(BoardData cb:ClientsDemand){
                        flags = flags + cb.getAmount().getValue();
                        if(cb.getAmount().getValue() > 0){
                            if(cb.length.getValue() <= Ws && cb.width.getValue() <= Ls){
                                // taille parfait, on peut decoupe
                                if(x == 0.00){

                                    premierL = cb.getLength().getValue();
                                    System.out.println("note premier L "+premierL);
                                }
                                // decoupe
                                CutElement cute = new CutElement(cb.getOwnerId(), cb.getId(), cb.getNumber(),
                                        sb.getOwnerId(), sb.getId(), sb.getNumber(),
                                        cb.getLength().getValue(), cb.getWidth().getValue(),
                                        sb.getLength().getValue(), sb.getWidth().getValue(),
                                        x, y);
                                cuts.add(cute);

                                //

                                x = x+cb.width.getValue();
                                Ls = Ls - cb.width.getValue();
                                cb.setAmountValue(cb.getAmount().getValue()-1);
                                System.out.println("decoupe 1 in "+sb.getDate().toString()+"longuer " + cb.getLength().getValue()+"width "+cb.getWidth().getValue());
                            }
                        }else {
                            continue;
                        }

                    }

                    // quand une ligne est finie, on va commencer par nouvelle ligne
                    Ws = Ws - premierL;
                    y = y+premierL;
                    Ls = sb.getLength().getValue();
                    x = 0.00;
                    //System.out.println("y : "+y);
                }

            System.out.println("im in sb "+sb.id);


        }
        System.out.println("jj");
        w.writeToFile(cuts);
        svgw.writeToFile(cuts);
        w.closeFile();
        svgw.closeFile();
        sc.close();

    }
    static void etape3_1er(ArrayList<BoardData> ClientsDemand, ArrayList<BoardData> SupplierDemand, String path) {
        String filenameXML = path + "/export.XML";
        String filenameSVG = path + "/export.SVG";
        Scanner sc = new Scanner(System.in);
        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgw = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgw.openFile(filenameSVG, sc);
        ArrayList<IWritable> cuts = new ArrayList<>();
        int flags = -1;
        for (BoardData sb : SupplierDemand) {

            double Ls = sb.length.getValue();
            double Ws = sb.width.getValue();
            double x = 0.00;
            double y = 0.00;
            //double premierL = 0.00;
           // System.out.println(ClientsDemand.get(ClientsDemand.size() - 1).getLength().getValue());
            while (Ws > ClientsDemand.get(ClientsDemand.size() - 1).getWidth().getValue() && flags != 0 && Ls>ClientsDemand.get(ClientsDemand.size()-1).getWidth().getValue()) {
                // compare with the long and width min
                flags = 0;
                for (BoardData cb : ClientsDemand) {
                    flags = flags + cb.getAmount().getValue();
                    if (cb.getAmount().getValue() > 0) {
                        if (cb.width.getValue() <= Ws && cb.length.getValue() <= Ls) {
                            // taille parfait, on peut decoupe
                            // decoupe
                            CutElement cute = new CutElement(cb.getOwnerId(), cb.getId(), cb.getNumber(),
                                    sb.getOwnerId(), sb.getId(), sb.getNumber(),
                                    cb.getLength().getValue(), cb.getWidth().getValue(),
                                    sb.getLength().getValue(), sb.getWidth().getValue(),
                                    x, y);
                            cuts.add(cute);

                            //

                            x = x + cb.length.getValue();
                            y = y + cb.width.getValue();
                            Ls = Ls - cb.length.getValue();
                            Ws = Ws - cb.width.getValue();
                            cb.setAmountValue(cb.getAmount().getValue() - 1);
                            System.out.println("decoupe 1 in " + sb.getDate().toString() + "longuer " + cb.getLength().getValue() + "width " + cb.getWidth().getValue());
                        }
                    } else {
                        continue;
                    }
                    //System.out.println("y : "+y+ " x: "+x);
                }

                // quand une ligne es

            }

            System.out.println("im in sb " + sb.id);


        }
        System.out.println("jj");
        w.writeToFile(cuts);
        svgw.writeToFile(cuts);
        w.closeFile();
        svgw.closeFile();
        sc.close();
    }
}

//double premierw;
//            for(BoardData cb:ClientsDemand){ // 遍历array cb
//                Client c = listC.get(cb.ownerId);
//                if(cb.length.getValue() <= Ws && cb.width.getValue() <= Ls){
//                    // cb.length > sb.length or cb.width > sb.width;
//                    if(x == 0.00){
//                        premierL = cb.getLength().getValue();
//                    }
//                    Ls = Ls - cb.width.getValue();
//
//                    Cut cut = new Cut(id++,c,s,x,y);
//                    x = x+cb.width.getValue();
//
//                    cut.hasValidCuts();
//                    ArrayList<IWritable> export = cut.export();
//                    w.writeToFile(export);
//                    svgw.writeToFile(export);
//                }
////                else {
////                    enligne = true;
////                    Ls = Ls - cb.getLength().getValue();
////
////                }
//                else if(cb.width.getValue() > Ls){
//                    Ws = Ws - premierL;
//                    Ls = sb.getLength().getValue();
//                    x = 0.00;
//                    y = y + premierL;
//                    if(cb.length.getValue() <= Ws){
//                        Cut cut = new Cut(id++,c,s,x,y);
//                        x = x+cb.width.getValue();
//                        premierL = cb.getLength().getValue();
//
//                        cut.hasValidCuts();
//                        ArrayList<IWritable> export = cut.export();
//                        w.writeToFile(export);
//                        svgw.writeToFile(export);
//                    }else{
//                        continue;
//                    }
//                }
//
//
//            }