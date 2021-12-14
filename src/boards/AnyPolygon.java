package boards;

import java.util.ArrayList;

/**
 * Used in part 4 to represent any possible polygon
 */
class AnyPolygon extends Polygon {

    /**
     * basic constructor
     */
    AnyPolygon(){
        this.points = new ArrayList<Point>();
        this.addPoint(new Point(0,0));
        this.addPoint(new Point(1,1));
    }

    /**
     * Overloaded constructor
     * @param points list of points describing the polygon
     */
    AnyPolygon(ArrayList<Point> points){
        this.points = points;

    }

    /**
     * tests if the polygon can be placed on the board
     * @param b board to place the polygon onto
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
     * tests if two polygons overlap. Not working yet.
     * @param p another polygon to compare self to
     * @return always false as of now
     */
    boolean overlaps(Polygon p){
        return false; // TODO
    }
}
