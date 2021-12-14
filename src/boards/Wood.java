package boards;

/**
 * For bonus part, describes the wood durability, origin and color
 */
class Wood {
    private int durability;
    private String origin;
    private Color color;

    Wood(){
        this.durability=0;
        this.origin="Oak";
        this.color = new Color();
    }

    Wood(int durability, String origin,Color color){
        this.durability=Math.max(0,durability);
        this.origin=origin;
    }



}
