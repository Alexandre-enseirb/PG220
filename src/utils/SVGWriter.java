package utils;

import boards.IWritable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.StrictMath.floor;

public class SVGWriter implements IWriter {

    private String folder="";
    private FileOutputStream file;
    private int exportCounter=0;

    SVGWriter() {
        this.file=null;
    }

    public void openFile(String filename, Scanner sc) {
        File tmp = new File(this.folder+filename);
        boolean exists = tmp.exists();
        if (exists){
            String ret="";
            //Scanner sc = new Scanner(System.in);
            while (!ret.equalsIgnoreCase("y") && !ret.equalsIgnoreCase("n")) {
                System.out.print("[WARNING] File "+filename+" already exists. Do you want to overwrite it? (y/n) ");
                ret = sc.next();

            }

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
        ArrayList<ArrayList<String>> data = w.toStr();
        String tab="    ";
        char quote='"';
        char newline='\n';



        /* "beautifying" data */
        int textFontSize   = 16;
        int originX        = 10;  /* starts drawing at the origin in x */
        int originY        = 10;  /* same in y */
        int distRow        = 10; /* 10 px between each cut row */
        int distCol        = 10; /* 10 px between each cut column */
        int perRow         = 3;  /* number of cuts to display in each line */
        int nbRow          = (int) floor(data.size() / 3) + 1; /* number of lines to prepare */
        double totalHeight = maxHeight(data) * nbRow + distRow * (nbRow-1) + originX;
        double totalLength = maxLength(data) * perRow + distCol * (perRow -1) + originY ;


        // creating the canvas
        
        String out="<svg xmlns="+quote+"http://www.w3.org/2000/svg"+quote+" xmlns:xlink="+quote+"http://www.w3.org/1999/xlink"
                +quote+" x="+quote+0+quote+" y="+quote+0+quote+" width=\"100%\" height=\""
                +Double.toString(totalHeight+textFontSize)+"\">";


        /* position of first board */
        int posX = originX;
        int posY = originY;

        // size of the client board and supplier board
        String width = "0";
        String height = "0";
        String width2 = "0";
        String height2 = "0";

        // for when we will need an offset
        int offX = 0;
        int offY = 0;

        // number of times the cut needs to be performed
        String amount = "0";

        // id of the cut


        int positionInRow = 0;
        int positionInCol = 0;

        for (ArrayList<String> listS : data) {
            posX = originX + positionInRow * (int) (totalLength + distRow);
            posY = originY + positionInCol * (int) (totalHeight + distCol) + textFontSize;
            amount = listS.get(6);
            width = listS.get(7);
            height = listS.get(8);
            width2 = listS.get(15);
            height2 = listS.get(16);
            out += text("Cut no."+Integer.toString(++this.exportCounter)+" x"+amount, posX, posY-textFontSize/2);
            out += fullRectangle(posX, posY, width2, height2);
            out += dashedRectangle(posX+offX, posY+offY, width, height);

            positionInRow = this.exportCounter%3;
            positionInCol = (int) floor(this.exportCounter/3);
        }
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
    /*
    @Override
    public void writeDecoupes(IWritable w) {

    }*/

    /**
     * parses all the given cuts to get the one with the maximum height
     * @param listListS
     * @return
     */
    double maxHeight(ArrayList<ArrayList<String>> listListS) {
        double maxVal=0;
        for (ArrayList<String> listS: listListS) {
            if (Double.parseDouble(listS.get(16)) > maxVal)
                maxVal=Double.parseDouble(listS.get(16));
        }
        return maxVal;
    }

    /**
     * parses all the given cuts to get the one with the maximum height
     * @param listListS
     * @return
     */
    double maxLength(ArrayList<ArrayList<String>> listListS) {
        double maxVal=0;
        for (ArrayList<String> listS: listListS) {
            if (Double.parseDouble(listS.get(15)) > maxVal)
                maxVal=Double.parseDouble(listS.get(15));
        }
        return maxVal;
    }

    /**
     * creates text element for the .svg file, at the given position
     * @param textVal
     * @param posX
     * @param posY
     * @return
     */
    String text(String textVal, int posX, int posY) {
        String fill="black";
        return "    <text x=\""+posX+"\" y=\""+posY+"\" fill=\""+fill+"\">"+textVal+"</text>";
    }

    String fullRectangle(int posX, int posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }
    String fullRectangle(int posX, int posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }
    String dashedRectangle(int posX, int posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\" stroke-dasharray=\""+strokeDasharray+"\"/>";
    }

    String dashedRectangle(int posX, int posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
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
