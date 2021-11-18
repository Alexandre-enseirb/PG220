package boards;

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
