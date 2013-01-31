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
import java.util.Random;

/**
 * A polygon with additional drawing capabilities.
 *
 * @author Stan Sclaroff <sclaroff>
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffreyf>
 * @author Jianming Zhang
 */
public class Polygon extends Shape
{

    /** The currently selected vertex, which will be moved. */
    private Point selectedVertex = null;
    /** The list of vertices that make up this polygon. */
    private final ArrayList<Point> vertices = new ArrayList<Point>();
    /** The polygon's bounding box */
    private BoundingBox2D bbox = new BoundingBox2D();
    /** Tolerance for 0 */
    private float TOL = 0.0005f;
    /** An list of lists of vertices for holes */
    private final ArrayList<ArrayList<Point>> holes = new ArrayList<ArrayList<Point>>();

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
     * @param p1	The start of line segment
     * @param p2	The end of line segment
     * @param c1	The center of circle
     * @param radius	The radius of the circle
     * @return {@code true} if and only if the circle is intersecting the polygon
     */
    public boolean circleTouching(Point p1, Point p2, Point c1, float radius)
    {
    	Point closest;
    	Point edgeVec = new Point(p2.x - p1.x, p2.y - p1.y); /* Vector representing edge p1-p2 */
    	Point toCenter = new Point(c1.x - p1.x, c1.y - p1.y); /* Vector from p1 to circle center */
    	
    	float magnitude = (float)Math.sqrt( Math.pow((double)edgeVec.x, 2) + 
    			Math.pow((double)edgeVec.y, 2) );
    	Point unitVector = new Point(edgeVec.x / magnitude, edgeVec.y / magnitude);
    	
    	/* Project of vector to center in the direction of edge (dot product) */
    	float projection = toCenter.x * unitVector.x + toCenter.y * unitVector.y;
    	
    	/* Now, edge cases.  If projection is greater than magnitude of edgeVec, it is past the
    	 * end of the vector, if it is < 0, it is before the vector.  In this case, the closest
    	 * point is the vertices themselves.
    	 */
    	if (projection <= 1e-005)
    	{
    		closest = p1;
    	}
    	else if (projection >= magnitude-1e-005)
    	{
    		closest = p2;
    	}
    	else
    	{
    		Point projectionVector = new Point(unitVector.x * projection, unitVector.y * projection);
    		closest = new Point(p1.x + projectionVector.x, p1.y + projectionVector.y);
    	}
    	
    	/* Now we have the closest point, so we take this closest point, and get the distance to the center */
    	float xDist = c1.x - closest.x;
    	float yDist = c1.y - closest.y;
    	float distance = (float) Math.sqrt(Math.pow((double)xDist, 2) + Math.pow((double)yDist, 2));
    	if (distance <= radius)
    		return true;
    	else 
    		return false;
    }

    /**
     *
     * @return {@code true} if and only if polygon vertices are counterclockwise
     */
    public boolean isClockwise()
    {
        /* This test uses a simplified version of green's theorem to get the signed area
         * if the result is positive, curve is clockwise, else it is counter clockwise, I got the idea from:
         * http://stackoverflow.com/questions/1165647/how-to-determine-if-a-list-of-polygon-points-are-in-clockwise-order
         */
        float sum = 0;
        Point p1, p2;
        int size = this.vertices.size();
        
        if (size == 0)
        	return false;
        
        for ( int ii = 0 ; ii < (size - 1) ; ii++ )
        {
            p1 = this.vertices.get(ii);
            p2 = this.vertices.get(ii + 1);
            sum += (p2.x - p1.x) * (p2.y + p1.y);
        }
        /* Add in the final edge */
        sum += (this.vertices.get(0).x - this.vertices.get(size - 1).x) * (this.vertices.get(0).y + this.vertices.get(size - 1).y);

        /* Since the opengl coordinate system starts from top left, with +y going down, I invert the if statement to be consistent with
         * the way the polygons will appear to the user (a typical counterclockwise in standard coordinates will look clockwise here)
         */

        if (sum < 0)
            return false;
        else
            return true;
    }

    /**
     * @return {@code true} if and only if the two line segments intersect
     */
    public boolean segmentIntersect(Point p, float dpx, float dpy, Point q, float dqx, float dqy)
    {
        /* Uses parametric line equation from the homework notes
         * (xp - xl) * dyl - (yp - yl) * dxl = (dyp*dxl - dxp*dyl) t
         * t is the point of intersection on the p ray, then check s as well
         */
    	float a = (p.x - q.x) * dqy - (p.y - q.y) * dqx; 
    	float b = dpy * dqx - dpx * dqy;
    	float t = a / b;
    	/* I use greater than 0 simply because i do not want a vertex to count as an intersect */
    	if (t >= -1e-005 && t <= (1 - 1e-005))
    	{
    		float s;
    		/* Must account for vertical or horizontal line segments */
    		if (dqx != 0)
    		{
    			s = ( ( p.x - q.x) + dpx * t ) / dqx;
    		}
    		else
    		{
    			s = ( ( p.y - q.y) + dpy * t) / dqy;
    		}
    		
    		if (s > 0 && s < 1)
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    		
    	}
		else
		{
			return false;
		}
    }

    /**
     * Returns {@code true} if and only if this polygon overlaps Circle c.
     *
     * @param c
     *          The Circle to be tested for overlap
     *
     * @return {@code true} if and only if this polygon overlaps c.
     */

    public boolean isOverlapping(Circle c)
    {
    	/* First checks if center is in the polygon */
    	if (isInside(c.getCenterPoint().x, c.getCenterPoint().y))
    		return true;
    	
    	/* Then check if circle is just outside the bounding box */
    	float boundLeft = c.getCenterPoint().x - c.getRadius();
    	float boundRight = c.getCenterPoint().x + c.getRadius();
    	float boundNorth = c.getCenterPoint().y - c.getRadius();
    	float boundSouth = c.getCenterPoint().y + c.getRadius();
    	
    	/* Check if circle is even close enough */
    	if (boundRight < this.bbox.getMinX() || boundLeft > this.bbox.getMaxX())
    		return false;
    	if (boundSouth < this.bbox.getMinY() || boundNorth > this.bbox.getMaxY())
    		return false;
    	
        /* Finds the closest point on line segment for each circle, then checks if it is in radius */
    	boolean overlap;
    	int size = vertices.size();
    	for (int ii = 0 ; ii < size ; ii++ )
    	{
    		overlap = circleTouching(vertices.get(ii), vertices.get( (ii + 1) % size), c.getCenterPoint(), c.getRadius());
    		if (overlap)
    			return true;
    	}
        return false;
    }

    /**
     * Returns {@code true} if and only if this polygon is concave.
     *
     * @return {@code true} if and only if this polygon is concave.
     */
    public boolean isConcave()
    {
        /* Calculate by finding sign of z component of cross product.  The sign of this,
         * along with the knowledge of whether the polygon is drawn clockwise or counter-
         * determines the concavity
         */
        Point p1, p2, p3;
        float zCross;
        boolean clockwise = isClockwise();
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
            else if (ii == (size - 2))
            {
                /* Special case, angle that uses mid as last point */
                p1 = vertices.get(ii);
                p2 = vertices.get(ii + 1);
                p3 = vertices.get(0);
            }
            else
            {
                /* Base case */
                p1 = vertices.get(ii);
                p2 = vertices.get(ii + 1);
                p3 = vertices.get(ii + 2);
            }

            /* Get cross product (Sign of this is the sign of the sin(theta) */
            zCross = (p2.x - p1.x) * (p3.y - p2.y) - (p2.y - p1.y) * (p3.x - p2.x);
            if (clockwise)
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
    public boolean isInside(final float x, final float y)
    {
    	if (this.vertices.size() == 0)
    		return false;
    	
        /* First detect if point is even in the bounding box */
        if (x > this.bbox.getMaxX() || x < this.bbox.getMinX() ||
                y > this.bbox.getMaxY() || y < this.bbox.getMinY())
            return false;
        
        boolean isCC = isClockwise();
        Point thePoint = new Point(x,y);
        
        /* Check if the point is one of the vertices */
        for (Point p : this.vertices)
        {
        	if (p.x == thePoint.x && p.y == thePoint.y)
        		return true;
        }

        /* Next pick a point outside the polygon and verify the ray from the out point to this
         * point does not cross any vertices, otherwise select a new line
         */
        Random rand = new Random();
        Point outer = new Point(rand.nextFloat() + 10 + this.bbox.getMaxX() , 
        		rand.nextFloat() + 10 + this.bbox.getMaxY());
        boolean notValid = true;
        /* Use a dot product to check if it goes through a vertex */
        while (notValid)
        {
        	/* Get a new point */
        	outer.x = rand.nextFloat() + 10 + this.bbox.getMaxX();
        	outer.y = rand.nextFloat() + 20 + this.bbox.getMaxY();
        	
        	/* Set up the two vectors */
        	float rayX = outer.x - thePoint.x;
        	float rayY = outer.y - thePoint.y;
        	float testX, testY, intersectTest;
        	
        	for (Point p : this.vertices)
        	{
        		/* Check if any vertex lies on line from point */
        		testX = p.x - thePoint.x;
        		testY = p.y - thePoint.y;
        		float magRay = (float) Math.sqrt( Math.pow((double)rayX, 2) + Math.pow((double)rayY, 2));
        		float magTest = (float) Math.sqrt( Math.pow((double)testX, 2) + Math.pow((double)testY, 2));

        		//System.out.println("The mag multiply for " + p.toString() + " is " + magTest * magRay);
				intersectTest = Math.abs(1 - (Math.abs(rayX * testX + rayY
							* testY)));// / (magRay * magTest)));

				if (intersectTest <= 1e-005)
        		{
        			/* This is on the line */
        			notValid = true;
        			break;
        		}
        		/* No vertex was on line, we have a good point */
        		notValid = false;
        	}
        }
        
        /* Now, use winding rule on the ray */
        int count = 0;
        int size = this.vertices.size();
        boolean intersect = false;
        for (int ii = 0 ; ii < size ; ii++)
        {
        	float dqx = vertices.get((ii + 1) % size).x - vertices.get(ii).x;
        	float dqy = vertices.get((ii + 1) % size).y - vertices.get(ii).y;
        	intersect = segmentIntersect(thePoint, outer.x-thePoint.x, outer.y-thePoint.y, 
        			vertices.get(ii), dqx, dqy);
        	if (intersect)
        	{
        		/* Detect if it is clockwise or counterclockwise */
        		float zDot = (outer.x - thePoint.x) * (dqy) - (outer.y - thePoint.y) * dqx;
        		if (zDot < 0)
        			count++;
        		else
        			count--;
        	}
        }
        
        if (count != 0)
        {
        	return true;
        }
        else
        	return false;
    }

    /**
     * Returns {@code true} if the polygon has self-intersection
     *
     */
    public boolean hasSelfIntersection()
    {
        /*
         * Compares every line segment with every other line segment to detect intersection, uses parametric line equation
         */
        int size = vertices.size();
        boolean intersect = false;;
        Point p1, p2, q1, q2; /* Start and end points of line segments */

        float p; /* The starting point */
        float dpx; /* X slope of p */
        float dpy; /* Y slope of p */

        float q;
        float dqx;
        float dqy;

        for (int ii = 0 ; ii < size ; ii++)
        {
            for (int jj = ii + 1 ; jj < size ; jj++)
            {
                /* Compare line segment */
                p1 = this.vertices.get(ii);
                p2 = this.vertices.get( (ii + 1) % size); /* Mod takes into account the edge case */
                q1 = this.vertices.get(jj);
                q2 = this.vertices.get( (jj + 1) % size); 
                intersect = segmentIntersect(p1, p2.x - p1.x, p2.y - p1.y, q1, q2.x - q1.x, q2.y - q1.y);
                if (intersect)
                	return true;
            }
        }
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
    public void addVert(final int x, final int y)
    {
        this.vertices.add(new Point(x, y));
        setBbox(); /* Set bbox whenver vertec changed */
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
    public void moveVert(final int x, final int y)
    {
        if (this.selectedVertex != null)
        {
            this.selectedVertex.x = x;
            this.selectedVertex.y = y;
        }
        setBbox(); /* Set bbox whenver vertec changed */
    }

    /**
     * Resets this polygon so that its list of vertices is empty and the currently
     * selected vertex is {@code null}.
     */
    public void reset()
    {
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
    public void selectVert(final int x, final int y)
    {
        if (this.vertices.isEmpty())
            return;
        final Point c = this.vertices.get(0);
        float dx = x - c.x;
        float dy = y - c.y;
        float winning_dist_squared = dx * dx + dy * dy;
        Point winner = c;
        for (final Point vertex : this.vertices)
        {
            dx = x - vertex.x;
            dy = y - vertex.y;
            float dist_squared = dx * dx + dy * dy;
            if (dist_squared < winning_dist_squared)
            {
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
    public String toString()
    {
        String result = "Polygon[";
        for (final Point vertex : this.vertices)
        {
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
    List<Point> vertices()
    {
        return this.vertices;
    }

    public BoundingBox2D getBbox()
    {
        return bbox;
    }

    /**
     * compute the the bounding box of the polygon
     */
    public void setBbox()
    {
        float minX, minY, maxX, maxY;
        int i;
        int num = this.vertices.size();

        if (num == 0)
            return;

        // test against MBR first
        minX = maxX = this.vertices.get(0).x;
        minY = maxY = this.vertices.get(0).y;
        for (i = 1; i < num; ++i)
        {
            if (minX > this.vertices.get(i).x)
                minX = this.vertices.get(i).x;
            else if (maxX < this.vertices.get(i).x)
                maxX = this.vertices.get(i).x;
            if (minY > this.vertices.get(i).y)
                minY = this.vertices.get(i).y;
            else if (maxY < this.vertices.get(i).y)
                maxY = this.vertices.get(i).y;
        }
        this.bbox.set(minX, minY, maxX, maxY);
    }

}
