package boards;

import java.util.ArrayList;
import utils.IReader;

class Client extends Actor {

    //private Reader reader; --- UNCOMMENT WHEN EXISTS


    Client(){
        this.id=0;
        this.boards = new ArrayList<BoardData>();

    }

    Client(int id, ArrayList<BoardData> listB){
        this.id=id;
        this.boards = listB;

    }

    void Validate(int boardId){
        ClientBoard b = (ClientBoard) this.boards.get(boardId);
        b.setValidated(true);
    }

    /**
     * will probably change, supposed to load a client
     * @param r
     */


}
