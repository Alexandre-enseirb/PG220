package boards;


import java.util.ArrayList;

/**
 * Class extending BoardData. Adds a validated boolean and a shape Polygon as the Client has to be able to request
 * any polygon for his cut.
 */

class ClientBoard extends BoardData {

    /**
     * true if a supplier has been found for this order
     */
    private boolean validated;
    /**
     * shape of the cut
     */
    Polygon shape;

    /**
     * Constructor
     */
    ClientBoard(){
        super(0,0,0,"00.00.00",0, 0, 0);
    }

    /**
     * Overloaded constructor
     * @param oid owner's id
     * @param id board id
     * @param amount amount of boards for the order
     * @param date delivery date
     * @param price price of the order
     * @param length length of the boards (if not polygon)
     * @param width width of the boards (if not polygon)
     */
     ClientBoard(int oid, int id, int amount, String date,double price,double length,double width) {
        super(oid, id,amount,date,price,length,width);
    }
    ClientBoard(int oid, int id, int amount, String date,double price,double length,double width,int number) {
        super(oid, id,amount,date,price,length,width,number);
    }


    /**
     * to see if a board is validated or not
     * @return true if the board is validated, false else
     */

    public boolean isValidated() {
        return validated;
    }

    /**
     * sets a board as validated or not
     * @param validated true or false
     */
    void setValidated(boolean validated) {
        this.validated = validated;
    }

    // take all the boards demand from arraylist of client
    static ArrayList<BoardData> ordonneBoard(ArrayList<Client> t){
        ArrayList<BoardData> boards = new ArrayList<BoardData>();
        for(Client c:t){
            for(BoardData board:c.boards){
                board.setUserid(c.getId());
                boards.add(board);
            }
        }
        return boards;
    }

    static ArrayList<BoardData> ordonneBoard2(ArrayList<Client> t){
        ArrayList<BoardData> boards = new ArrayList<BoardData>();
        for(Client c:t){
            for(BoardData board:c.boards){
                for(int i = 0;i<board.getAmount().getValue();i++){
                    BoardData b = new ClientBoard(c.id,board.id,1,board.date.toString(),board.price.getValue(),board.length.getValue(),board.width.getValue(),i);
                    boards.add(b);
                }
            }
        }
        return boards;
    }
}
