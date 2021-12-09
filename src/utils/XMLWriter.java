package utils;

import boards.IWritable;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class XMLWriter implements IWriter {

    private boolean append;
    private FileOutputStream file;
    private String folder="";
    XMLWriter(boolean append){
        this.file=null;
        this.append=append;
    }

    public void openFile(String filename) {
        File tmp = new File(this.folder+filename);
        boolean exists = tmp.exists();
        if (exists && !this.append){
            String ret="";
            Scanner sc = new Scanner(System.in);
            while (!ret.equalsIgnoreCase("y") && !ret.equalsIgnoreCase("n")) {
                System.out.print("[WARNING] File "+filename+" already exists. Do you want to overwrite it? (y/n) ");
                ret = sc.next();
            }
            sc.close();
            if (ret.equalsIgnoreCase("n")) {
                System.out.println("Aborting.");
                return;
            }

        }
        try {
            tmp.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try {
            System.out.println(this.folder+filename);
            this.file = new FileOutputStream(this.folder+filename, this.append);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }
        System.out.println("[ IWriter ] File open successfully.");
        String open="<cuts>\n";
        byte[] strToBytes = open.getBytes();
        try {
            this.file.write(strToBytes);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    public void writeToFile(IWritable w) {
        ArrayList<ArrayList<String>> data = w.toStr();
        String tab="    ";
        char quote='"';
        char newline='\n';
        char startElem='<';
        String endElem=">"+newline;
        String out="";
        for (ArrayList<String> listS : data) {
            out += tab + startElem + listS.get(0) + endElem;
            out += tab+tab+ startElem + listS.get(1) + " "
                    +listS.get(2)+"="+listS.get(3)+" "
                    +listS.get(4)+"="+listS.get(5)+"."+listS.get(6)+"/"+endElem;
            out += tab+tab+ startElem + listS.get(7) + " "
                    +listS.get(8)+"="+listS.get(9)+" "
                    +listS.get(10)+"="+listS.get(11)+"."+listS.get(12)+"/"+endElem;
            out += tab+tab+ startElem + listS.get(13) + " "
                    +listS.get(14)+"="+listS.get(15)+" "
                    +listS.get(16)+"="+listS.get(17)+"/"+endElem;
            out+=tab+startElem+"/"+listS.get(0)+endElem;
        }
        /*
        int boardsAmount;
        int ptr=3;
        // begin XLM file
        String out;
        // create Cut child
        out =tab+"<Cut id="+quote+data.get(0)+quote+">"+newline;
        // create Client subchild
        out +=tab+tab+"<Client id="+quote+data.get(1)+quote+"/>"+newline;
        // create Supplier subchild
        out +=tab+tab+"<Supplier id="+quote+data.get(2)+quote+"/>"+newline;
        // create Boards subchilds
        boardsAmount = data.size();

        while(ptr<boardsAmount) {
            out+=tab+tab+"<Board id="+quote+data.get(ptr++)+quote
                    +" amount="+quote+data.get(ptr++)+quote
                    +" date="+quote+data.get(ptr++)+quote
                    +" price="+quote+data.get(ptr++)+quote+">"+newline;
            out+=tab+tab+tab+"<Dim l="+quote+data.get(ptr++)+quote+" L="+quote+data.get(ptr++)+quote+"/>"+newline;
            out+=tab+tab+"</Board>"+newline;
        }
        out+=tab+"</Cut>"+newline;
        */
        byte[] strToBytes = out.getBytes();
        try {
            this.file.write(strToBytes);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }


    }
    /*
    public void writeDecoupes(IWritable w) {
        ArrayList<String> data = w.toStr();
        String tab="    ";
        char quote='"';
        char newline='\n';
        int boardsAmount;
        int ptr=3;
        // begin XLM file
        String out = new String();
       // String decoupeid = data.get(0);
        String clientid = data.get(1);
        String supplierid = data.get(2);

        boardsAmount = data.size();

        while(ptr<boardsAmount){
            int clientBoardId = Integer.parseInt(data.get(ptr++));
            int supplierBoardId = Integer.parseInt(data.get(ptr++));
            int nbrBoard = Integer.parseInt(data.get(ptr++));
            int currentIdCb = Integer.parseInt(data.get(ptr++));
            int currentIdSb = Integer.parseInt(data.get(ptr++));

            for(int i = 0;i<nbrBoard;i++){
                out =tab+"<Cut"+">"+newline;
                out +=tab+tab+"<client id="+quote+clientid+quote+" planche="+quote+clientBoardId+"."+(i+currentIdCb)+quote+" />"+newline;
                out +=tab+tab+"<fournisseur id="+quote+supplierid+quote+" panneau="+quote+supplierBoardId+"."+(i+currentIdSb)+quote+" />"+newline;
                out +=tab+tab+"<position x="+quote+"0.00"+quote+" y="+quote+"0.00"+quote+" />"+newline;
                out += tab+"</Cut>"+newline;
                byte[] strToBytes = out.getBytes();

                try {
                    this.file.write(strToBytes);
                }
                catch (IOException e){
                    e.printStackTrace();
                    return;
                }
            }

        }

    }
*/
    public void closeFile() {
        String close ="</cuts>\n";
        byte[] strToBytes = close.getBytes();
        try {
            this.file.write(strToBytes);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        try {
            this.file.close();
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }
    }
}



