package boards;

import java.util.ArrayList;

class Cut implements IWritable {

    private Client client;
    private Supplier supplier;
    private ArrayList<BoardData> validBoards;
    private int id;
    private ArrayList<String> algo2;

    Cut(){
        this.id=0;
        this.client = new Client();
        this.supplier = new Supplier();
        this.validBoards=new ArrayList<>();
        this.algo2 = new ArrayList<>();
    }

    Cut(int id, Client client, Supplier supplier){
        this.id=id;
        this.client=client;
        this.supplier=supplier;
        this.validBoards=new ArrayList<>();
        this.algo2 = new ArrayList<>();
        this.algo2.add(Integer.toString(id));
        this.algo2.add(Integer.toString(client.id));
        this.algo2.add(Integer.toString(supplier.id));

    }

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
                    if (!cb.getDate().comesAfter(sb.getDate())) {
                        //System.out.println(tab+cb.id+"Delays not working.");
                        System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Delays not working");
                        continue; // won't be done in the given delays
                    }
                    if (cb.getPrice().getValue() < sb.getPrice().getValue()) { //  price nest pas correcte
                        System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " too expensive");
                        continue; // too expensive for the client
                    }
                    if (cb.getAmount().getValue() > sb.getAmount().getValue()) {
                        System.out.println(tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Not enough board to buy");
                        continue; // supplier can't supply enough
                    }
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
                    try {
                        System.out.println(tab+tab +"Client id " + this.client.id + " board " + cb.id+ " Supplier " + this.supplier.id +" board "+ sb.id + " Buy it.");
                        this.supplier.buy(sb, cb.getAmount());
                        break;
                    }
                    catch(NotEnoughBoardsException e){
                            e.printStackTrace();
                    }

                }
            }
        }
    }
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
    }

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
    public ArrayList<String> toStr(){
        return this.algo2;
    }  // retourner directement array de algo2
}
