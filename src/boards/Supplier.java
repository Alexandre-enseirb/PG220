package boards;

import utils.IReader;

import java.util.ArrayList;

/**
 * Our suppliers
 */
class Supplier extends Actor{

    Supplier(){
        this.id=0;
        this.boards = new ArrayList<BoardData>();
    }

    Supplier(int id,ArrayList<BoardData> boards){
        this.id=id;
        this.boards=boards;
    }

    /*ArrayList<BoardData> allValidBoards(ClientBoard b){
        ArrayList<BoardData> ret = new ArrayList<BoardData>();
        for (BoardData sb : boards){
            if (b.shape.fitsOnBoard(sb))
                ret.add(sb);
        }

        return ret;

    }*/

    /**
     * Removes the quantity of boards described by a to this supplier
     * @param b boards to buy
     * @param a quantity to buy
     * @throws NotEnoughBoardsException if the supplier has less boards than we want to buy
     */
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
