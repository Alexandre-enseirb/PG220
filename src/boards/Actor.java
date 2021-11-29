package boards;

import java.util.ArrayList;


abstract class Actor implements IGenerable {

    int id;
    ArrayList<BoardData> boards;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    ArrayList<BoardData> getBoards() {
        return boards;
    }

    void setBoards(ArrayList<BoardData> boards) {
        this.boards = boards;
    }
}
