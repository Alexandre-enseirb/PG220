package boards;

import java.util.ArrayList;

/**
 * abstract class representing any possible board
 */
abstract class BoardData{

    /**
     * board's id
     */
    int id;
    /**
     * board's owner id (either a Client or a Supplier)
     */
    int ownerId;
    /**
     * date of delivery for the board
     */
    Date date;
    /**
     * amount of board provided or requested
     */
    Amount amount;
    /**
     * price of the command
     */
    Price price;
    /**
     * length of the board
     */
    Dimension length;
    /**
     * width of the board
     */
    Dimension width;

    int currentid;  // pour sauvgarder l'id acutel de ce qu'il reste
    int userid;


    int number;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * list to store every parameter than implements Validable
     */
    private ArrayList<Validable> listV;

    /**
     * Constructor
     * @param ownerId id of the owner actor
     * @param id id of the board
     * @param amount amount of boards in the order
     * @param date date of delivery
     * @param price price of the order
     * @param length length of the boards
     * @param width width of the boards
     */
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
        this.userid = -1;
        this.number = 0;
        this.listV = new ArrayList<>();

        this.listV.add(this.date);
        this.listV.add(this.amount);
        this.listV.add(this.price);
        this.listV.add(this.length);
        this.listV.add(this.width);
    }
    BoardData(int ownerId, int id, int amount, String date,double price, double length, double width, int number) {
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
        this.userid = -1;
        this.number = number;
        this.listV = new ArrayList<>();

        this.listV.add(this.date);
        this.listV.add(this.amount);
        this.listV.add(this.price);
        this.listV.add(this.length);
        this.listV.add(this.width);
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }




    /**
     * checks if every Validable is valid
     * @return true if all are valid, false else
     */

    boolean allValid(){
        for(Validable v : listV){
            if (!v.isValid()){
                System.out.println(v);
                return false;

            }

        }
        return true;
    }

    /*boolean validForBoard(BoardData b) throws InvalidBoardException {
        if (!this.allValid() || !b.allValid()){
            throw new InvalidBoardException();
        }
        if (this.getDate().)
    }*/

    /**
     * gets the id
     * @return id got
     */
    int getId() {
        return id;
    }

    /**
     * sets the id
     * @param id id to set
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * gets the delivery date
     * @return date of delivery
     */
    Date getDate() {
        return date;
    }

    /**
     * sets delivery date
     * @param date date to set
     */
    void setDate(Date date) {
        this.date = date;
    }

    /**
     * gets the amount of boards to provide
     * @return amount of boards
     */
    Amount getAmount() {
        return amount;
    }

    /**
     * sets the amount of boards
     * @param amount amount of boards
     */
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


    static void sort(ArrayList<BoardData> t) {
        for (int i = 1; i < t.size(); i++) {
            for (int j = 0; j < t.size() - 1; j++) {
                if (t.get(j).length.getValue() < t.get(j + 1).length.getValue()) { // 比length
                    BoardData temp = t.get(j);
                    // ClientBoard temp2 = t.get(j+1);
                    t.set(j, t.get(j + 1));
                    t.set(j + 1, temp);
                } else if (t.get(j).length.getValue() == t.get(j + 1).length.getValue()) {
                    if (t.get(j).width.getValue() < t.get(j + 1).width.getValue()) {
                        BoardData temp = t.get(j);
                        // ClientBoard temp2 = t.get(j+1);
                        t.set(j, t.get(j + 1));
                        t.set(j + 1, temp);
                    }
                }


            }
        }
    }
    /**
     * removes number unit of a board
     * @param number number of units of boards to remove
     * @throws NoMoreBoardsException if the number of boards gets to 0 or below
     */
    public void remove (int number) throws NoMoreBoardsException {
        this.amount.setValue(this.amount.getValue()-1);
        if (this.amount.getValue()<=0) {
            throw new NoMoreBoardsException();

        }
    }
}
