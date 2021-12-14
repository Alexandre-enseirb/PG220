package boards;

import java.util.ArrayList;

/**
 * Class representing the clients
 */
class Client extends Actor {


    /**
     * constructor
     */
    Client(){
        this.id=0;
        this.boards = new ArrayList<BoardData>();
    }

    /**
     * Overloaded constructor
     * @param id id of the Client
     * @param listB list of Boards
     */
    Client(int id, ArrayList<BoardData> listB){
        this.id=id;
        this.boards = listB;

    }

    /**
     * sets a board as Validated (a Supplier has been found for this board)
     * @param boardId id of the board in the list
     */
    void Validate(int boardId){
        ClientBoard b = (ClientBoard) this.boards.get(boardId);
        b.setValidated(true);
    }


    /**
     * adds a board to the client's list
     * @param b board to add
     */
    void addBoard(BoardData b) {
        this.boards.add(b);
    }

    /**
     * gets the Client's id
     * @return the Client's id
     */
    int getId(){
        return this.id;
    }



}
