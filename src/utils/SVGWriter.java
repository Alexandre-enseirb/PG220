package utils;

import boards.IWritable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class SVGWriter implements IWriter {

    private String folder="out/SVG/";
    private FileOutputStream file;

    SVGWriter() {
        this.file=null;
    }

    public void openFile(String filename) {
        File tmp = new File(this.folder+filename);
        boolean exists = tmp.exists();
        if (exists){
            String ret="";
            Scanner sc = new Scanner(System.in);
            while (!ret.equalsIgnoreCase("y") && !ret.equalsIgnoreCase("n")) {
                System.out.print("[WARNING] File "+filename+" already exists. Do you want to overwrite it? (y/n) ");
                //ret = sc.next();
                ret="y";
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
            this.file = new FileOutputStream(this.folder+filename, false);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }
        System.out.println("[ IWriter ] File open successfully.");
        char quote='"';
        String out="<!DOCTYPE html>\n<html>\n<body>\n";

        byte[] outStream = out.getBytes();
        try {
            this.file.write(outStream);
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
        String out="<svg xmlns="+quote+"http://www.w3.org/2000/svg"+quote+" xmlns:xlink="+quote+"http://www.w3.org/1999/xlink"
                +quote+" width="+quote+"100%"+quote+" height="+quote+"100%"+quote+" viewBox="+quote+"0 0 100 100"
                +quote+">";
        out+=tab+"<rect width="+quote+"50"+quote+" height="+quote+"50"+quote+" fill="+quote+"white"+quote+" stroke="
                +quote+"black"+quote+" stroke-width="+quote+".5"+quote+"/>\n";
        out+="</svg>\n";

        byte[] outB = out.getBytes();
        try {
            this.file.write(outB);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    public void closeFile() {
        String close = "\n</body>\n</html>";
        byte[] outB = close.getBytes();
        try {
            this.file.write(outB);
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
