import javax.swing.*;   // Import everything from swing package
import java.awt.*;		// Import everything from awt package
import java.awt.event.*; // for key listener
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;


public class NewGame extends JPanel implements KeyListener, Runnable  // Implementing KeyListener here allows you to react to key presses
{
	private static final int frameWidth = 1000;  // Set Dimension of full frame
	private static final int frameHeight = 800;
	private boolean gameOn, win,left, right, up, down, jump, left2, right2, jump2;
	private String msg;
	private int x1, y1; // starting coordinates of ball
	private int x2, y2; // starting coordinates of first player
	private int x3, y3; //starting coordinates of second player
	private Rectangle2D.Double goal1, goal2; //the two goals
	private Rectangle2D.Double goaltop1, goaltop2;  //the posts of the two goals
	private static  double gravity = 0.5;
	private double y1_velocity = 0; //y velocity of ball
	private double x1_velocity = 0; //x velocity of ball
	private double y2_velocity = 0; //y velocity of player1
	private double y3_velocity = 0; //y velocity of player 2
	private JFrame frame;
	private Font f;
	private Color color;
	private Thread t;
	private Ball ball1; // Uses new object created for this version
	private Player player1;
	private Player player2;
	private boolean inAir1 = false;  //used to tell if player1 is jumping
	private boolean inAir2 = false; //used to tell if player2 is jumping
	private boolean kick = false; //used to tell if player1 kicks
	private boolean kick2 = false; //used to tell if player2 kicks
	private int score1 = 0;  //points for player 1
	private int score2 = 0;   //points for player 2
	int waittime = 8;  //how much time between each repaint - this number is going to vary



	public NewGame()
	{
		x1 = 500;
		y1 = 40;

		x2 = 250;
		y2 = 645;

		x3 = 800;
		y3 = 645;

		win = false;
		gameOn = false;


		// Default not moving
		right = false; /*************  Key Listener Related declarations   */
		left = false;
		up = false;
		down = false;
		jump = false;
		left2 = false;
		right2 = false;
		jump2 = false;

		frame=new JFrame();
		f=new Font("TIMES NEW ROMAN",Font.BOLD,32);  // Set Font  https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html
		frame.add(this);  //Add Object to Frame, this will invoke the paintComponent method
		frame.setSize(frameWidth,frameHeight);
		frame.setVisible(true);
		frame.addKeyListener(this); /***********  NEEDED TO USE KEYLISTENER ******/   //wait what exactly is "this"?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




		ball1 = new Ball(x1, y1, 25, 25);  // Set the current position for ball1. So I guess all it does is set the starting position
		player1 = new Player(x2, y2, 25,50);
		player2 = new Player(x3, y3, 25,50);
		goal1 = new Rectangle2D.Double(0, 600, 15, 100);
		goal2 = new Rectangle2D.Double(969, 600, 15, 100);
		goaltop1 = new Rectangle2D.Double(0, 601, 15, 2);
		goaltop2 = new Rectangle2D.Double(969, 601, 15, 2);

		msg = score1 + "                        SCORE                          " + score2;


		t=new Thread(this);  // The thread  - what exatly does "this" mean?
		t.start();
	}


	public void paintComponent(Graphics g)  // Need to have this method to complete the painting
	{
		super.paintComponent(g);  // Need to call this first to initialize in parent class, do not change
		Graphics2D g2d = (Graphics2D)g;  // Cast to Graphics2D which is a subclass of Graphics with additional properties

		/*  Fill background with a gray rectangle the size of the entire frame */
		g2d.setPaint(Color.GREEN);
		g2d.fillRect(0,0,frameWidth,frameHeight);

		g2d.setColor(Color.BLUE);
		g2d.setFont(f);  // NOTE: Font initialized in the constructor
		g2d.drawString(msg,275,30);  // Write text from msg String







		g2d.setColor(Color.WHITE);  // Another way to set color.   Parameters are red, green, blu on a scale of 0-255
		g2d.fill(ball1.getBall());

		g2d.setColor(Color.BLACK);
		g2d.fill(player1.getPlayer());
		g2d.fill(player2.getPlayer());

		g2d.setColor(Color.GRAY);
		g2d.fill(goal1);
		g2d.fill(goal2);

		g2d.setColor(Color.WHITE);
		g2d.fill(goaltop1);
		g2d.fill(goaltop2);


	}


		/*   This method does the math and creates the pause to create animation */
		public void run()
		{

		while(!gameOn){ // Wait here until any key press kicks of the game

			try
			{
				t.sleep(5);
			}catch(InterruptedException e){}
			repaint();

			}

			while(gameOn &&!win)  // Keep going until win or window is closed
			{

				if(ball1.getY() >= 669)   //if ball has touched the ground(at 669), it has an upward speed slightly less than what it came down with, so that eventually it stops bouncing
				{
					y1_velocity = (y1_velocity) * -0.9;
					ball1.setY(669);
				}



				y1_velocity += gravity;  //if ball is going down, it's speed increases, and if going up, its speed decreases

				if(ball1.getY() >= 669 && Math.abs(y1_velocity) < 0.5) //if it has bounced for a while and its speed is so small that it has basically stopped bouncing, set the speed to 0
				{
					y1_velocity = 0;
					ball1.setY(669);
				}

				ball1.changeY(y1_velocity);






				//player1 controls

				if (jump && player1.getY() == 645)  //if player1 has pressed up arrow and they are on the ground
				{
				inAir1 = true;
				y2_velocity = -15;
				}

				if(inAir1)
				{



						y2_velocity += gravity; //they then begin to slow down while going up

						player1.changeY(y2_velocity);
			//			player1.setPos(player1.getX(), player1.getY());
						if(player1.getY() >= 645)
						{
						player1.setY(645); //if they have reached the ground, inAir1 is false, so this loop won't execute anymore
						inAir1 = false;
						}
				}

				if(right && player1.getX() <= 942) //they can only move if they are within the boundaries
				player1.changeX(2);
				if(left && player1.getX() >= 16)
				player1.changeX(-2);
				player1.setPos(player1.getX(), player1.getY()); //changes the player1 object's coordinates for a new repaint


				//player 2 controls, almost the same as player1 controls


				if (jump2 && player2.getY() == 645)
				{
				inAir2 = true;
				y3_velocity = -15;
				}

				if(inAir2)
				{



						y3_velocity += gravity;

						player2.changeY(y3_velocity);
						if(player2.getY() >= 645)
						{
						player2.setY(645);
						inAir2 = false;
						}
				}

				if(right2 && player2.getX() <= 942)
				player2.changeX(2);
				if(left2 && player2.getX() >= 16)
				player2.changeX(-2);
				player2.setPos(player2.getX(), player2.getY());



				//collision detection between players and the ball

				if(   ball1.getBall().intersects(    player1.getPlayer()     )       )  //if player1 and ball collide...
				{
					if(ball1.getX() < player1.getX() && x1_velocity <= 0)   //if ball is behind player 1 and moving left, it moves left faster
					x1_velocity -=1;
					else if(ball1.getX() > player1.getX() && x1_velocity >= 0) //if ball is in front of player 1 and moving right, it moves right faster
					x1_velocity += 1;
					else
					{
						x1_velocity *= -1;   //if the ball just comes at player1, in the opposite direction player1 moves in, its x velocity just gets reversed
					}
				}





				if(        ball1.getBall().intersects(    player2.getPlayer()     )                ) //if player 2 and ball collide
								{
									if(ball1.getX() < player2.getX() && x1_velocity <= 0)
									x1_velocity -=1;
									else if(ball1.getX() > player2.getX() && x1_velocity >= 0)//basically same thing as above, I split it into two if structures to avoid cluttering
									x1_velocity += 1;
									else
									{
										x1_velocity *= -1;
									}
								}


								//kicking

				if( ball1.getBall().intersects( player1.getPlayer() ) && kick) //if player1 intersects ball and presses kick, the ball gets a random upward velocity
					y1_velocity = (int)(Math.random() * 10) + 15;

				if(ball1.getBall().intersects(player2.getPlayer() )&& kick2 ) //if player2 intersects ball and presses their kick key, the ball gets a random upward veloicty
					y1_velocity = (int)(Math.random() * 10) + 15;



					if(ball1.getX() > 950 || ball1.getX() < 14) //if ball hits boundaries its x direction reverses
					x1_velocity *= -1;


					ball1.changeX(x1_velocity);   //this method changes the x coordinate of the ball based on x_velocity, which in turn is based on all the stuff above

					if(Math.abs(x1_velocity) > 7) //if ball gets too fast it starts to slow down a little
					x1_velocity *= 0.99;

					//goal collision detection

					if((ball1.getBall().intersects(goal1)) && !(ball1.getBall().intersects(goaltop1)) && !(ball1.getBall().intersects(goaltop2)) )
					{
						//if the ball hits the goal and it doesn't hit the posts...
						score2++; //player2 gets a point
						ball1.setPos(x1, y1); //ball returns to position at beginning of game
						player1.setPos(x2, y2); //players returns to starting position
						player2.setPos(x3, y3);
						waittime = 1000; //there will now be a slight delay before the next point happens
						x1_velocity = 0; //ball's speed goes back to 0
						y1_velocity = 0;

					}
					else waittime = 8;
					if((ball1.getBall().intersects(goal2)) && !(ball1.getBall().intersects(goaltop1)) && !(ball1.getBall().intersects(goaltop2)) )
					{
						score1++;
						ball1.setPos(x1, y1);
						player1.setPos(x2, y2);
						player2.setPos(x3, y3);
						waittime = 1000;
						x1_velocity = 0;
						y1_velocity = 0;

					}
					else waittime = 8;


					if((ball1.getBall().intersects(goaltop1)) || (ball1.getBall().intersects(goaltop2))) //if ball hits the top of the goal its y velocity reverses
					y1_velocity *= -1;


					msg =  score1 + "                        SCORE                          " + score2;

					if(score1 == 3)
					{
					msg = "           Player 1 wins!";
					win = true;
					}
					else if (score2 == 3)
					{
					msg = "          Player 2 wins!";
					win = true;
					}


					ball1.setPos(ball1.getX(), ball1.getY()); //very important method - changes the x and y coordinates of the ball1 object, which then allows for a new repaint












				try
				{
					t.sleep(waittime);  //play with this number
				}catch(InterruptedException e)
				{
				}
				repaint(); //so this invokes the paintComponent method again
			}
	}




	/*  Key Listener Methods Below
	https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyListener.html
	*/
	public void keyPressed(KeyEvent ke)
	{
		gameOn = true;
		if(ke.getKeyCode()==87)
			jump=true;
		if(ke.getKeyCode()==68)
			right=true;
		if(ke.getKeyCode()==65)
			left=true;
		if(ke.getKeyCode() == 32)
			kick = true;
		if(ke.getKeyCode()==38)
			jump2=true;
		if(ke.getKeyCode()==39)
			right2=true;
		if(ke.getKeyCode()==37)
			left2=true;
		if(ke.getKeyCode()==40)
			kick2=true;
	}
	public void keyReleased(KeyEvent ke)
	{
		if(ke.getKeyCode()==87)
			jump=false;
		if(ke.getKeyCode()==68)
			right=false;
		if(ke.getKeyCode()==65)
			left=false;
		if(ke.getKeyCode() == 32)
			kick = false;
		if(ke.getKeyCode()==38)
			jump2=false;
		if(ke.getKeyCode()==39)
			right2=false;
		if(ke.getKeyCode()==37)
			left2=false;
		if(ke.getKeyCode()==40)
			kick2=false;

	}


	public void keyTyped(KeyEvent ke)
	{
	//	 This method is included because it is required by the KeyListener Interface
	//	 it is not used for basic movement but can be used to get actual input
	//	 show the keyboard such as typing name.

	}


	public static void main(String args[])
	{
		NewGame app = new NewGame();
	}
}