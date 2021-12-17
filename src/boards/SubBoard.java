package boards;

/**
 * Used in part 3 to represent a part of the original board, after a cut was chosen on it.
 * Has fewer parameters than ClientBoards/SupplierBoards
 */
public class SubBoard {

    /**
     * length of the board
     */
    private Dimension length;
    /**
     * width of the board
     */
    private Dimension width;
    /**
     * position of this sub-board on the original board, x-axiswise
     */
    private double offsetX;
    /**
     * position of this sub-board on the original board, y-axiswise
     */
    private double offsetY;
    /**
     * the original board, where we can fetch useful information such as the date of delivery or the price
     */
    private BoardData parent;

    SubBoard(BoardData parent, Dimension l, Dimension w, double offX, double offY) {
        this.parent = parent;
        this.length  = l;
        this.width   = w;
        this.offsetX = offX;
        this.offsetY = offY;
    }

    double getPrice()  {
        return this.parent.getPrice().getValue();
    }

    Date getDate() {
        return this.parent.getDate();
    }

    double getLength() {
        return this.length.getValue();
    }

    double getWidth() {
        return this.width.getValue();
    }

    double getPosX(){
        return this.offsetX;
    }

    double getPosY(){
        return this.offsetY;
    }

    int getNumber() {
        return this.parent.getNumber();
    }

    int getOwnerId(){
        return this.parent.getOwnerId();
    }

    int getId(){
        return this.parent.getId();
    }

    void remove(int value) throws NoMoreBoardsException {
        try {
            this.parent.remove(value);
        }catch (NoMoreBoardsException e){
            throw new NoMoreBoardsException();
        }
    }

    BoardData getParent() {
        return this.parent;
    }
}
