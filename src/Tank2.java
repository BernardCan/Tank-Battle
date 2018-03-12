import java.awt.Graphics;
import java.awt.*;
import java.net.*;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class Tank2 extends Tank{
	private final static Point defaultPosition =  new Point(970, 700);
	private final static int defaultDirection = LEFT;
	private LifeBar lifeBar;
	
	Tank2(GamePlay gamePlay){
		super(gamePlay, defaultPosition, defaultDirection);
		lifeBar = new LifeBar(gamePlay, new Point(790, 0), 2);
		lifeBar.setMineNum(mineAmount);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (!pressedKeys.contains(new Integer(e.getKeyCode())))
			pressedKeys.add(new Integer(e.getKeyCode()));
		for (int i = 0; i < pressedKeys.size(); i++){
			switch(pressedKeys.get(i).intValue()){
			case KeyEvent.VK_UP:
				direction = UP;
				timer.start();
				break;
			case KeyEvent.VK_DOWN:
				direction = DOWN;
				timer.start();
				break;
			case KeyEvent.VK_RIGHT:
				direction = RIGHT;
				timer.start();
				break;
			case KeyEvent.VK_LEFT:
				direction = LEFT;
				timer.start();
				break;
			case KeyEvent.VK_ENTER:
				shoot();
				gamePlay.repaint();
				break;
			case KeyEvent.VK_INSERT:
				createMine();
				gamePlay.repaint();
				break;
			default: break;
			}
		}		
		
	}
	public void keyReleased(KeyEvent e){
		pressedKeys.remove(new Integer(e.getKeyCode()));
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			timer.stop();
			break;
		case KeyEvent.VK_DOWN:
			timer.stop();
			break;
		case KeyEvent.VK_RIGHT:
			timer.stop();
			break;
		case KeyEvent.VK_LEFT:
			timer.stop();
			break;
		default: break;
		}	
	}
	
	//draw lifeBar
	//changed!
	public void drawLifeBar(Graphics g){
		lifeBar.setMineNum(mineAmount);
		lifeBar.setSoundSelected(soundSelected);
		lifeBar.drawBar(g);
	}
	
	//update life
	public void updateLife(int value){
		lifeAmount = Math.min(lifeAmount + value, FULL_LIFE_AMOUNT);
		
		if(value < 0)
			lifeBar.decreaseLife(value);
		else
			lifeBar.increseLife();
	}
	
	//draw tank
	public void drawTank(Graphics g){
		Image tankImage = null, flameImage = null, exploadImage = null;
		URL tankImageLink = null, flameLink = GamePlay.class.getResource("flame1.gif"), exploadLink = GamePlay.class.getResource("mineExplosion.gif");
		switch (direction){
		case LEFT:
			tankImageLink = GamePlay.class.getResource("tank2left.png");
			break;
		case RIGHT:
			tankImageLink = GamePlay.class.getResource("tank2right.png");
			break;
		case UP:
			tankImageLink = GamePlay.class.getResource("tank2up.png");
			break;
		case DOWN:
			tankImageLink = GamePlay.class.getResource("tank2down.png");
			break;
		}
		tankImage = Toolkit.getDefaultToolkit().getImage(tankImageLink);
		flameImage = Toolkit.getDefaultToolkit().getImage(flameLink);
		exploadImage = Toolkit.getDefaultToolkit().getImage(exploadLink);
		g.drawImage(tankImage, position.x, position.y, TANK_WIDTH, TANK_HEIGHT, gamePlay);
		if (almostDead())
			g.drawImage(flameImage, position.x+5, position.y, FLAME_WIDTH, FLAME_HEIGHT, gamePlay);
		if (exploded)
			g.drawImage(exploadImage, position.x, position.y, EXPLOSION_WIDTH, EXPLOSION_HEIGHT, gamePlay);
		//g.drawImage(tankImage, position.x, position.y, TANK_WIDTH, TANK_HEIGHT, gamePlay);
	}
	
}
