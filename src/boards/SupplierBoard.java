package boards;

class SupplierBoard extends BoardData {

    SupplierBoard(){
        super(0,0,"00.00.00",0,new Dimension(0), new Dimension(0));
    }

     SupplierBoard(int id, int amount, String date,double price, IGenerable length, IGenerable width) {
         super(id, amount, date, price, length, width);

    }

}
