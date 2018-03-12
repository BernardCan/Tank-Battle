import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

public class Transparent extends Obstacles{
	
	private static final int TYPE = 2;
	
	Transparent(GamePlay game, Point position){
		super(game, TYPE, position);
        
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawObstacle(Graphics g) {
		
		Image hal = null;
        try
        {
            URL imagePath = GamePlay.class.getResource("grass.png");
            hal= Toolkit.getDefaultToolkit().getImage(imagePath);
        }
        catch (Exception e)
        {
            //image not exist
            e.printStackTrace();
        }
		GamePlay gamePlay= super.getGamePlay(); 
    	g.drawImage(hal, position.x , position.y , BOX_WIDTH, BOX_HEIGHT, gamePlay);
		
	}
	
}
