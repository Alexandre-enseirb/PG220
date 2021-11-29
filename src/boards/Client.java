package boards;

import java.util.ArrayList;


class Client extends Actor {



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


    void addBoard(BoardData b) {
        this.boards.add(b);
    }

    int getId(){
        return this.id;
    }

}
