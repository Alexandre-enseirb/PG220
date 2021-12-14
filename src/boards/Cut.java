package boards;

import java.util.ArrayList;

/**
 * Contains the main algorithms to select and optimize the choice of suppliers for each clients
 * Can be exported to XML or SVG files via the CutElement class
 */
class Cut {

    /**
     * Client to serve
     */
    private Client client;
    /**
     * Supplier to test
     */
    private Supplier supplier;
    /* the following lists are used to store the cuts for later */
    /**
     * adds a client board to be removed after the iterator has finished
     */
    private ArrayList<BoardData> clientBoardsToRemove;
    /**
     * adds a supplier board to be removed after the iterator has finished
     */
    private ArrayList<BoardData> supplierBoardsToRemove;
    /**
     * How much of each Supplier's offer has to be bought
     */
    private ArrayList<Integer>   quantities;

    /* unused value, might delete later */
    /**
     * Cut id, unused as of now
     */
    private int id;
    /**
     * Every valid cut, ready to be exported
     */
    private ArrayList<IWritable> cuts;
    //private ArrayList<String> algo2;

    /**
     * Constructor
     */
    Cut(){
        this.id=0;
        this.client = new Client();
        this.supplier = new Supplier();
        this.clientBoardsToRemove=new ArrayList<>();
        //this.algo2 = new ArrayList<>();
    }

    /**
     *
     * @param id cut id
     * @param client Client to serve
     * @param supplier Supplier to test
     */
    Cut(int id, Client client, Supplier supplier){
        this.id=id;
        this.client=client;
        this.supplier=supplier;
        this.clientBoardsToRemove=new ArrayList<>();
        this.supplierBoardsToRemove = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.cuts = new ArrayList<>();
    }
/*        this.algo2 = new ArrayList<>();
        this.algo2.add(Integer.toString(id));
        this.algo2.add(Integer.toString(client.id));
        this.algo2.add(Integer.toString(supplier.id));
*/


    // supplier的price比较有误 (已修改） ？
    // break  已修改

    /**
     * Tests each unfulfilled order of the client and each offer of the supplier, stores the matches and buys the
     * corresponding amount of boards to the supplier
     */
    void hasValidCuts(){
        CutElement cutElem;
        ArrayList<BoardData> supplierBoards =  this.supplier.getBoards();
        ArrayList<BoardData> clientBoards   =  this.client.getBoards();
        String tab="    ";
        for (BoardData cb: clientBoards){
            // looking for a pending board

            for (BoardData sb : supplierBoards) {
                // comparing to a supplier board

                // DATE
                if (!cb.getDate().comesAfter(sb.getDate())) {
                    //System.out.println(tab+cb.id+"Delays not working.");
                    System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Delays not working");
                    continue; // won't be done in the given delays
                }

                // PRICE
                if (cb.getPrice().getValue() < sb.getPrice().getValue()) {
                    System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " too expensive");
                    continue; // too expensive for the client
                }

                // AMOUNT
                if (cb.getAmount().getValue() > sb.getAmount().getValue()) {
                    System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Not enough board to buy");
                    continue; // supplier can't supply enough
                }

                // DIMENSIONS
                if (    (cb.getLength().getValue() > sb.getLength().getValue() ||
                        cb.getWidth().getValue() > sb.getWidth().getValue() )  &&
                        (cb.getWidth().getValue() > sb.getLength().getValue()  ||
                        cb.getLength().getValue() > sb.getWidth().getValue())  )
                {
                    System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Board too small.");
                    continue; // does not fit in length
                }


                // if we get here, everything is okay.

                // we create as many as possible
                int clientPtr;
                int supplierPtr;

                while(true) {
                    clientPtr = cb.getNumber();
                    supplierPtr = sb.getNumber();
                    cutElem = new CutElement(cb.getOwnerId(), cb.getId(), clientPtr,
                            sb.getOwnerId(), sb.getId(), supplierPtr,
                            cb.getLength().getValue(), cb.getWidth().getValue(),
                            sb.getLength().getValue(), sb.getWidth().getValue(),
                            0, 0);
                    System.out.println("Valid cut");
                    this.cuts.add(cutElem);
                    cb.setNumber(clientPtr + 1);
                    sb.setNumber(clientPtr + 1);
                    try {
                        cb.remove(1);
                    } catch (NoMoreBoardsException e) {
                        this.clientBoardsToRemove.add(cb);
                        break;
                    }
                    try {
                        sb.remove(1);
                    } catch (NoMoreBoardsException e) {
                        this.supplierBoardsToRemove.add(sb);
                        break;
                    }
                }
            }
        }
        boardsCleanup();
    }

    void boardsCleanup() {
        for (BoardData cb : this.clientBoardsToRemove) {
            this.client.removeBoard(cb);
        }
        for (BoardData sb : this.supplierBoardsToRemove) {
            this.supplier.removeBoard(sb);
        }
    }

    /**
     * exports the valid cuts
     * @return ArrayList of all the valid cuts
     */
    ArrayList<IWritable> export() {
        return this.cuts;
    }


}
