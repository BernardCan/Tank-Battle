import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;

public class Breakable extends Obstacles{
	
	//attributes
	private static final int TYPE = 1;
	
	//constructor
	
	public Breakable(GamePlay gamePlay, Point position){
		super (gamePlay, TYPE, position);
	}
	
	public void explode (){
		//do nothing
	}
	
	// drawing Unbreakable obstacle
	
    public void drawObstacle(Graphics g){
	    Image hal = null;
	    try
	    {
	         URL imagePath = GamePlay.class.getResource("breakable.jpg");
	         hal= Toolkit.getDefaultToolkit().getImage(imagePath);
	    }
	    catch (Exception e)
	    {
	       //image not exist
	       e.printStackTrace();
	    }
	    //GamePlay gamePlay= super.getGamePlay(); 
		g.drawImage(hal, position.x, position.y , BOX_WIDTH, BOX_HEIGHT, gamePlay);

    }
		
}
