package boards;

import java.util.ArrayList;

abstract class BoardData{

    int id;
    int ownerId;
    Date date;
    Amount amount;
    Price price;
    Dimension length;
    Dimension width;
    int currentid;  // pour sauvgarder l'id acutel de ce qu'il reste

    public int getCurrentid() {
        return currentid;
    }

    public void setCurrentid(int currentid) {
        this.currentid = currentid;
    }

    private ArrayList<Validable> listV;

    BoardData(int ownerId, int id, int amount, String date,double price, double length, double width) {
        this.ownerId=ownerId;
        this.id = id;
        String[] array = date.split("\\.");
        this.date = new Date();
        this.date.setDay(Integer.parseInt(array[0]));
        this.date.setMonth(Integer.parseInt(array[1]));
        this.date.setYear(Integer.parseInt(array[2]));
        this.amount= new Amount(amount);
        this.price = new Price(price);
        this.length = new Dimension(length);
        this.width =  new Dimension(width);
        this.currentid = 0;
        this.listV = new ArrayList<>();

        this.listV.add(this.date);
        this.listV.add(this.amount);
        this.listV.add(this.price);
        this.listV.add(this.length);
        this.listV.add(this.width);
    }



    boolean allValid(){
        for(Validable v : listV){
            if (!v.isValid())
                return false;
        }
        return true;
    }

    /*boolean validForBoard(BoardData b) throws InvalidBoardException {
        if (!this.allValid() || !b.allValid()){
            throw new InvalidBoardException();
        }
        if (this.getDate().)
    }*/

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

    void setOwnerId(int id) { this.ownerId = id; }

    int getOwnerId() { return this.ownerId; }


}
