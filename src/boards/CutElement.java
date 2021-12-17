package boards;

import java.util.ArrayList;

/**
 * Class facilitating the export of cuts
 */
class CutElement implements IWritable {

    private int clientId;
    private int cBoardId;
    private int cBoardNo;
    private int supplierId;
    private int sBoardId;
    private int sBoardNo;
    private double cLength;
    private double cWidth;
    private double sLength;
    private double sWidth;
    private double posX;
    private double posY;

    CutElement(int cId, int cBId, int cBNo, int sId, int sBId, int sBNo,
               double cLength, double cWidth, double sLength, double sWidth,
               double posX, double posY) {
        this.clientId   = cId;
        this.cBoardId   = cBId;
        this.cBoardNo   = cBNo;
        this.supplierId = sId;
        this.sBoardId   = sBId;
        this.sBoardNo   = sBNo;
        this.cLength    = cLength;
        this.cWidth     = cWidth;
        this.sLength    = sLength;
        this.sWidth     = sWidth;
        this.posX       = posX;
        this.posY       = posY;
    }


    @Override
    public ArrayList<String> toStr() {
        ArrayList<String> listS = new ArrayList<>();

        listS.add("client");
        listS.add("id");                              // data(1)
        listS.add(Integer.toString(this.clientId));   // data(2)
        listS.add("board");                           // data(3)
        listS.add(Integer.toString(this.cBoardId));   // data(4)
        listS.add(Integer.toString(this.cBoardNo));   // data(5)
        listS.add(Double.toString(this.cLength));     // data(6)
        listS.add(Double.toString(this.cWidth));      // data(7)
        listS.add("supplier");                        // data(8)
        listS.add("id");                              // data(9)
        listS.add(Integer.toString(this.supplierId)); // data(10)
        listS.add("board");                           // data(11)
        listS.add(Integer.toString(this.sBoardId));   // data(12)
        listS.add(Integer.toString(this.sBoardNo));   // data(13)
        listS.add(Double.toString(this.sLength));     // data(14)
        listS.add(Double.toString(this.sWidth));      // data(15)
        listS.add("position");                        // data(16)
        listS.add("x");                               // data(17)
        listS.add(Double.toString(this.posX));        // data(18)
        listS.add("y");                               // data(19)
        listS.add(Double.toString(this.posY));        // data(20)

        return listS;
    }
}
