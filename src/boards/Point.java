package boards;

class Point {

    private int x;
    private int y;

    Point(){
        this.x=0;
        this.y=0;
    }

    Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    boolean isOnBoard(BoardData b){
        if (this.x < 0 || this.x > b.getLength().getValue() )
            return false;
        if (this.y < 0 || this.y > b.getWidth().getValue() )
            return false;
        return true;
    }

}
