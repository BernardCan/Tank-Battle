import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GamePlayMine extends Mine {
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	private final int DISAPPEAR_TIME = 7000;
	
	public GamePlayMine(GamePlay game){
		super(game);
		mine_coordinates = getRandomCoordinates();
		setTimer(DISAPPEAR_TIME);
	}

	@Override
	public void drawMine(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getImage(), mine_coordinates.x, mine_coordinates.y, MINE_WIDTH, MINE_HEIGHT, gameplay);
		
	}

	@Override
	public JLabel explode() {
		// TODO Auto-generated method stub
		ImageIcon icon = new ImageIcon(("gif_image.gif"));
		JLabel jlabel = new JLabel(icon);
		return jlabel;
		
	}
	
	public Point getRandomCoordinates(){
		
		Random rand = new Random();
		int  x = rand.nextInt(DIMENSION_X - 50) + 1;
		int  y = rand.nextInt(DIMENSION_Y - 18) + 1;
		Point a = new Point(x, y);
		return a;
	}

}
