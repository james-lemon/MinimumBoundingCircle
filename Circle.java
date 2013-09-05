
public class Circle {

	private Point center;
	private double r;
	
	public Circle(Point c, double radius)
	{
		center = new Point(c.getX(), c.getY());
		r = radius;
	}
	
	public Point getCenter()
	{
		return center;
	}
	public double getRadius()
	{
		return r;
	}
}
