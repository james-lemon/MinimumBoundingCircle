import java.util.ArrayList;
import java.util.Random;


/*
 * By James Lemon
 */
public class MinimumBoundingCircle {

	private ArrayList<Point> points;
	
	public MinimumBoundingCircle(ArrayList<Point> p)
	{
		points = (ArrayList<Point>) p.clone();
	}
	
	private static ArrayList<Point> createSampleData()
	{
		ArrayList<Point> p = new ArrayList<Point>();
		
		p.add(new Point(0, 0));
		p.add(new Point(50, 100));
		p.add(new Point(100, 0));
		return p;
	}
	
	public Circle getCircle()
	{
		
		Point center = findCenter();
		double maxdistance = 0;
		for(Point p : points)
		{
			if(getDistance(p, center) > maxdistance)
			{
				maxdistance = getDistance(p, center);
			}
		}
		return new Circle(center, maxdistance);
	}
	
	/*
	 * Algorithm borrowed from stack overflow
	 * http://stackoverflow.com/questions/217578/point-in-polygon-aka-hit-test/2922778#2922778
	 */
	private boolean isPointInside(Point test)
	{
		double[] vertx = new double[points.size()];
		double[] verty = new double[points.size()];
		boolean c = false;
		for(int i = 0; i < points.size(); i++)
		{
			vertx[i] = points.get(i).getX();
			verty[i] = points.get(i).getY();
		}
		double testx = test.getX();
		double testy = test.getY();
		int nvert = points.size();
		int i, j = 0;
		  for (i = 0, j = nvert-1; i < nvert; j = i++) {
		    if ( ((verty[i]>testy) != (verty[j]>testy)) &&
		     (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
		       c = !c;
		  }
		  return c;

	}

	
	
	private boolean isPointInsideCircle(Circle c, Point test)
	{
		if(getDistance(test, c.getCenter()) <= c.getRadius())
		{
			return true;
		}
		return false;
	}
	
	
	private Point findCenter()
	{
		double centerx = 0;
		double centery = 0;
		for(Point p : points)
		{
			centerx = centerx + p.getX();
			centery = centery + p.getY();
		}
		centerx = centerx / points.size();
		centery = centery / points.size();
		return new Point(centerx, centery);
	}


	public double howCircular()
	{
		Random generator = new Random(123);
		Point tmp;
		double counter = 0;
		double numhits = 1000000;
		Circle c = getCircle();
		for(int i = 0; i < numhits; i++)
		{
			tmp = new Point(generator.nextDouble() * 100 , generator.nextDouble() * 100);
			while(!isPointInsideCircle(c, tmp))
			{
				tmp = new Point(generator.nextDouble() * 100, generator.nextDouble() * 100);
			}
			if(isPointInside(tmp))
			{
				counter++;
			}
		}
		
		return counter / numhits;
	}
	private double getDistance(Point a, Point b)
	{
		double dsum = 0;
		if(a.getX() > b.getX())
		{
			dsum = dsum + a.getX() - b.getX();
		}
		else
		{
			dsum = dsum + b.getX() - a.getX();
		}
		if(a.getY() > b.getY())
		{
			dsum = dsum + a.getY() - b.getY();
		}
		else
		{
			dsum = dsum + b.getY() - a.getY();
		}
		return dsum;
	}
	
	public static void main(String args[])
	{
		MinimumBoundingCircle MBC = new MinimumBoundingCircle(createSampleData());
		System.out.println(MBC.howCircular());
	}
}
