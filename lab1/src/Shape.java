
/**
 * Abstrat shape class
 * with basic geometric functions
 * 
 * 
 * @author jmzhang
 *
 */
public abstract class Shape {

/** The shapes's RGB color, which can be changed */
private Color color = new Color();
private BoundingBox2D bbox = new BoundingBox2D();

public Shape () {}

/**
 * Returns {@code true} if and only if the specified point is inside this
 * shape.
 * 
 * @param x
 *          The x value of the point to test.
 * @param y
 *          The y value of the point to test.
 * @return {@code true} if and only if the specified point is inside this
 *         polygon.
 */
public abstract boolean isInside(final float x, final float y);
public abstract void setBbox();

public BoundingBox2D getBbox(){
	return this.bbox;
}
public Color getColor() {
	return color;
}
public void setColor(Color color) {
	this.color = color;
}
public void setRGBColor(float r, float g, float b) {
	this.color.setColor(r, g, b);
}



}
