import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

public class Health {
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	private static final int HEALTH_WIDTH = 20, HEALTH_HEIGHT = 20;
	private final int DISAPPEAR_TIME = 20000;
	private Point health_coordinates;
	private final int VALUE = 3;
	private Image health_image;
	private GamePlay gameplay;
	private boolean finished;
	
	public Health(GamePlay game){
		this.health_coordinates = getRandomCoordinates();
		this.gameplay = game;
		setImage("heart.png");
		finished = false;
		setTimer(DISAPPEAR_TIME);
	}
	
	public void drawHealth(Graphics g){
		g.drawImage(health_image, health_coordinates.x, health_coordinates.y, HEALTH_WIDTH, HEALTH_HEIGHT, gameplay);
	}
	public void explode(){
		
	}
	
	public Point getRandomCoordinates(){
		
		Random rand = new Random();
		int  x = rand.nextInt(DIMENSION_X - 50) + 1;
		int  y = rand.nextInt(DIMENSION_Y - 18) + 1;
		Point a = new Point(x, y);
		return a;
	}
	
	public void setImage(String name){
		health_image = null;
		try {
			URL imagePath = GamePlay.class.getResource(name);
			health_image = Toolkit.getDefaultToolkit().getImage(imagePath);
		} catch (Exception e) {
			// image not exist
			e.printStackTrace();
		}
	}
	
	public Image getImage(){
		return this.health_image;
	}
	
	public void setNullImage(){
		this.health_image = null;
	}
	
	public void setTimer(int time){
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                //setNullImage();
		            	finished = true;
		            	gameplay.eraseFinishedHealths();
		            	gameplay.repaint();
		            }
		        }, 
		        time 
		);
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	
	public Point getPosition(){
		return health_coordinates;
	}
	
	public int getValue(){
		return VALUE;
	}
	
	public static int getHeight(){
		return HEALTH_HEIGHT;
	}
	
	public static int getWidth(){
		return HEALTH_WIDTH;
	}

}
