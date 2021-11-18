package boards;

import java.util.ArrayList;
import utils;
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
    void loadClient(IReader r){
        this.boards = new ArrayList<BoardData>();
        while(r.hasNext()){
            if (r.localName().equals("client"))
                this.id=r.readInt();
            if (r.localName().equals("board"))
                this.boards.add(BoardData.readBoard(r, true));

        }


    }

}
