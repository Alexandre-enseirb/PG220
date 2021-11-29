package boards;

class ClientBoard extends BoardData {

    private boolean validated;
    Polygon shape;

    ClientBoard(){
        super(0,0,"00.00.00",0, new Dimension(0), new Dimension(0));
    }

     ClientBoard(int id, int amount, String date,double price, IGenerable length, IGenerable width) {
        super(id,amount,date,price,length,width);
    }


    public boolean isValidated() {
        return validated;
    }

    void setValidated(boolean validated) {
        this.validated = validated;
    }
}
