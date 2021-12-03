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
                +quote+" x="+quote+5+quote+" y="+quote+5+quote+" width="+quote+"100%"+quote+" height="+quote+"100%"
                +quote+" viewBox="+quote+"0 0 200 200"
                +quote+">";
        out+=fullRectangle(5,5,80,50);
        out+=fullRectangle(10,10,60,30, "yellow");
        out+=dashedRectangle(5,5,20,10);
        out+=dashedRectangle(25,70,20,10, "blue");
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

    String fullRectangle(int posX, int posY, int width, int height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";

        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +Integer.toString(width)+"\" height=\""+Integer.toString(height)+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }
    String fullRectangle(int posX, int posY, int width, int height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";

        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +Integer.toString(width)+"\" height=\""+Integer.toString(height)+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }
    String dashedRectangle(int posX, int posY, int width, int height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";

        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +Integer.toString(width)+"\" height=\""+Integer.toString(height)+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\" stroke-dasharray=\""+strokeDasharray+"\"/>";
    }

    String dashedRectangle(int posX, int posY, int width, int height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";

        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +Integer.toString(width)+"\" height=\""+Integer.toString(height)+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\" stroke-dasharray=\""+strokeDasharray+"\"/>";
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
