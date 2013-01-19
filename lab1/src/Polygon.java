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
	  /*
	   * For now this always returns false. You should implement this method.
	   */
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
	  /*
	   * For now, this method always returns false. Remove this line when you
	   * implement your own algorithm.
	   */
	  return false;
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
