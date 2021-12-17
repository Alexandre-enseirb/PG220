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

    /**
     * opens given file. Scanner serves if the file already exists.
     * @param filename
     * @param sc
     */
    public void openFile(String filename, Scanner sc) {
        String[] array = filename.split("\\.");
        int add=-1; // not set for now
        filename=array[0];
        String extension="."+array[1];
        File tmp = new File(this.folder+filename+extension);
        boolean exists = tmp.exists();
        if (exists && !this.append){
            String ret="";
            //Scanner sc = new Scanner(System.in);
            while (!ret.equalsIgnoreCase("y") && !ret.equalsIgnoreCase("n")) {
                System.out.print("[WARNING] File "+filename+extension+" already exists. Do you want to overwrite it? (y/n) ");
                ret = sc.next();
            }

            if (ret.equalsIgnoreCase("n")) {
                add = 0; // set, we were prompted to erase the file
                /* ensures we create a file that does not exist yet */
                while (tmp.exists()) {
                    add++;
                    tmp = new File(this.folder + filename + Integer.toString(add) + extension);

                }
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
            System.out.println(this.folder+filename+extension);
            if (add==-1) {
                this.file = new FileOutputStream(this.folder + filename + extension, this.append);
            }else {
                this.file = new FileOutputStream(this.folder + filename +Integer.toString(add)+ extension, this.append);
            }

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

    /**
     * Calls for the .toStr() method of a list of IWritable to write them as an XML file
     * @param w List of objects to write
     */
    public void writeToFile(ArrayList<IWritable> w) {

        String tab="    ";
        char quote='"';
        char newline='\n';
        char startElem='<';
        String endElem=">"+newline;
        String out="";

        for (IWritable export : w) {

            ArrayList<String> data = export.toStr();
            if (data.size() == 0)
                return;

            out += tab + startElem + "Cut" + endElem;
            out += tab+tab+ startElem + data.get(0) + " "
                    +data.get(1)+"="+data.get(2)+" "
                    +data.get(3)+"="+data.get(4)+"."+data.get(5)+"/"+endElem;
            out += tab+tab+ startElem + data.get(8) + " "
                    +data.get(9)+"="+data.get(10)+" "
                    +data.get(11)+"="+data.get(12)+"."+data.get(13)+"/"+endElem;
            out += tab+tab+ startElem + data.get(16) + " "
                    +data.get(17)+"="+data.get(18)+" "
                    +data.get(19)+"="+data.get(20)+"/"+endElem;
            out+=tab+startElem+"/Cut"+endElem;
        }

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

    /**
     * finishes writing in the file and closes it
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



