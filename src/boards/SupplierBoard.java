package boards;


import java.util.ArrayList;


/**
 * represents an offer from the supplier
 */

class SupplierBoard extends BoardData {

    SupplierBoard(){
        super(0,0,0,"00.00.00",0,0, 0);
    }

     SupplierBoard(int oid, int id, int amount, String date,double price, double length,double width) {
         super(oid, id, amount, date, price, length, width);

    }
    static ArrayList<BoardData> ordonneBoard(ArrayList<Supplier> t){
        ArrayList<BoardData> boards = new ArrayList<BoardData>();
        for(Supplier s:t){
            for(BoardData board:s.boards){
                board.setUserid(s.getId());
                boards.add(board);
            }
        }
        return boards;
    }
}
