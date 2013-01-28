// ****************************************************************************
// Polygon class.
// ****************************************************************************
// Comments :
// Subroutines to manage and draw polygons
//
// History :
// 9 Jan 2013 - updated by Jianming Zhang
// 7 February 2011 - updated code to be more Java-y
// 9 Jan 2008 Created by Tai-Peng Tian (tiantp@gmail.com) based on code by
// Stan Sclaroff (from CS480 '06 poly.c)


import java.util.ArrayList;
import java.util.List;

/**
 * A polygon with additional drawing capabilities.
 * 
 * @author Stan Sclaroff <sclaroff>
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffreyf>
 * @author Jianming Zhang
 */
public class Polygon extends Shape{

  /** The currently selected vertex, which will be moved. */
  private Point selectedVertex = null;
  /** The list of vertices that make up this polygon. */
  private final ArrayList<Point> vertices = new ArrayList<Point>();
  /** The polygon's bounding box */
  private BoundingBox2D bbox = new BoundingBox2D();

  public Polygon()
  {
	  /** default constructor gives polygon color = white*/
	  this.setRGBColor(1, 1, 1);
  }
  
  public Polygon(float r, float g, float b)
  {
	  this.setRGBColor(r, g, b);
  }

////////////////////////////////////////////////////////////////
//  IMPLEMENT THE FUNCTIONS BELOW                     //////////  
////////////////////////////////////////////////////////////////
  
  /**
   * 
   * @return {@code true} if and only if polygon vertices are counterclockwise
   */
  public boolean isCounterClockwise()
  {
	/* This test uses a simplified version of green's theorem to get the signed area
	 * if the result is positive, curve is clockwise, else it is counter clockwise  
	 */
	  float sum = 0;
	  Point p1, p2;
	  int size = this.vertices.size();
	  for ( int ii = 0 ; ii < (size - 1) ; ii++ )
	  {
		  p1 = this.vertices.get(ii); 
		  p2 = this.vertices.get(ii+1);
		  sum += (p2.x - p1.x)*(p2.y + p1.y);
	  }
	  /* Add in the final edge */
	  sum += (this.vertices.get(0).x - this.vertices.get(size-1).x) * (this.vertices.get(0).y + this.vertices.get(size-1).y);
	  
	  /* Since the opengl coordinate system starts from top left, with +y going down, I invert the if statement to be consistent with
	   * the way the polygons will appear to the user (a typical counterclockwise in standard coordinates will look clockwise here)
	   */
	  
	  if (sum < 0)
		  return false;
	  else
		  return true;
  }
  
  /**
   * Returns {@code true} if and only if this polygon overlaps Circle c.
   * 
   * @param c
   *          The Circle to be tested for overlap 
   *          
   * @return {@code true} if and only if this polygon overlaps c.
   */
  
  public boolean isOverlapping(Circle c){
	  /*
	   * Test to see if this polygon overlaps with a given circle c. 
	   * For now this always returns false. You should implement this method.
	   */
	  
	  return false;	
  }
  
  /**
   * Returns {@code true} if and only if this polygon is concave.
   * 
   * @return {@code true} if and only if this polygon is concave.
   */
  public boolean isConcave() {
	  /* Calculate by finding sign of z component of cross product.  The sign of this,
	   * along with the knowledge of whether the polygon is drawn clockwise or counter-
	   * determines the concavity
	   */
	  Point p1, p2, p3;
	  float zCross;
	  boolean isCounterClockwise = isCounterClockwise();
	  int size = vertices.size();
	  
	  if (size < 3)
	  {
		  return false; /* Not yet a polygon */
	  }
	  
	  for (int ii = 0 ; ii < vertices.size() ; ii++)
	  {
		  if (ii == (size - 1))
		  {
			  /* Special case, angle that uses mid as center point */
			  p1 = vertices.get(ii);
			  p2 = vertices.get(0);
			  p3 = vertices.get(1);
		  }
		  else if (ii == (size -2))
		  {
			  /* Special case, angle that uses mid as last point */
			  p1 = vertices.get(ii);
			  p2 = vertices.get(ii+1);
			  p3 = vertices.get(0);
		  }
		  else
		  {
			  /* Base case */
			  p1 = vertices.get(ii);
			  p2 = vertices.get(ii+1);
			  p3 = vertices.get(ii+2);
		  }
		  
		  /* Get magnitudes */
		  zCross = (p2.x - p1.x)*(p3.y - p2.y) - (p2.y - p1.y)*(p3.x - p2.x);
		  if (isCounterClockwise)
		  {
			  /* I invert to stay consistent with direction, and the opengl coordinate system */
			  zCross *= -1;
		  }
		  
		  if (zCross < 0)
		  {
			  return true;
		  }
	  }
	  
	  
	  return false;
  }

  /**
   * Returns {@code true} if and only if the specified point is inside this
   * polygon by non-zero winding number
   * 
   * @param x
   *          The x value of the point to test.
   * @param y
   *          The y value of the point to test.
   * @return {@code true} if and only if the specified point is inside this
   *         polygon.
   */
  public boolean isInside(final float x, final float y) {
	  /* First detect if point is even in the bounding box */
	  if (x > this.bbox.getMaxX() || x < this.bbox.getMinX() ||
			  y > this.bbox.getMaxY() || y < this.bbox.getMinY())
		  return false;
	  
	  /* Next pick a point outside the polygon and verify the ray from the out point to this
	   * point does not cross any vertices, otherwise select a new line
	   */
	  Point outer = new Point(this.bbox.getMaxX() + 1, this.bbox.getMaxY() + 1);
//	  while (true)
//	  {
//		  
//	  }
	  return true;
  }

  /**
   * Returns {@code true} if the polygon has self-intersection
   * 
   */
  public boolean hasSelfIntersection(){
  	/*
  	 * For now, this method always returns false. Remove this line when you
  	 * implement your own algorithm.
  	 */
  	return false;
  }
  
////////////////////////////////////////////////////////////////
//  IMPLEMENT THE FUNCTIONS ABOVE                     //////////  
////////////////////////////////////////////////////////////////

  /**
   * Add a vertex to this polygon at the point specified by the given x and y
   * values.
   * 
   * @param x
   *          The x value of the vertex to add.
   * @param y
   *          The y value of the vertex to add.
   */
  public void addVert(final int x, final int y) {
    this.vertices.add(new Point(x, y));
  }


  /**
   * Moves the currently selected vertex to the specified location.
   * 
   * @param x
   *          The x value of the point to which to move the currently selected
   *          vertex.
   * @param y
   *          The y value of the point to which to move the currently selected
   *          vertex.
   */
  public void moveVert(final int x, final int y) {
    if (this.selectedVertex != null) {
      this.selectedVertex.x = x;
      this.selectedVertex.y = y;
    }
  }

  /**
   * Resets this polygon so that its list of vertices is empty and the currently
   * selected vertex is {@code null}.
   */
  public void reset() {
    this.selectedVertex = null;
    this.vertices.clear();
  }

  /**
   * Selects the vertex closest to the specified point.
   * 
   * @param x
   *          The x value of the point used to select the nearest vertex.
   * @param y
   *          The y value of the point used to select the nearest vertex.
   */
  public void selectVert(final int x, final int y) {
    if (this.vertices.isEmpty())
      return;
    final Point c = this.vertices.get(0);
    float dx = x - c.x;
    float dy = y - c.y;
    float winning_dist_squared = dx * dx + dy * dy;
    Point winner = c;
    for (final Point vertex : this.vertices) {
      dx = x - vertex.x;
      dy = y - vertex.y;
      float dist_squared = dx * dx + dy * dy;
      if (dist_squared < winning_dist_squared) {
        winner = vertex;
        winning_dist_squared = dist_squared;
      }
    }
    this.selectedVertex = winner;
  }

  /**
   * Returns the String representation of this polygon.
   * 
   * @return The String representation of this polygon.
   */
  @Override
  public String toString() {
    String result = "Polygon[";
    for (final Point vertex : this.vertices) {
      result += vertex + ",";
    }
    result = result.substring(0, result.length() - 1);
    result += "]";
    return result;
  }

  /**
   * Returns the list of vertices which comprise this polygon.
   * 
   * @return The list of vertices which comprise this polygon.
   */
  List<Point> vertices() {
    return this.vertices;
  }

public BoundingBox2D getBbox() {
	return bbox;
}

/**
 * compute the the bounding box of the polygon
 */
public void setBbox() {
	float minX, minY, maxX, maxY;
	int i;
	int num = this.vertices.size();
	
	if(num==0)
		return;
	
	// test against MBR first
	minX=maxX=this.vertices.get(0).x;
	minY=maxY=this.vertices.get(0).y;
	for(i=1;i<num;++i){
		if(minX>this.vertices.get(i).x)
			minX=this.vertices.get(i).x;
		else if(maxX<this.vertices.get(i).x)
			maxX=this.vertices.get(i).x;
		if(minY>this.vertices.get(i).y)
			minY=this.vertices.get(i).y;
		else if(maxY<this.vertices.get(i).y)
			maxY=this.vertices.get(i).y;
	}
	this.bbox.set(minX, minY, maxX, maxY);
}

}
