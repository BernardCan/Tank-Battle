import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import sun.audio.*;

import java.util.*;

public abstract class Tank implements KeyListener, ActionListener{
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	protected final int TANK_HEIGHT = 25;
	protected final int TANK_WIDTH = 25;
	protected final int FLAME_HEIGHT = 17;
	protected final int FLAME_WIDTH = 17;
	protected final int EXPLOSION_HEIGHT = 27;
	protected final int EXPLOSION_WIDTH = 27;
	
	protected Timer timer;
	
	protected final static int LEFT = 0;
	protected final static int RIGHT = 1;
	protected final static int UP = 2;
	protected final static int DOWN = 3;
	protected final int SPEED = 5;
	protected final int FULL_LIFE_AMOUNT = 7;
	protected final int MAX_NUM_MINES = 3;
	
	protected boolean exploded; 
	protected GamePlay gamePlay;
	protected Point position;
	protected int direction;
	protected int lifeAmount;
	protected int mineAmount;
	protected ArrayList<Shell>shells;
	protected ArrayList<TankMine>mines;
	protected static List<Integer>pressedKeys;
	protected InputStream music;
	protected AudioStream audios;
	
	protected InputStream music2;
	protected AudioStream audios2;
	
	protected InputStream music3;
	protected AudioStream audios3;
	
	protected InputStream music4;
	protected AudioStream audios4;
	//soundSelection
	protected boolean soundSelected;
	
	Tank(GamePlay gamePlay, Point position, int direction){
		this.gamePlay = gamePlay;
		this.position = position;
		this.direction = direction;
		lifeAmount = FULL_LIFE_AMOUNT;
		mineAmount = MAX_NUM_MINES;
		shells = new ArrayList<Shell>();
		mines = new ArrayList<TankMine>();
		pressedKeys = new ArrayList<Integer>();
		gamePlay.addKeyListener(this);
		exploded = false;
		timer = new Timer(5, this);
		setTimer(2000);
		
		//soundSelected = gamePlay.getSound();
	}
	
	public void drawShells(Graphics g){
		for (int i = 0; i < shells.size(); i++)
				shells.get(i).drawShell(g);
	}
	
	public abstract void keyPressed(KeyEvent e); 
	public abstract void keyReleased(KeyEvent e);

	public void keyTyped(KeyEvent e) {	
	}
	
	public void setTimer(int time){
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	exploded = false;
		            	setTimer(2000);
		            }
		        }, 
		        time 
		);
	}
	
	public void actionPerformed(ActionEvent e){//it just runs the code every 5 millisecond till key released and it stops.
		move(direction, SPEED);
		gamePlay.repaint();
	}
	
	public void move(int direction, int speed){
			switch(direction){
			case UP:
				if(position.y-1 >= 47)
					position.y-=speed;
				break;
			case DOWN:
				if (position.y + 1 <= DIMENSION_Y - 65)
					position.y+=speed;
				break;
			case RIGHT:
				if (position.x + 1 <= DIMENSION_X - 42)
					position.x+=speed;
				break;
			case LEFT:
				if (position.x-1 >= 0)
					position.x-=speed;
				break;
			}
	}
	public void shoot(){
		Point shellPosition = new Point(position.x + (TANK_WIDTH/2), position.y + (TANK_HEIGHT/2));
		Shell newShell = new Shell(gamePlay, shellPosition, direction);
		shells.add(newShell);
		shootSound();
	}
	
	public void createMine(){
		if (mineAmount > 0){
			int x = position.x;
			int y = position.y;
			if (direction == UP)
				y += TANK_HEIGHT;
			else if (direction == DOWN)
				y -= TANK_HEIGHT;
			else if (direction == LEFT)
				x += TANK_HEIGHT;
			else if (direction == RIGHT)
				x -= TANK_HEIGHT;
			
			if (0 <= x && x <= DIMENSION_X - 50 && 0 <= y && y <= DIMENSION_Y - 18){
				TankMine tankMine = new TankMine(gamePlay, new Point(x, y));
				mines.add(tankMine);
				mineAmount--;
			}
		}
	}
	
	public void drawMines(Graphics g){
		for (int i = 0; i < mines.size(); i++)
			mines.get(i).drawMine(g);
	}
	
	public boolean isDead(){
		return lifeAmount <= 0;
	}
	
	public abstract void updateLife(int value);
	
	public void updateMineAmount(int amount){
		mineAmount = Math.min(mineAmount+amount, MAX_NUM_MINES);
		if(!soundSelected)
			pickMineSound();
	}
	
	public int getMineAmount(){
		return mineAmount;
	}
	
	public int getMaxNumMines(){
		return MAX_NUM_MINES;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public int getWidth(){
		return TANK_WIDTH;
	}
	
	public int getHeight(){
		return TANK_HEIGHT;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public ArrayList<TankMine> getMines(){
		return mines;
	}
	
	public ArrayList<Shell> getShells(){
		return shells;
	}
	
	public void shootSound(){

		try{
			music = new FileInputStream(new File("tank_shoot_sound.wav"));
			audios = new AudioStream(music);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}

		if(!soundSelected)
			AudioPlayer.player.start(audios);
	}
	
	public void pickMineSound(){

		try{
			music2 = new FileInputStream(new File("pick_mine_sound.wav"));
			audios2 = new AudioStream(music2);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}

		if(!soundSelected)
			AudioPlayer.player.start(audios2);

	}
	
	public boolean almostDead(){
		return lifeAmount == 1;
	}
	
	public void explode(){
		exploded = true;
	}
	
	//set sound
	public void setSound(boolean soundSelected){
		this.soundSelected = soundSelected;
	}
	
	public void shellToTankSound(){

		try{
			music3 = new FileInputStream(new File("shell_to_tank_sound.wav"));
			audios3 = new AudioStream(music3);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}

		if(!soundSelected)
			AudioPlayer.player.start(audios3);

	}
	
	public void mineSound(){

		try{
			music4 = new FileInputStream(new File("mine_sound.wav"));
			audios4 = new AudioStream(music4);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}


		AudioPlayer.player.start(audios4);

	}
}
