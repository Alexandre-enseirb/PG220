package utils;

import boards.IWritable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.StrictMath.floor;

/**
 * Object istantiated via the IWriter interface, used to write the data contained in an IWritable object to an SVG file
 */
public class SVGWriter implements IWriter {

    private String folder="";
    private FileOutputStream file;
    private int exportCounter=0;

    /**
     * Basic constructor
     */
    SVGWriter() {
        this.file=null;
    }

    /**
     * Opens the file pointed at by filename. If the file already exists, asks if the user wants to overwrite it.
     * @param filename name of the file. Does not have to specify the folder, nor the extension
     * @param sc scanner used to get the user's prompts
     */
    public void openFile(String filename, Scanner sc) {
        String[] array = filename.split("\\.");
        filename=array[0];
        String extension="."+array[1];
        File tmp = new File(this.folder+filename+extension);
        boolean exists = tmp.exists();
        if (exists){
            String ret="";
            //Scanner sc = new Scanner(System.in);
            while (!ret.equalsIgnoreCase("y") && !ret.equalsIgnoreCase("n")) {
                System.out.print("[WARNING] File "+filename+extension+" already exists. Do you want to overwrite it? (y/n) ");
                ret = sc.next();

            }

            if (ret.equalsIgnoreCase("n")) {
                int add = 1;
                /* ensures we create a file that does not exist yet */
                while (tmp.exists()) {
                    tmp = new File(this.folder + filename + Integer.toString(add)+extension);
                    add++;
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
            this.file = new FileOutputStream(this.folder+filename+extension, false);
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

    /**
     * Calls for the .toStr() method of a list of IWritable to write them as an XML file
     * @param w List of objects to write
     */
    public void writeToFile(ArrayList<IWritable> w) {
        ArrayList<String> listS = null;
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
        int nbRow          = (int) floor(w.size() / 3) + 1; /* number of lines to prepare */
        double totalHeight = maxHeight(w) * nbRow + distRow * (nbRow-1) + originX;
        double totalLength = maxLength(w) * perRow + distCol * (perRow -1) + originY ;


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

        for (IWritable export : w) {
            listS = export.toStr();
            posX = originX + positionInRow * (int) (totalLength + distRow);
            posY = originY + positionInCol * (int) (totalHeight + distCol) + textFontSize;
            amount = listS.get(6);
            width = listS.get(7);
            height = listS.get(8);
            width2 = listS.get(15);
            height2 = listS.get(16);
            out += text("Cut no." + Integer.toString(++this.exportCounter) + " x" + amount, posX, posY - textFontSize / 2);
            out += fullRectangle(posX, posY, width2, height2);
            out += dashedRectangle(posX + offX, posY + offY, width, height);

            positionInRow = this.exportCounter % 3;
            positionInCol = (int) floor(this.exportCounter / 3);
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
     * @param w list of IWritables
     * @return maxval the maximum height of all boards
     */
    double maxHeight(ArrayList<IWritable> w) {
        double maxVal=0;
        ArrayList<String> listS = null;
        for (IWritable export : w) {
            listS = export.toStr();
            if (Double.parseDouble(listS.get(15)) > maxVal)
                maxVal=Double.parseDouble(listS.get(15));
        }
        return maxVal;
    }

    /**
     * parses all the given cuts to get the one with the maximum length
     * @param w list of IWritables
     * @return maxVal, the maximum length of all boards
     */
    double maxLength(ArrayList<IWritable> w) {
        double maxVal=0;
        ArrayList<String> listS = null;
        for (IWritable export : w) {
            listS = export.toStr();
            if (Double.parseDouble(listS.get(14)) > maxVal)
                maxVal=Double.parseDouble(listS.get(14));
        }
        return maxVal;
    }

    /**
     * creates text element for the .svg file, at the given position
     * @param textVal String to write in the file
     * @param posX position along the x axis
     * @param posY position along the y axis
     * @return String describing the text element
     */
    String text(String textVal, int posX, int posY) {
        String fill="black";
        return "    <text x=\""+posX+"\" y=\""+posY+"\" fill=\""+fill+"\">"+textVal+"</text>";
    }

    /**
     * creates a rectangle with full lines
     * @param posX position to create the rectangle at along the x axis
     * @param posY position to create the rectangle at along the y axis
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @return String describing the rectangle
     */
    String fullRectangle(int posX, int posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }

    /**
     * creates a rectangle with full lines with a color for the lines
     * @param posX position to create the rectangle at along the x axis
     * @param posY position to create the rectangle at along the y axis
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param fill color to use for the lines
     * @return String describing the rectangle
     */
    String fullRectangle(int posX, int posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\"/>";
    }

    /**
     * creates a rectangle with dashed lines
     * @param posX position to create the rectangle at along the x axis
     * @param posY position to create the rectangle at along the y axis
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @return String describing the rectangle
     */
    String dashedRectangle(int posX, int posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\" stroke-dasharray=\""+strokeDasharray+"\"/>";
    }

    /**
     * creates a rectangle with dashed lines
     * @param posX position to create the rectangle at along the x axis
     * @param posY position to create the rectangle at along the y axis
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param fill color to use for the lines
     * @return String describing the rectangle
     */
    String dashedRectangle(int posX, int posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Integer.toString(posX)+"\" y=\""+Integer.toString(posY)+"\" width=\""
                +width+"\" height=\""+height+"\" fill =\""+fill+"\" stroke=\""
                +strokeColor+"\" stroke-width=\""+strokeWidth+"\" stroke-dasharray=\""+strokeDasharray+"\"/>";
    }

    /**
     * Closes the file
     */
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
