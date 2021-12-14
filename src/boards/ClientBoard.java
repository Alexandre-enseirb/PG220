package boards;

import java.util.ArrayList;

class ClientBoard extends BoardData {

    private boolean validated;
    Polygon shape;

    ClientBoard(){
        super(0,0,0,"00.00.00",0, 0, 0);
    }

     ClientBoard(int oid, int id, int amount, String date,double price,double length,double width) {
        super(oid, id,amount,date,price,length,width);
    }

    @Override
    boolean allValid() {
        return super.allValid();
    }

    public boolean isValidated() {
        return validated;
    }

    void setValidated(boolean validated) {
        this.validated = validated;
    }

    // take all the boards demand from arraylist of client
    static ArrayList<BoardData> ordonneBoard(ArrayList<Client> t){
        ArrayList<BoardData> boards = new ArrayList<BoardData>();
        for(Client c:t){
            for(BoardData board:c.boards){
                board.setUserid(c.getId());
                boards.add(board);
            }
        }
        return boards;
    }


}
