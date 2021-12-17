package boards;

/**
 * Represents a point in 2D
 */
class Point {

    /**
     * Coordinate along the x-axis
     */
    private double x;
    /**
     * Coordinate along the y-axis
     */
    private double y;

    Point(){
        this.x=0;
        this.y=0;
    }

    Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * tests if the point is within the boundaries of the board
     * @param b a board
     * @return true if the point is on the board, false else
     */
    boolean isOnBoard(BoardData b){
        if (this.x < 0 || this.x > b.getLength().getValue() )
            return false;
        if (this.y < 0 || this.y > b.getWidth().getValue() )
            return false;
        return true;
    }
	
	double getX() {
		return this.x;
	}
	
	double getY() {
        return this.y;
    }
}
