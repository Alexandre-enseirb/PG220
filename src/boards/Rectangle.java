package boards;

/**
 * Simplifies rectangles management
 */
class Rectangle extends Polygon {

    Point p1;
    Point p2;

    Rectangle(){
        this.p1= new Point();
        this.p2 = new Point();
    }

    Rectangle(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        this.addPoint(p1);
        this.addPoint(p2);
    }

    /**
     * tests if the rectangle is on the board
     * @param b the board
     * @return true if it fits, false else
     */
    boolean fitsOnBoard(BoardData b){
        for (Point p : this.getPoints()){
            if (!p.isOnBoard(b))
                return false;
        }
        return true;
    }

    /**
     * tests if both polygons overlap
     * @param p another polygon to compare this with
     * @return false as of now
     */
    boolean overlaps(Polygon p){
        return false; // TODO
    }

    double getLength() {
        double ret = this.p2.getX() - this.p1.getX();
        if (ret < 0){
            ret *=-1;
        }
        return ret;
    }

    double getWidth() {
        double ret = this.p2.getY() - this.p1.getY();
        if (ret < 0){
            ret*=-1;
        }
        return ret;
    }
}
