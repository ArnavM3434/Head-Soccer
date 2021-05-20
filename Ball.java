/*  This is an extenstion of Block which adds functionality for the block to
    'wiggleSize' or move back and forth in a set pattern ehwn you call the move method
*/

import java.awt.geom.Ellipse2D;
public class Ball{

private double x, y, w, h;
private Ellipse2D.Double ball;

	public Ball(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		ball = new Ellipse2D.Double(x,y,w,h);
	}


	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
		ball = new Ellipse2D.Double(x,y,w,h);
	}

	public Ellipse2D.Double getBall(){
		return ball;
	}



	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}
	public void changeX(double x){
		this.x += x;
	}
	public void changeY(double y){
		this.y += y;
	}
	public void setY(double y)
	{
		this.y= y;
	}






}