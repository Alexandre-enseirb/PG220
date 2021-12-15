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

    static ArrayList<BoardData> ordonneBoard2(ArrayList<Supplier> t){
        ArrayList<BoardData> boards = new ArrayList<BoardData>();
        for(Supplier c:t){
            for(BoardData board:c.boards){
                for(int i = 0;i<board.getAmount().getValue();i++){
                    BoardData b = new ClientBoard(c.id,board.id,1,board.date.toString(),board.price.getValue(),board.length.getValue(),board.width.getValue(),i);
                    boards.add(b);
                }
            }
        }
        return boards;
    }
}
