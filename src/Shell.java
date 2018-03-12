import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;


public class Shell{
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	private final static int SHELL_HEIGHT = 10;
	private final static int SHELL_WIDTH = 6;
	private final static int LEFT = 0;
	private final static int RIGHT = 1;
	private final static int UP = 2;
	private final static int DOWN = 3;
	private final int SPEED = 10;
	private final int VALUE = 1;
	
	private Point position;
	private int direction;
	private GamePlay gamePlay;
	private boolean reachedBorders;
	
	Shell(GamePlay game, Point position, int direction){
		this.position = position;
		this.direction = direction;
		gamePlay = game;
		reachedBorders = false;
	}
	
	public void drawShell(Graphics g){
		Image shellImage = null;
		URL shellImageLink = null;
		switch (direction){
		case LEFT:
			shellImageLink = GamePlay.class.getResource("shellLeft.png");
			break;
		case RIGHT:
			shellImageLink = GamePlay.class.getResource("shellRight.png");
			break;
		case UP:
			shellImageLink = GamePlay.class.getResource("shellUp.png");
			break;
		case DOWN:
			shellImageLink = GamePlay.class.getResource("shellDown.png");
			break;
		}
		shellImage = Toolkit.getDefaultToolkit().getImage(shellImageLink);
		g.drawImage(shellImage, position.x, position.y, SHELL_WIDTH, SHELL_HEIGHT, gamePlay);
		
		updatePosition();
	}
	
	private void updatePosition(){
		switch (direction){
		case LEFT:
			if (position.x > 0)
				position.x-=SPEED;
			else 
				reachedBorders = true;
			gamePlay.repaint();
			break;
		case RIGHT:
			if (position.x < DIMENSION_X - 30)
				position.x+=SPEED;
			else 
				reachedBorders = true;
			gamePlay.repaint();
			break;
		case UP:
			if (position.y > 0)
				position.y-=SPEED;
			else 
				reachedBorders = true;
			gamePlay.repaint();
			break;
		case DOWN:
			if (position.y < DIMENSION_Y - 18)
				position.y+=SPEED;
			else 
				reachedBorders = true;
			gamePlay.repaint();
			break;
		}
	}
	
	public boolean reachedBorders(){
		return reachedBorders;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public int getValue(){
		return VALUE;
	}
	
	public static int getHeight(){
		return SHELL_HEIGHT;
	}
	
	public static int getWidth(){
		return SHELL_WIDTH;
	}

}
