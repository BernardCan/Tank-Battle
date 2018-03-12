import java.awt.Graphics;
import java.awt.Point;

public abstract class Obstacles {
	   
	//attributes
	protected Point position;
	protected int type;
	protected GamePlay gamePlay;
	protected static final int BOX_HEIGHT = 27;
	protected static final int BOX_WIDTH = 27;
    
    //constructor
    
    public Obstacles (GamePlay gamePlay,int type, Point position) {
    	this.gamePlay = gamePlay;
    	this.position = position;
    	this.type = type;
    }
    
	
	//methods
	
    public abstract void explode();
    
    public abstract void drawObstacle(Graphics g);
    
    public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public GamePlay getGamePlay(){
		return gamePlay;
	}
	
	public static int getHeight(){
		return BOX_HEIGHT;
	}
	
	public static int getWidth(){
		return BOX_WIDTH;
	}
		
}
