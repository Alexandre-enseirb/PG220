package boards;

import java.util.ArrayList;

abstract class Actor implements IGenerable{

    int id;
    ArrayList<BoardData> boards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<BoardData> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<BoardData> boards) {
        this.boards = boards;
    }
}
