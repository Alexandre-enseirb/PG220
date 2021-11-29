package utils;

import boards.IWritable;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XMLWriter implements IWriter {

    private boolean append;
    private FileOutputStream file;
    private String folder="out/xml/";
    XMLWriter(boolean append){
        this.file=null;
        this.append=append;
    }

    public void openFile(String filename) {
        File tmp = new File(this.folder+filename);
        try {
            tmp.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try {
            this.file = new FileOutputStream(this.folder+filename, this.append);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }
        System.out.println("[ IWriter ] File open successfully.");
        String open="<Cuts>\n";
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
        ArrayList<String> data = w.toStr();
        String tab="    ";
        char quote='"';
        char newline='\n';
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
                    +", amount="+quote+data.get(ptr++)+quote
                    +", date="+quote+data.get(ptr++)+quote
                    +", price="+quote+data.get(ptr++)+quote+">"+newline;
            out+=tab+tab+tab+"<Dim l="+quote+data.get(ptr++)+quote+", L="+quote+data.get(ptr++)+quote+"/>"+newline;
            out+=tab+tab+"</Board>"+newline;
        }
        out+=tab+"</Cut>"+newline;

        byte[] strToBytes = out.getBytes();
        try {
            this.file.write(strToBytes);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    public void closeFile() {
        String close ="</Cuts>\n";
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
