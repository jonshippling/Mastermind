package model;

public enum Color {
    
	BAD(null, null),
    RED("Red", java.awt.Color.red),
    CYAN("Cyan", java.awt.Color.cyan),
    BLUE("Blue", java.awt.Color.blue),
    YELLOW("Yellow", java.awt.Color.yellow),
    GREEN("Green", java.awt.Color.green),
    WHITE("White", java.awt.Color.white),
    BLACK("Black", java.awt.Color.black),
    GRAY("None", java.awt.Color.lightGray); 
    
    private final String name;
    private final java.awt.Color awtColor;
    
    Color(String name, java.awt.Color awtColor) {
        this.name = name;
        this.awtColor = awtColor;
    }
    
    public String toString() {
        return name;
    }
    
    public java.awt.Color getAwtColor() {
        return awtColor;
    }
    
    public static Color findColorByAwtColor(java.awt.Color color) {
    	for(Color c : Color.values()) {
    		if(c.getAwtColor() == color) {
    			return c;
    		}
    	}
    	return BAD;
    }
}