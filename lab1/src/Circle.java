

/**
 * A polygon with additional drawing capabilities.
 * 
 * @author Stan Sclaroff <sclaroff>
 * @auther Jianming Zhang
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffreyf>
 */
public class Circle extends Shape{

/** center point*/
private Point cpoint;
/** radius*/
private float radius;
/** The circle's bounding box */
private BoundingBox2D bbox = new BoundingBox2D();

public Circle()
{
	cpoint = new Point(-100,-100);
	radius = 50;
	// default constructor gives circle color = white
	this.setRGBColor(1, 1, 1);
}
	  
public Circle(float r, float g, float b)
{
	this.cpoint = new Point(-100,-100);
	this.radius = 50;
	this.setRGBColor(r, g, b);
}
	  
/**
 * Set the center of the circle at the point specified by the given x and y
 * values.
 * 
 * @param x
 *          The x value of the center point.
 * @param y
 *          The y value of the center point.
 */
public void setCenterPoint(final float x, final float y) {
	this.cpoint = new Point(x, y);
}

/**
 * Set the radius of the circle at the point specified by r
 * values.
 * 
 * @param r
 *          radius.
 */
public void setRadius (final float r) {
	this.radius=r;
}
	  

/**
 * Returns {@code true} if and only if the specified point is inside this
 * circle.
 * 
 * @param x
 *          The x value of the point to test.
 * @param y
 *          The y value of the point to test.
 * @return {@code true} if and only if the specified point is inside this
 *        circle.
*/
public boolean isInside(final float x, final float y) {
	float dis2=(x-this.cpoint.x)*(x-this.cpoint.x) + 
			(y-this.cpoint.y)*(y-this.cpoint.y);
	return this.radius-Math.sqrt(dis2) >= 0;
}

/**
 * Move the boundary to the passing point (x,y)
 * 
 * @param x
 *          The x value of the point to test.
 * @param y
 *          The y value of the point to test.
*/
public void moveBoundary(final float x, final float y) {
	this.radius = (float) Math.sqrt((x-this.cpoint.x)*(x-this.cpoint.x)+
			(y-this.cpoint.y)*(y-this.cpoint.y));
}


 /**
  * Resets this circle so that its radius is 0 and center point
  * is (-1,-1).
  */
public void reset() {
	this.cpoint = new Point(-100,-100);
}

/**
 * Returns the center point.
 * 
 * @return The center point.
 */
Point getCenterPoint() {
	return this.cpoint;
}
/**
 * Returns the radius.
 * 
 * @return The radius.
 */
float getRadius(){
	return this.radius;
}

public void setBbox() {
	this.bbox.set(cpoint.x-radius,cpoint.y-radius,cpoint.x+radius,cpoint.y+radius);	
}

}
