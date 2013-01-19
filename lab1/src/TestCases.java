/**
 * TestCases.java
 */


import java.util.Iterator;

/**
 * An iterator over polygon test cases.
 * 
 * @author Jianming Zhang
 * @author Jeffrey Finkelstein <jeffreyf>
 * @author Stan Sclaroff <sclaroff>
 * @since Spring 2011
 * 
 */
public class TestCases implements Iterator<Shape[]> {

  /** The total number of test cases. */
  public static final int NUM_TEST_CASES = 5;

  /** The current test case. */
  private int currentTestCase = 0;

  /**
   * Always returns {@code true}.
   * 
   * @return {@code true}
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return true;
  }

  /**
   * Gets a new polygon which is the next test case.
   * 
   * Cycles through {@value #NUM_TEST_CASES} test cases.
   * 
   * @return A new polygon which is the next test case.
   * @see java.util.Iterator#next()
   */
  @Override
  public Shape[] next() {
    final Shape[] shapes = new Shape[2];
    Polygon polygon = new Polygon();
    Circle circle = new Circle();

    switch (this.currentTestCase) {
    case 0:
    	polygon.setRGBColor((float)0.0, (float)1.0, (float)0.8);
    	polygon.addVert(50, 200);
    	polygon.addVert(200, 30);
    	polygon.addVert(300, 300);
    	polygon.addVert(50, 300);
    	polygon.addVert(200, 200);
    	polygon.addVert(100, 20);
    	polygon.addVert(100, 350);
    	
    	circle.setRGBColor((float)1.0, (float)0.0, (float)0.2);
    	circle.setCenterPoint((float) 150, (float) 170);
    	circle.setRadius((float) 20);
    	break;
    	
    case 1:
    	polygon.setRGBColor((float)1.0, (float)0.0, (float)0.2);
    	polygon.addVert(60, 60);
    	polygon.addVert(60, 110);
    	polygon.addVert(110, 110);
    	polygon.addVert(110, 60);
    	
    	circle.setRGBColor((float)0.0, (float)1.0, (float)0.8);
    	circle.setCenterPoint((float) 150, (float) 150);
    	circle.setRadius((float) 100);
    	break;

    case 2:
    	polygon.setRGBColor((float)0.1, (float)0.5, (float)1.0);
    	polygon.addVert(50, 250);
    	polygon.addVert(50, 150);
    	polygon.addVert(100, 150);
    	polygon.addVert(150, 50);
    	polygon.addVert(200, 200);
    	polygon.addVert(250, 50);
    	polygon.addVert(275, 150);
    	polygon.addVert(275, 250);
    	
    	circle.setRGBColor((float)0.9, (float)0.5, (float)0.0);
    	circle.setCenterPoint((float) 240, (float) 325);
    	circle.setRadius((float) 25);
    	break;    	

    case 3:
      	polygon.setRGBColor((float)0.1, (float)1.0, (float)1.0);polygon.addVert(50, 50);
      	polygon.addVert(50, 200);
      	polygon.addVert(75, 200);
      	polygon.addVert(75, 100);
      	polygon.addVert(100, 100);
      	polygon.addVert(100, 200);
      	polygon.addVert(125, 200);
      	polygon.addVert(125, 100);
      	polygon.addVert(150, 100);
      	polygon.addVert(150, 200);
      	polygon.addVert(175, 200);
      	polygon.addVert(175, 100);
      	polygon.addVert(275, 100);
      	polygon.addVert(275, 5);
      	polygon.addVert(250, 5);
      	polygon.addVert(250, 50);
      	polygon.addVert(225, 50);
      	polygon.addVert(225, 5);
      	polygon.addVert(200, 5);
      	polygon.addVert(200, 50);
      	polygon.addVert(175, 50);
      	polygon.addVert(175, 5);
      	polygon.addVert(150, 5);
      	polygon.addVert(150, 50);
      	
      	circle.setRGBColor((float)0.9, (float)0.0, (float)0.0);
    	circle.setCenterPoint((float) 200, (float) 180);
    	circle.setRadius((float) 80);
      	break;

    case 4:
      	polygon.setRGBColor((float)0.8, (float)0.8, (float)0.2);
      	polygon.addVert(39, 272);
    	polygon.addVert(44, 215);
    	polygon.addVert(83, 215);
    	polygon.addVert(83, 163);
    	polygon.addVert(95, 163);
    	polygon.addVert(95, 240);
    	polygon.addVert(130, 240);
    	polygon.addVert(130, 168);
    	polygon.addVert(145, 168);
    	polygon.addVert(145, 245);
    	polygon.addVert(191, 245);
    	polygon.addVert(191, 140);
    	polygon.addVert(270, 110);
    	polygon.addVert(269, 281);	
    	
    	circle.setRGBColor((float)0.2, (float)0.20, (float)0.82);
    	circle.setCenterPoint((float) 290, (float) 180);
    	circle.setRadius((float) 50);
      break;
    }

    this.currentTestCase = (this.currentTestCase + 1) % NUM_TEST_CASES;

    shapes[0] = polygon;
    shapes[1] = circle;
    return shapes;
  }

  /**
   * Always throws a {@link UnsupportedOperationException}.
   * 
   * @throws UnsupportedOperationException
   *           Always throws this exception.
   * @see java.util.Iterator#remove()
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}