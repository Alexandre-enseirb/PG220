package boards;

import java.util.ArrayList;

class Cut implements IWritable {

    private Client client;
    private Supplier supplier;
    /* the following lists are used to store the cuts for later */
    private ArrayList<BoardData> validBoards;
    private ArrayList<BoardData> supplierBoards;
    private ArrayList<Integer>   quantities;

    /* unused value, might delete later */
    private int id;

    Cut(){
        this.id=0;
        this.client = new Client();
        this.supplier = new Supplier();
        this.validBoards=new ArrayList<>();
    }

    Cut(int id, Client client, Supplier supplier){
        this.id=id;
        this.client=client;
        this.supplier=supplier;
        this.validBoards=new ArrayList<>();
        this.supplierBoards = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }


    void hasValidCuts(){
        ArrayList<BoardData> supplierBoards =  this.supplier.getBoards();
        ArrayList<BoardData> clientBoards   =  this.client.getBoards();
        String tab="    ";
        for (BoardData cb: clientBoards){
            // looking for a pending board

            if (!((ClientBoard) cb).isValidated()) {
                for (BoardData sb : supplierBoards) {
                    // comparing to a supplier board
                    if (!cb.getDate().comesAfter(sb.getDate())) {
                        System.out.println(tab+"Delays not working.");
                        continue; // won't be done in the given delays
                    }
                    if (cb.getPrice().getValue() < sb.getPrice().getValue()) {
                        System.out.println(tab+"Too expensive.");
                        continue; // too expensive for the client
                    }
                    if (cb.getAmount().getValue() > sb.getAmount().getValue()) {
                        System.out.println(tab+"Not enough boards to buy.");
                        continue; // supplier can't supply enough
                    }
                    if (    (cb.getLength().getValue() > sb.getLength().getValue() ||
                            cb.getWidth().getValue() > sb.getWidth().getValue() )  &&
                            (cb.getWidth().getValue() > sb.getLength().getValue()  ||
                            cb.getLength().getValue() > sb.getWidth().getValue())  )
                    {
                        System.out.println(tab + "Board too small.");
                        continue; // does not fit in length
                    }
                    // if we get here, everything is okay.
                    ((ClientBoard) cb).setValidated(true);
                    //displayCut((ClientBoard) cb, (SupplierBoard) sb);
                    this.validBoards.add(cb);
                    this.supplierBoards.add(sb);
                    this.quantities.add(cb.getAmount().getValue());
                    try {
                        this.supplier.buy(sb, cb.getAmount());
                    }
                    catch(NotEnoughBoardsException e){
                            e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * subdivides the cut in different arrays of string. Each array corresponds to a different Object
     * @return
     */
    public ArrayList<ArrayList<String>> toStr() {
        ArrayList<ArrayList<String>> listS = new ArrayList<>();
        SupplierBoard sb = new SupplierBoard();
        ArrayList<String> cutData      = new ArrayList<>();

        int loop=0;
        for ( BoardData b : this.validBoards ) {
            cutData.add("cut");     // data(0)
            cutData.add("client");  // data(1)
            cutData.add("id");      // data(2)
            cutData.add(Integer.toString(b.getOwnerId()));
            cutData.add("board");   // data(4)
            cutData.add(Integer.toString(b.getId()));
            cutData.add(Integer.toString(b.getAmount().getValue()));
            sb = (SupplierBoard) this.supplierBoards.get(loop);
            cutData.add("supplier");
            cutData.add("id");
            cutData.add(Integer.toString(sb.getOwnerId()));
            cutData.add("board");
            cutData.add(Integer.toString(sb.getId()));
            cutData.add(Integer.toString(sb.getAmount().getValue()));
            cutData.add("position");
            cutData.add("x");
            cutData.add("0");
            cutData.add("y");
            cutData.add("0");
            listS.add(cutData);
        }

        /*
        listS.add(Integer.toString(this.id));
        listS.add(Integer.toString(this.client.getId()));
        listS.add(Integer.toString(this.supplier.getId()));
        for (BoardData b : this.validBoards) {
            listS.add(Integer.toString(b.getId()));
            listS.add(Double.toString(b.getAmount().getValue()));
            listS.add(b.getDate().toString());
            listS.add(Double.toString(b.getPrice().getValue()));
            listS.add(Double.toString(b.getLength().getValue()));
            listS.add(Double.toString(b.getWidth().getValue()));
        }
         */
        return listS;
    }
}
