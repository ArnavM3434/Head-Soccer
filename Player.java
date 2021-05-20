/*  This is an extenstion of Block which adds functionality for the block to
    'wiggleSize' or move back and forth in a set pattern ehwn you call the move method
*/

import java.awt.geom.Rectangle2D;
public class Player{

private double x, y, w, h;
private Rectangle2D.Double rectangle;

	public Player(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		rectangle = new Rectangle2D.Double(x,y,w,h);
	}


	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
		rectangle = new Rectangle2D.Double(x,y,w,h);
	}

	public Rectangle2D.Double getPlayer(){
		return rectangle;
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