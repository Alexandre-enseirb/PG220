package boards;

class ClientBoard extends BoardData {

    private boolean validated;
    Polygon shape;

    ClientBoard(){
        super(0,0,0,"00.00.00",0, 0, 0);
    }

     ClientBoard(int oid, int id, int amount, String date,double price,double length,double width) {
        super(oid, id,amount,date,price,length,width);
    }


    public boolean isValidated() {
        return validated;
    }

    void setValidated(boolean validated) {
        this.validated = validated;
    }
}
