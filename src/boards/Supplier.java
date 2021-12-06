package boards;

import utils.IReader;

import java.util.ArrayList;

class Supplier extends Actor{

    Supplier(){
        this.id=0;
        this.boards = new ArrayList<BoardData>();
    }

    Supplier(int id,ArrayList<BoardData> boards){
        this.id=id;
        this.boards=boards;
    }
    /**
     * Parses through all of the supplier's boards and returns those
     * that get along with what the client needs
     * @param b board needed by the client
     * @return {ArrayList\<BoardData\>} boards that fit
     */
    /*ArrayList<BoardData> allValidBoards(ClientBoard b){
        ArrayList<BoardData> ret = new ArrayList<BoardData>();
        for (BoardData sb : boards){
            if (b.shape.fitsOnBoard(sb))
                ret.add(sb);
        }

        return ret;

    }*/

    void buy(BoardData b, Amount a) throws NotEnoughBoardsException {
        if (b.getAmount().getValue() < a.getValue()){
            throw new NotEnoughBoardsException();
        }
        b.setAmountValue(b.getAmount().getValue() - a.getValue());
    }

    void addBoard(BoardData b) {
        this.boards.add(b);
    }

    void buy2(BoardData b, Amount a){

        b.setAmountValue(b.getAmount().getValue() - a.getValue());
    }
    int getId() {
        return this.id;
    }
}
