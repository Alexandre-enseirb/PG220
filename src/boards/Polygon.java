package boards;


import utils.IReader;

import java.util.ArrayList;
import utils.IReader;

/**
 * Abstract class that links together AnyPolygon and Rectangle
 */
abstract class Polygon {

    /**
     * List of points
     */
    ArrayList<Point> points;

    /**
     * tests if the polygon fits on the board
     * @param board the board to test
     * @return true if it fits, false else
     */
    abstract boolean fitsOnBoard(BoardData board);

    /**
     * tests if two polygons overlap
     * @param p another polygon to compare this with
     * @return true if they overlap, false else
     */
    abstract boolean overlaps(Polygon p);

    /**
     * adds a point to the polygon
     * @param p A point
     */
    void addPoint(Point p){
        this.points.add(p);
    }

    ArrayList<Point> getPoints(){
        return this.points;
    }
}
