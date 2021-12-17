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
 * Object instantiated via the IWriter interface, used to write the data contained in an IWritable object to an SVG file
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
        int add=-1;
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
                add = 0;
                /* ensures we create a file that does not exist yet */
                while (tmp.exists()) {
                    add++;
                    tmp = new File(this.folder + filename + Integer.toString(add)+extension);

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
            if (add==-1){
                this.file = new FileOutputStream(this.folder+filename+extension, false);
            }else{
                this.file = new FileOutputStream(this.folder+filename+Integer.toString(add)+extension, false);
            }

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
        double originX        = 10.0;  /* starts drawing at the origin in x */
        double originY        = 10.0;  /* same in y */
        int distRow        = 10; /* 10 px between each cut row */
        double distCol     = 10.0;
        int perRow         = 1;  /* number of cuts to display in each line */
        int nbRow          = (int) floor(w.size() / perRow) + 1; /* number of lines to prepare */
        double maxH = maxHeight(w);
        double maxL = maxLength(w);

        double totalHeight = maxHeight(w) * nbRow + distRow * (nbRow-1) + originX;
        double totalLength = maxLength(w) * perRow + distCol * (perRow -1) + originY ;


        // creating the canvas

        String out="<svg xmlns="+quote+"http://www.w3.org/2000/svg"+quote+" xmlns:xlink="+quote+"http://www.w3.org/1999/xlink"
                +quote+" x="+quote+0+quote+" y="+quote+0+quote+" width=\""+Double.toString(totalLength)+"\" height=\""
                +Double.toString(totalHeight+textFontSize)+"\">";


        /* position of first board */
        Double posX = originX;
        Double posY = originY+textFontSize;

        // size of the client board and supplier board
        String width = "0";
        String height = "0";
        String width2 = "0";
        String height2 = "0";
        String cutPosX = "0";
        String cutPosY = "0";

        // for when we will need an offset
        int offX = 0;
        int offY = 0;

        // number of times the cut needs to be performed
        String amount = "0";

        // id of the cut


        int positionInRow = 0;
        int positionInCol = 0;

        String uid = "not an id";
        String prevuid="still not an id";

        for (IWritable export : w) {
            listS = export.toStr();
            amount = "1";
            width = listS.get(7);
            height = listS.get(6);
            width2 = listS.get(15);
            height2 = listS.get(14);
            posX = originX;
            uid = listS.get(10)+":"+listS.get(12);
            if (uid.equals(prevuid)){
                posY -= (Double.parseDouble(height2) + distCol + textFontSize);
            }else{
                out += text("Cut no." + Integer.toString(++this.exportCounter) + " x" + amount, posX, posY - textFontSize / 2);
                out += fullRectangle(posX, posY, width2, height2);
            }

            cutPosX = listS.get(18);
            cutPosY = listS.get(20);

            out += dashedRectangle(posX + Double.parseDouble(cutPosX), posY + Double.parseDouble(cutPosY), width, height);
            posY += Double.parseDouble(height2) + distCol + textFontSize;
            prevuid = uid;
            positionInCol++;
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
    String text(String textVal, double posX, double posY) {
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
    String fullRectangle(double posX, double posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Double.toString(posX)+"\" y=\""+Double.toString(posY)+"\" width=\""
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
    String fullRectangle(double posX, double posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";


        return "    <rect x=\""+Double.toString(posX)+"\" y=\""+Double.toString(posY)+"\" width=\""
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
    String dashedRectangle(double posX, double posY, String width, String height) {
        String fill = "white";
        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Double.toString(posX)+"\" y=\""+Double.toString(posY)+"\" width=\""
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
    String dashedRectangle(double posX, double posY, String width, String height, String fill) {

        String strokeColor = "black";
        String strokeWidth = ".5";
        String strokeDasharray = "2,2";


        return "    <rect x=\""+Double.toString(posX)+"\" y=\""+Double.toString(posY)+"\" width=\""
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
