package boards;

/**
 * represents an offer from the supplier
 */
class SupplierBoard extends BoardData {

    SupplierBoard(){
        super(0,0,0,"00.00.00",0,0, 0);
    }

     SupplierBoard(int oid, int id, int amount, String date,double price, double length,double width) {
         super(oid, id, amount, date, price, length, width);

    }

}
