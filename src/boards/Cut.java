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
    //private ArrayList<String> algo2;

    Cut(){
        this.id=0;
        this.client = new Client();
        this.supplier = new Supplier();
        this.validBoards=new ArrayList<>();
        //this.algo2 = new ArrayList<>();
    }

    Cut(int id, Client client, Supplier supplier){
        this.id=id;
        this.client=client;
        this.supplier=supplier;
        this.validBoards=new ArrayList<>();
        this.supplierBoards = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }
/*        this.algo2 = new ArrayList<>();
        this.algo2.add(Integer.toString(id));
        this.algo2.add(Integer.toString(client.id));
        this.algo2.add(Integer.toString(supplier.id));
*/


    // supplier的price比较有误 (已修改） ？
    // break  已修改
    void hasValidCuts(){
        ArrayList<BoardData> supplierBoards =  this.supplier.getBoards();
        ArrayList<BoardData> clientBoards   =  this.client.getBoards();
        String tab="    ";
        for (BoardData cb: clientBoards){
            // looking for a pending board

            if (!((ClientBoard) cb).isValidated()) {
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
                    ((ClientBoard) cb).setValidated(true);
                    //displayCut((ClientBoard) cb, (SupplierBoard) sb);
                    this.validBoards.add(cb);
                    this.supplierBoards.add(sb);
                    this.quantities.add(cb.getAmount().getValue());
                    try {
                        System.out.println(tab+tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Buy it.");
                        this.supplier.buy(sb, cb.getAmount());

                    }
                    catch(NotEnoughBoardsException e){
                            e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
    /*
    //les seules contraintes ´etant celle des dates
    //les client etant prˆets `a payer une somme ridiculement grande pour leur commande.
    void algo_etape2 () {
        ArrayList<BoardData> supplierBoards =  this.supplier.getBoards();
        ArrayList<BoardData> clientBoards   =  this.client.getBoards();
        String tab="    ";
        for (BoardData cb: clientBoards){
            if (!((ClientBoard) cb).isValidated()){
                for (BoardData sb : supplierBoards){
                    if(sb.getAmount().getValue() >0){
                        if (!cb.getDate().comesAfter(sb.getDate())) {
                            //System.out.println(tab+cb.id+"Delays not working.");
                            System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Delays not working");
                            continue; // won't be done in the given delays
                        }
                        // if we get here, everything is okay.
                        ((ClientBoard) cb).setValidated(true);
                        //displayCut((ClientBoard) cb, (SupplierBoard) sb);
                        if(cb.getAmount().getValue() <= sb.getAmount().getValue()){  //1er cas, nbr de serveur board > nbr de client board

                            this.algo2.add(Integer.toString(cb.id));
                            this.algo2.add(Integer.toString(sb.id));
                            this.algo2.add(Integer.toString(cb.getAmount().getValue()));
                            this.algo2.add(Integer.toString(cb.getCurrentid()));
                            this.algo2.add(Integer.toString(sb.getCurrentid()));
                            System.out.println(tab+"Client id "+this.client.id+" buy " +cb.id+" from "+"supplier"+ this.supplier.id+ " with panneaux "+sb.id + " in " +cb.getAmount().getValue()+" with prix " + sb.getPrice().getValue());

                            sb.setCurrentid(sb.getCurrentid()+cb.getAmount().getValue());



                            this.supplier.buy2(sb,cb.getAmount());
                            cb.setAmountValue(0);
                            //cb.setCurrentid(cb.getAmount().getValue());

*/


    /**
     * subdivides the cut in different arrays of string. Each array corresponds to a different Object
     * @return
     */
    public ArrayList<ArrayList<String>> toStr(){
        ArrayList<ArrayList<String>> listS=new ArrayList<>();
        SupplierBoard sb = new SupplierBoard();
        ArrayList<String> cutData=new ArrayList<>();

        int loop=0;
        for(BoardData b:this.validBoards){
            cutData.clear();                                            // avoids cut duplication
            cutData.add("cut");                                         // data(0)
            cutData.add("client");                                      // data(1)
            cutData.add("id");                                          // data(2)
            cutData.add(Integer.toString(b.getOwnerId()));              // data(3)
            cutData.add("board");                                       // data(4)
            cutData.add(Integer.toString(b.getId()));                   // data(5)
            cutData.add(Integer.toString(b.getAmount().getValue()));    // data(6)
            cutData.add(Double.toString(b.getLength().getValue()));     // data(7)
            cutData.add(Double.toString(b.getWidth().getValue()));      // data(8)
            sb=(SupplierBoard)this.supplierBoards.get(loop++);            // data(9)
            cutData.add("supplier");                                    // data(10)
            cutData.add("id");                                          // data(11)
            cutData.add(Integer.toString(sb.getOwnerId()));             // data(12)
            cutData.add("board");                                       // data(13)
            cutData.add(Integer.toString(sb.getId()));                  // data(14)
            cutData.add(Integer.toString(b.getAmount().getValue()));    // data(15) - TEMPORARY
            cutData.add(Double.toString(sb.getLength().getValue()));    // data(16)
            cutData.add(Double.toString(sb.getWidth().getValue()));     // data(17)
            cutData.add("position");                                    // data(18)
            cutData.add("x");                                           // data(19)
            cutData.add("0");                                           // data(20)
            cutData.add("y");                                           // data(21)
            cutData.add("0");                                           // data(22)
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
/*=======
                            break; // jump to prochain cb
                        }
                        else{ //2eme cas, nbr de serveur board < nbr de client board

                            this.algo2.add(Integer.toString(cb.id));  // 1. id de client board
                            this.algo2.add(Integer.toString(sb.id)); //2. id de serveur board
                            this.algo2.add(Integer.toString(sb.getAmount().getValue()));  //3 nbr de d'acheter
                            //System.out.println(tab+tab+tab+sb.getAmount().getValue());
                            this.algo2.add(Integer.toString(cb.getCurrentid())); // 4 current id cb
                            this.algo2.add(Integer.toString(sb.getCurrentid())); // 5 current id sb
                            System.out.println(tab+"Client id "+this.client.id+" buy " +cb.id+" from "+"supplier"+ this.supplier.id+ " with panneaux "+sb.id + " in " +sb.getAmount().getValue()+" with prix " + sb.getPrice().getValue());

                            cb.setCurrentid(cb.getCurrentid()+sb.getAmount().getValue());
                            //System.out.println(tab+tab+tab+tab+cb.getCurrentid());


                            cb.setAmountValue(cb.getAmount().getValue() - sb.getAmount().getValue());
                            this.supplier.buy2(sb,sb.getAmount());
                            //this.validBoards.add(cb);



                            continue;
                        }
                    }
                    else {
                        continue;
                    }


                }
            }
        }
>>>>>>> ef232cc6fe9c7d7ea806b459311950851cd5f1d0
    }*/

//    public ArrayList<String> toStr() {
//        ArrayList<String> listS = new ArrayList<>();
//        listS.add(Integer.toString(this.id));
//        listS.add(Integer.toString(this.client.getId()));
//        listS.add(Integer.toString(this.supplier.getId()));
//        for (BoardData b : this.validBoards) {
//            listS.add(Integer.toString(b.getId()));
//            listS.add(Double.toString(b.getAmount().getValue()));
//            listS.add(b.getDate().toString());
//            listS.add(Double.toString(b.getPrice().getValue()));
//            listS.add(Double.toString(b.getLength().getValue()));
//            listS.add(Double.toString(b.getWidth().getValue()));
//        }
//        return listS;
//    }
//    public ArrayList<String> toStr(){
//        return this.algo2;
//    }  // retourner directement array de algo2
}
