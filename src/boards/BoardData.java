package boards;

import java.util.ArrayList;
import utils.IReader;

abstract class BoardData {

    int id;
    Date date;
    Amount amount;
    Price price;
    Dimension length;
    Dimension width;

    private ArrayList<Validable> listV;




    boolean allValid(){
        for(Validable v : listV){
            if (!v.isValid())
                return false;
        }
        return true;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    Amount getAmount() {
        return amount;
    }

    void setAmount(Amount amount) {
        this.amount = amount;
    }

    void setAmountValue(int value){
        this.amount.setValue(value);
    }

    Price getPrice() {
        return price;
    }

    void setPrice(Price price) {
        this.price = price;
    }

    Dimension getLength() {
        return length;
    }

    void setLength(Dimension length) {
        this.length = length;
    }

    Dimension getWidth() {
        return width;
    }

    void setWidth(Dimension width) {
        this.width = width;
    }

    /**
     * reads all the fields needed to create a board
     * @param r
     * @param clientBoard
     * @return
     */
    static BoardData readBoard(IReader r, boolean clientBoard){
        ClientBoard board = new ClientBoard(); // will be returned as BoardData, but instantiated as ClientBoard
                                               // in order to set Validated and Shape
        board.id = r.readInt();
        board.amount=Amount.read();
        board.date=Date.readDate();
        board.length=Dimension.read();
        board.width = Dimension.read();
        if (clientBoard) {
            board.shape = Polygon.readPolygon(r);
            board.setValidated(false);
        }
        return (BoardData) board;
    }
}
