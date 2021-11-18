package boards;


import java.util.ArrayList;

abstract class Polygon {

    private ArrayList<Point> points;

    abstract boolean fitsOnBoard(BoardData board);

    abstract boolean overlaps(Polygon p);

    void addPoint(Point p){
        this.points.add(p);
    }

    ArrayList<Point> getPoints(){
        return this.points;
    }

    /**
     * will probably change
     * @param r
     * @return
     */
    static Polygon readPolygon(IReader r){
        ArrayList<Point> points = new ArrayList<>();
        while (r.localName().equals("shape")){
            points.add(new Point(r.readInt(), r.readInt()));
        }
    }
}
