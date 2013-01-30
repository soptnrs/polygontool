/**
 * A class which provides a single public method which draws a polygon and a circle.
 * 
 * This method also drawS the overlapping area of the two shapes.
 * 
 * @author Jianming Zhang
 * @author Jeffrey Finkelstein <jeffreyf>
 * @since Spring 2011
 */


import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Drawer {
/**
 * Draw the specified polygon and circle 
 * The argument @param{showOverlap} is the flag for draw the
 * overlapping area of the two shapes. It needs to be implemented.
 * 
 * @param drawable
 * @param polygon: Polygon to draw
 * @param circle: Circle to draw
 * @param showOverlap: if draw the overlap
 */
public static void draw(final GLAutoDrawable drawable, final Polygon polygon, final Circle circle, final boolean showOverlap) {
	// store the current color and stencil buffer
	final GL2 gl = (GL2) drawable.getGL();
	gl.glPushAttrib(GL2.GL_CURRENT_BIT);
	
	if (showOverlap){
		/*
		 * Comment the following two lines if you want to also drawing the 
		 * overlaping area to get the extra credit.
		 */
		drawConcave(drawable, polygon, polygon.getColor());
		drawCircle(drawable, circle, circle.getColor());
		/*
		 * Your code should go here.
		 */
	}
	else{
		drawConcave(drawable, polygon, polygon.getColor());
		drawCircle(drawable, circle, circle.getColor());
	}
		
	
	// restore the attributes
	gl.glPopAttrib();
	
}

////////////////////////////////////////////////////////////////
//  IMPLEMENT THE FUNCTIONS BELOW                     //////////  
////////////////////////////////////////////////////////////////

/**
 * Draws the specified concave polygon with the specified OpenGL drawable
 * object.
 * 
 * Pre-condition: the specified polygon is concave.
 * 
 * @param drawable
 *          The OpenGL object on which to draw this polygon.
 * @param polygon
 *          The polygon to draw.
 */
private static void drawConcave(final GLAutoDrawable drawable,
    final Polygon polygon, final Color color) {
	/*
	 * The next line is only here until you implement your own algorithm for drawing
	 * a concave polygon. Comment out this line once you have implemented it!
	 */
	// drawConvex(drawable,polygon,color);  
	/*
	 * Your code for drawing a concave polygon, using the openGL stencil buffer should go here.
	 */
	if (polygon.vertices().isEmpty())
		return;

	final GL2 gl = (GL2) drawable.getGL();
	
	/* Set up stencil buffer */
	gl.glClearStencil(0);
	gl.glEnable(GL.GL_STENCIL_TEST);
	gl.glColorMask(false, false, false, false);
	gl.glStencilFunc(GL.GL_NEVER, 0, 1);
	gl.glStencilOp(GL.GL_INVERT, GL.GL_INVERT, GL.GL_INVERT);
	
	// if only 1 vertex, draw a point
	if (polygon.vertices().size() == 1)
		gl.glBegin(GL.GL_POINTS);

	// if only 2 vertices, draw a line
	else if (polygon.vertices().size() == 2)
		gl.glBegin(GL.GL_LINES);

	// otherwise draw a polygon
	else
		gl.glBegin(GL2.GL_TRIANGLE_FAN); /* For best performance here */

	for (final Point vertex : polygon.vertices())
		gl.glVertex2f(vertex.x, vertex.y);

	gl.glEnd();
	
	/* Re-enable color */
	gl.glColorMask(true, true, true, true);
	gl.glStencilFunc(GL.GL_EQUAL, 1, 1);
	gl.glStencilOp(GL.GL_ZERO, GL.GL_ZERO, GL.GL_ZERO);
	
	// push the current color
	gl.glPushAttrib(GL2.GL_CURRENT_BIT);

	// use the current polygon's color
	gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

	// if only 1 vertex, draw a point
	if (polygon.vertices().size() == 1)
		gl.glBegin(GL.GL_POINTS);

	// if only 2 vertices, draw a line
	else if (polygon.vertices().size() == 2)
		gl.glBegin(GL.GL_LINES);

	// otherwise draw a polygon
	else
		gl.glBegin(GL2.GL_POLYGON);

	for (final Point vertex : polygon.vertices())
		gl.glVertex2f(vertex.x, vertex.y);

	gl.glEnd();

	// pop current color
	gl.glPopAttrib();
	gl.glDisable(GL.GL_STENCIL_TEST);
}

////////////////////////////////////////////////////////////////
//  IMPLEMENT THE FUNCTIONS ABOVE                     //////////  
////////////////////////////////////////////////////////////////

/**
 * Draws the specified convex polygon with the specified OpenGL drawable
 * object.
 * 
 * Pre-condition: the specified polygon is convex.
 * 
 * @param drawable
 *          The OpenGL object on which to draw this polygon.
 * @param polygon
 *          The polygon to draw.
 */
private static void drawConvex(final GLAutoDrawable drawable,
    final Polygon polygon, final Color color) {
  if (polygon.vertices().isEmpty())
    return;

  final GL2 gl = (GL2) drawable.getGL();

  // push the current color
  gl.glPushAttrib(GL2.GL_CURRENT_BIT);

  // use the current polygon's color
  gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

  // if only 1 vertex, draw a point
  if (polygon.vertices().size() == 1)
    gl.glBegin(GL.GL_POINTS);

  // if only 2 vertices, draw a line
  else if (polygon.vertices().size() == 2)
    gl.glBegin(GL.GL_LINES);

  // otherwise draw a polygon
  else
    gl.glBegin(GL2.GL_POLYGON);

  for (final Point vertex : polygon.vertices())
    gl.glVertex2f(vertex.x, vertex.y);

  gl.glEnd();

  // pop current color
  gl.glPopAttrib();
}

private static void drawCircle(final GLAutoDrawable drawable, final Circle circle, final Color color) {
	
	if (circle.getCenterPoint().x < 0)
		return;
    final GL2 gl = (GL2) drawable.getGL();

 
    //Color color = circle.getColor();
    gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
    
    //Making circle in 50 small triangles	
    double increment = 2*Math.PI/50;
    	
    float cx = circle.getCenterPoint().x;
    float cy = circle.getCenterPoint().y;
    //Defining radius of circle equal to 70 pixels  
    double radius = circle.getRadius();
    
    //Starting loop for drawing triangles  
	  
    gl.glBegin(GL2.GL_POLYGON);
	for(double angle = 0; angle < 2*Math.PI; angle+=increment){
	//One vertex of each triangle is at center of circle
		//gl.glVertex2d(cx, cy);
	//Other two vertices form the periphery of the circle		
		gl.glVertex2d(cx + Math.cos(angle)* radius, cy + Math.sin(angle)*radius);
		gl.glVertex2d(cx + Math.cos(angle + increment)*radius, cy + Math.sin(angle + increment)*radius);
	}
	gl.glEnd();
}



}
