package boards;

import java.util.ArrayList;

class AnyPolygon extends Polygon {

    AnyPolygon(){
        this.points = new ArrayList<Point>();
        this.addPoint(new Point(0,0));
        this.addPoint(new Point(1,1));
    }

    AnyPolygon(ArrayList<Point> points){
        this.points = points;

    }

    boolean fitsOnBoard(BoardData b){
        for (Point p : this.getPoints()){
            if (!p.isOnBoard(b))
                return false;
        }
        return true;
    }

    boolean overlaps(Polygon p){
        return false; // TODO
    }
}
