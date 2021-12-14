package boards;

/**
 * Bonus class
 */
class Color {

    /**
     * red intensity
     */
    private int r;
    /**
     * blue intensity
     */
    private int b;
    /**
     * green intensity
     */
    private int g;
    /**
     * name of the color
     */
    private String name;

    /**
     * Constructor
     */
    Color(){
        this.r=0;
        this.g=0;
        this.b=0;
        this.name="Black";
    }

    /**
     * Overloaded constructor
     * @param r red intensity
     * @param g green intensity
     * @param b blue intensity
     * @param name name of the color
     */
    Color(int r, int g, int b, String name){
        this.r=r;
        this.b=b;
        this.g=g;
        this.name=name;
    }

    /**
     * ensures the value is in [0,255]
     * @param val color to use
     * @return color in [0,255]
     */
    private int normalize(int val){
        if (val<0)
            return 0;
        if (val>255)
            return 255;
        return val;
    }

    /**
     * avoids a color without a name
     * @param name name to set
     * @return potential new name
     */
    private String defName(String name){
        if (name.equals(""))
            return "default";
        return name;
    }


}
