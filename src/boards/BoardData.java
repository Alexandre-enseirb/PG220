package boards;

import java.util.ArrayList;
import utils.IReader;

abstract class BoardData implements IGenerable{

    int id;
    Date date;
    Amount amount;
    Price price;
    Dimension length;
    Dimension width;

    private ArrayList<Validable> listV;

    public BoardData(int id, int amount, String date,double price, IGenerable length, IGenerable width) {
        this.id = id;
        String[] array = date.split("\\.");
        this.date = new Date();
        this.date.setDay(Integer.parseInt(array[0]));
        this.date.setMonth(Integer.parseInt(array[1]));
        this.date.setYear(Integer.parseInt(array[2]));
        this.amount= new Amount(amount);
        this.price = new Price(price);
        this.length = (Dimension) length;
        this.width = (Dimension) width;
    }

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
        return (Dimension) length;
    }

    void setLength(Dimension length) {
        this.length = length;
    }

    Dimension getWidth() {
        return (Dimension) width;
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

}
