package boards;


import utils.IReader;

import java.util.ArrayList;
import utils.IReader;
abstract class Polygon {

    ArrayList<Point> points;

    abstract boolean fitsOnBoard(BoardData board);

    abstract boolean overlaps(Polygon p);

    void addPoint(Point p){
        this.points.add(p);
    }

    ArrayList<Point> getPoints(){
        return this.points;
    }
}
