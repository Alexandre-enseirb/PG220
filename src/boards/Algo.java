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
        Scanner sc = new Scanner(System.in);
        try {
            folder = args[0];
        }catch(ArrayIndexOutOfBoundsException e) {
            throw new NotEnoughArgumentsException("Please indicate the directory containing your XML files as an argument.");
        }
        Path path = Paths.get(folder);

        String folder_step_1 = "/etape 1";
        String folder_step_2 = "/etape 2";
        String folder_step_3 = "/etape 3";
        String folder_step_4 = "/etape 4";

        String clientFile = "/clients.xml";
        String supplierFile = "/suppliers.xml";
        /*
        String filename1= path.toString() + "/suppliers.xml";
        String filename2= path.toString() + "/clients.xml";
        */

        IReader reader =IReader.InstantiateXMLReader();
        IFactory cf = new ClientFactory();
        IFactory sf = new SupplierFactory();

        /* ====== TEST FOR STEP 1 ====== */
        System.out.println("");
        System.out.println("============================================================");
        System.out.println("=========================== PART 1 =========================");
        System.out.println("============================================================");
        System.out.println("");

        ArrayList<ArrayList<Object>> Users =  reader.read(path+folder_step_1+clientFile);

        ArrayList<Client> listC = ClientFactory.IGenerable2Clients(cf.generatePeople(Users));

        Users =  reader.read(path+folder_step_1+supplierFile);

        ArrayList<Supplier> listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));


        /* ====== TEST FOR STEP 2 ====== */
        System.out.println("");
        System.out.println("============================================================");
        System.out.println("=========================== PART 2 =========================");
        System.out.println("============================================================");
        System.out.println("");

        Users =  reader.read(path+folder_step_2+clientFile);

        listC = ClientFactory.IGenerable2Clients(cf.generatePeople(Users));

        Users =  reader.read(path+folder_step_2+supplierFile);

        listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));


        cutTest(listC, listS, path.toString()+folder_step_2,sc);

        /* ====== TEST PART 3 ====== */
        System.out.println("");
        System.out.println("============================================================");
        System.out.println("=========================== PART 3 =========================");
        System.out.println("============================================================");
        System.out.println("");
        Users =  reader.read(path+folder_step_3+clientFile);

        listC = ClientFactory.IGenerable2Clients(cf.generatePeople(Users));


        Users =  reader.read(path+folder_step_3+supplierFile);

        listS = SupplierFactory.IGenerable2Suppliers(sf.generatePeople(Users));

        ArrayList<BoardData> clientBoards = ClientBoard.ordonneBoard2(listC);
        ClientBoard.sort(clientBoards);

        ArrayList<BoardData> supplierBoards = SupplierBoard.ordonneBoard2(listS);
        SupplierBoard.sort(supplierBoards);
        System.out.println("[      ] First algorithm.");
        step3_unoptimized(clientBoards, supplierBoards, path.toString()+folder_step_3, sc);
        System.out.println("[  OK  ] First algorithm.");
        System.out.println("[      ] Improved algorithm. Please refuse overwrite if you want to see all the export files.");
        step3(clientBoards,supplierBoards,path.toString()+folder_step_3,sc);
        System.out.println("[  OK  ] Improved algorithm.");


        System.out.println("============================================================");
        System.out.println("====================== END OF PROCESS ======================");
        System.out.println("============================================================");

        System.out.println("");
        System.out.println("Please feel free to scroll up to see any message you may have missed.");
        System.out.println("Please type anything, and then ENTER to terminate once you are done.");
        sc.next();
        sc.close();

    }

    /**
     * demonstrates how the Cut class works
     * @param listC list of Clients
     * @param listS list of Suppliers
     * @param path path to the files
     */
    static void cutTest(ArrayList<Client> listC, ArrayList<Supplier> listS, String path, Scanner sc) {
        int id=0;

        String filenameXML=path + "/export.XML";
        String filenameSVG=path + "/export.SVG";

        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgwriter = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgwriter.openFile(filenameSVG, sc);
        for (Client c : listC) {
            for (Supplier s : listS) {
                Cut cut = new Cut(id++, c, s);
                cut.hasValidCuts();
                ArrayList<IWritable> export = cut.export();
                // modified toStr
                w.writeToFile(export);
                svgwriter.writeToFile(export);
                //cut.algo_etape2(); // ce que j'ai rajouté
                //w.writeDecoupes(cut);  //ce que j'ai rajouté
            }
        }
        w.closeFile();
        svgwriter.closeFile();
    }

    static void step3(ArrayList<BoardData> ClientsDemand, ArrayList<BoardData> SupplierDemand, String path, Scanner sc){

        /* opening and preparing the files for export */
        String filenameXML=path + "/export.XML"; // base name
        String filenameSVG=path + "/export.SVG"; // base name
        IWriter w = IWriter.instantiateXMLWriter(false);
        IWriter svgwriter = IWriter.instantiateSVGWriter();
        w.openFile(filenameXML, sc);
        svgwriter.openFile(filenameSVG, sc);
        ArrayList<IWritable> cuts = new ArrayList<>();

        int flags = -1;

        /* algorithm */
        for (BoardData sb:SupplierDemand){ // for each supplier board

            /* dimensions of the board */
            double Ls = sb.length.getValue();
            double Ws = sb.width.getValue();
            /* position on the board we are currently pointing at */
            double x = 0.00;
            double y = 0.00;

            double firstL=0.00;

                /* while at least the last board (the smallest) can be placed on the supplier board */
                while (Ws  > ClientsDemand.get(ClientsDemand.size()-1).getLength().getValue() && flags != 0){
                    // compare with min length
                    flags = 0;
                    for(BoardData cb:ClientsDemand){
                        flags = flags + cb.getAmount().getValue();
                        if(cb.getAmount().getValue() > 0){
                            if(cb.length.getValue() <= Ws && cb.width.getValue() <= Ls){
                                // correct length, can cut
                                if(x == 0.00){

                                    firstL = cb.getLength().getValue();

                                }
                                // cutting
                                CutElement cut = new CutElement(cb.getOwnerId(), cb.getId(), cb.getNumber(),
                                        sb.getOwnerId(), sb.getId(), sb.getNumber(),
                                        cb.getLength().getValue(), cb.getWidth().getValue(),
                                        sb.getLength().getValue(), sb.getWidth().getValue(),
                                        x, y);
                                cuts.add(cut);

                                //

                                x = x+cb.width.getValue();
                                Ls = Ls - cb.width.getValue();
                                cb.setAmountValue(cb.getAmount().getValue()-1);

                            }
                        }else {
                            continue;
                        }

                    }

                    // when End of Line, new line
                    Ws = Ws - firstL;
                    y = y+firstL;
                    Ls = sb.getLength().getValue();
                    x = 0.00;
                }



        }
        w.writeToFile(cuts);
        svgwriter.writeToFile(cuts);
        w.closeFile();
        svgwriter.closeFile();

    }
    static void step3_unoptimized(ArrayList<BoardData> ClientsDemand, ArrayList<BoardData> SupplierDemand, String path, Scanner sc) {
        String filenameXML = path + "/export.XML";
        String filenameSVG = path + "/export.SVG";

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
            while (Ws > ClientsDemand.get(ClientsDemand.size() - 1).getWidth().getValue() && flags != 0 && Ls>ClientsDemand.get(ClientsDemand.size()-1).getWidth().getValue()) {
                // compare with the long and width min
                flags = 0;
                for (BoardData cb : ClientsDemand) {
                    flags = flags + cb.getAmount().getValue();
                    if (cb.getAmount().getValue() > 0) {
                        if (cb.width.getValue() <= Ls && cb.length.getValue() <= Ws) {

                            CutElement cut = new CutElement(cb.getOwnerId(), cb.getId(), cb.getNumber(),
                                    sb.getOwnerId(), sb.getId(), sb.getNumber(),
                                    cb.getLength().getValue(), cb.getWidth().getValue(),
                                    sb.getLength().getValue(), sb.getWidth().getValue(),
                                    x, y);
                            cuts.add(cut);

                            //

                            x = x + cb.width.getValue();
                            y = y + cb.length.getValue();
                            Ls = Ls - cb.width.getValue();
                            Ws = Ws - cb.length.getValue();
                            cb.setAmountValue(cb.getAmount().getValue() - 1);

                        }
                    } else {
                        continue;
                    }

                }



            }




        }

        w.writeToFile(cuts);
        svgw.writeToFile(cuts);
        w.closeFile();
        svgw.closeFile();
    }
}
