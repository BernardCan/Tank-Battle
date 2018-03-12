import java.awt.Dimension;
import sun.audio.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//bu en önemli classlardan biri
public class GamePlay extends Panel {
		
	private final static long serialVersionUID = 1L;
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	private static int LOCATION_X = 0, LOCATION_Y = 0;
	private final int TIMER = 4000;
	private boolean flag;
	private Tank1 tank1;
	private Tank2 tank2;
	private ArrayList<Obstacles>obstacles;
	private ArrayList<GamePlayMine>mines;
	private ArrayList<Health>healths;
	private CollisionController collisionController;
	private String winnerPlayer;
	private Ending ending;
	
	//müzik
	private AudioStream audios;
	private InputStream music;
	
	//ses
	private boolean soundSelected;
	
	//for the start menu
	private boolean selected;
	
	GamePlay(){
		this.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y));
		//mapController = new MapController(this);
		healths = new ArrayList<Health>();
		mines = new ArrayList<GamePlayMine>();
		flag = false;
		setTimer(TIMER);
		
		tank1 = new Tank1(this);

		tank2 = new Tank2(this);
		//////
		ending = new Ending();
		
		try{
            music = new FileInputStream(new File("the_kraken_hans_zimmer_www_dilandau_eu.wav"));
            audios = new AudioStream(music);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
        }
			
	}
	
	public void paint(Graphics g){
		drawGrid(g);
		
		if(!tank1.isDead() && !tank2.isDead()){
	
			tank1.drawTank(g);
	
			tank1.drawShells(g);
			tank1.drawMines(g);
			////
			tank1.drawLifeBar(g);
			
			tank2.drawTank(g);		
			
			
			tank2.drawShells(g);
			tank2.drawMines(g);
			tank2.drawLifeBar(g);
	
			drawObstacles(g);
			drawHealths(g);
			drawMines(g);
		}
		else{
			if(tank1.isDead()){
				winnerPlayer = "Player2";
				ending.setNum(2);
			}
			else{
				winnerPlayer = "Player1";
				ending.setNum(1);
			}
			///
			ending.setWinner(winnerPlayer);
			ending.drawEnding(g);
		}
		collisionController.handleCollision();
	}
	
	private void drawHealths(Graphics g) {
		for (int i = 0; i < healths.size(); i++)
			healths.get(i).drawHealth(g);
	}

	public void update(Graphics g)
	{
		// default update contains double buffering
		Graphics offScreenGraphics;
		BufferedImage offScreen = null;
		Dimension d = this.getSize();
		
		offScreen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		offScreenGraphics = offScreen.getGraphics();
		offScreenGraphics.setColor(this.getBackground());
		offScreenGraphics.fillRect(0,  0, d.width, d.height);
		offScreenGraphics.setColor(this.getForeground());
		paint(offScreenGraphics);
		
		//flip
		g.drawImage(offScreen, 0, 0, this);
	}
	
	
	public void drawGrid(Graphics g)	{
		Image hal = null;
		try
		{
			URL imagePath = GamePlay.class.getResource("background.jpg");
			hal= Toolkit.getDefaultToolkit().getImage(imagePath);
		}
		catch (Exception e)
		{
			//image not exist
			e.printStackTrace();
		}
			
		g.drawImage(hal, LOCATION_X, LOCATION_Y, DIMENSION_X, DIMENSION_Y,this);
	}
	
	public void addObjects(Tank1 tank1, Tank2 tank2, ArrayList<Obstacles>obstacles){
		this.tank1 = tank1;
		this.tank2 = tank2;
		this.obstacles = obstacles;
	}
	
	public void drawObstacles(Graphics g)
	{
       for (int i = 0 ; i < obstacles.size(); i++){
    	   obstacles.get(i).drawObstacle(g);
       }	   
	}
	
	public void drawMines(Graphics g){
		for (int i = 0; i < mines.size(); i++){
			mines.get(i).drawMine(g);
		}
	}
	
	public void eraseFinishedHealths(){
		for (int i = 0; i < healths.size(); i++){
			if (healths.get(i).isFinished())
				healths.remove(i);
		}
	}
	
	public void eraseFinishedMines(){
		for (int i = 0; i < mines.size(); i++)
			if (mines.get(i).isFinished()){
				mines.remove(i);
			}
		for (int i = 0; i < tank1.getMines().size(); i++)
			if (tank1.getMines().get(i).isFinished()){
				if(!soundSelected)
					tank1.getMines().get(i).mineSound();
				tank1.getMines().remove(i);
			}
		for (int i = 0; i < tank2.getMines().size(); i++)
			if (tank2.getMines().get(i).isFinished()){
				if(!soundSelected)
					tank2.getMines().get(i).mineSound();
				tank2.getMines().remove(i);
			}
	}

	
	public void setTimer(int time){
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                if (!flag){
		                	healths.add(new Health(GamePlay.this));
		                	flag = true;
		                }
		                else{
		                	mines.add(new GamePlayMine(GamePlay.this));
		                	flag = false;
		                }
		                setTimer(TIMER);
		                repaint();
		            }
		        }, 
		        time 
		);
	}
	
	public void addCollisionController(CollisionController collisionController){
		this.collisionController = collisionController;
	}
	
	
	public Tank1 getTank1(){
		return tank1;
	}
	
	public Tank2 getTank2(){
		return tank2;
	}
	
	public ArrayList<Obstacles> getObstacles(){
		return obstacles;
	}
	
	public ArrayList<GamePlayMine> getMines(){
		return mines;
	}
	
	public ArrayList<Health> getHealths(){
		return healths;
	}
	
	//music selected get and set methods
	public boolean getSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}

	//deadPlayer
	public boolean playerDead(){
		if(tank1.isDead() || tank2.isDead())
			return true;
		return false;
	}
	
	//return winner
	public String getWinner(){
		return winnerPlayer;
	}
	
	//stop the music
	public void stopMusic(){
		AudioPlayer.player.stop(audios);
	}
	
	public void startMusic(){
		AudioPlayer.player.start(audios);
	}
	
	//selecting sounds in the game
	public void setSound(boolean soundSelected){
		this.soundSelected = soundSelected;
		tank1.setSound(soundSelected);
		tank2.setSound(soundSelected);
	}
	
	public boolean getSound(){
		return soundSelected;
	}
	
}

