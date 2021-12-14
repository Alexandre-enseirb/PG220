package boards;

import java.util.ArrayList;

/**
 * abstract class used to represent either a Client or a Supplier
 */
abstract class Actor implements IGenerable {
    /**
     * id of the Actor
     */
    int id;
    /**
     * List of boards possessed by the Supplier or requested by the Client
     */
    ArrayList<BoardData> boards;

    /**
     * @return Actor's id
     */
    int getId() {
        return id;
    }

    /**
     * @param id value to set as the Actor's id
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * @return List of boards possessed by the Actor
     */
    ArrayList<BoardData> getBoards() {
        return boards;
    }

    /**
     * @param boards List of boards to set for the Actor
     */
    void setBoards(ArrayList<BoardData> boards) {
        this.boards = boards;
    }

    /**
     * removes a board to the actor's list
     * @param b board to remove
     */
    void removeBoard(BoardData b) { this.boards.remove(b); }
}
