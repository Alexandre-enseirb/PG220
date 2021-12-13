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
    static ArrayList<ClientBoard> ordonneBoard(ArrayList<Client> t){
        ArrayList<ClientBoard> boards = new ArrayList<ClientBoard>();
        for(Client c:t){
            for(BoardData board:c.boards){
                board.setUserid(c.getId());
                boards.add((ClientBoard) board);
            }
        }
        return boards;
    }

    static void sort(ArrayList<ClientBoard> t){
        for(int i =1;i<t.size();i++){
            for (int j =0;j<t.size()-1;j++){
                if(t.get(j).length.getValue()<t.get(j+1).length.getValue()){ // 比length
                    ClientBoard temp = t.get(j);
                   // ClientBoard temp2 = t.get(j+1);
                    t.set(j,t.get(j+1));
                    t.set(j+1,temp);
                }
                else if(t.get(j).length.getValue() == t.get(j+1).length.getValue()){
                    if(t.get(j).width.getValue() < t.get(j+1).width.getValue()){
                        ClientBoard temp = t.get(j);
                        // ClientBoard temp2 = t.get(j+1);
                        t.set(j,t.get(j+1));
                        t.set(j+1,temp);
                    }
                }


            }
        }
    }
}
