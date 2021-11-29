package boards;

class ClientBoard extends BoardData {

    private boolean validated;
    Polygon shape;

  /*  ClientBoard(){
        this.id=0;
        this.date=new Date();
        this.width = new Dimension();
        this.length = new Dimension();
        this.price = new Price();
        this.amount = new Amount();
        this.shape = AnyPolygon();
    }*/

    public ClientBoard(int id, int amount, String date,double price, IGenerable length, IGenerable width) {
        super(id,amount,date,price,length,width);
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
