import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;


public class Unbreakable extends Obstacles{

	private final static int TYPE = 0;
	
    //constructor
	public Unbreakable(GamePlay gamePlay, Point position){
		super (gamePlay, TYPE, position);
	}
	
	//implementing explode
	public void explode (){
		//do nothing
	}
	
	// drawing Unbreakable obstacle
	public void drawObstacle(Graphics g){
        Image hal = null;
        try
        {
            URL imagePath = GamePlay.class.getResource("non-breakable.jpg");
            hal= Toolkit.getDefaultToolkit().getImage(imagePath);
        }
        catch (Exception e)
        {
            //image not exist
            e.printStackTrace();
        }
        //GamePlay gamePlay= super.getGamePlay(); 
    	g.drawImage(hal, position.x , position.y , BOX_WIDTH, BOX_HEIGHT, gamePlay);
    }
	
	public void animation(){
		
	}
}