package boards;

class SupplierBoard extends BoardData {

    SupplierBoard(){
        this.id=0;
        this.date=new Date();
        this.width = new Dimension();
        this.length = new Dimension();
        this.price = new Price();
        this.amount = new Amount();
    }

    SupplierBoard(int id, Date d, Amount a, Price p, Dimension l, Dimension w){
        this.id=id;
        this.date=d;
        this.amount=a;
        this.price=p;
        this.length=l;
        this.width=w;
    }


}
