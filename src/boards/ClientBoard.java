package boards;

class ClientBoard extends BoardData {

    private boolean validated;
    Polygon shape;

    ClientBoard(){
        this.id=0;
        this.date=new Date();
        this.width = new Dimension();
        this.length = new Dimension();
        this.price = new Price();
        this.amount = new Amount();
        this.shape = AnyPolygon();
    }

    ClientBoard(int id, Date d, Amount a, Price p,
                Dimension l, Dimension w, Polygon shape){
        this.id=id;
        this.date=d;
        this.amount=a;
        this.price=p;
        this.length=l;
        this.width=w;
        this.shape = shape;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
