package boards;

class SupplierBoard extends BoardData {

    /*SupplierBoard(){
        this.id=0;
        this.date=new Date();
        this.width = new Dimension();
        this.length = new Dimension();
        this.price = new Price();
        this.amount = new Amount();
    }*/

     SupplierBoard(int id, int amount, String date,double price, IGenerable length, IGenerable width) {
         super(id, amount, date, price, length, width);

    }

}
