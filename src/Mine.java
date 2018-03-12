import java.awt.Graphics;
import java.io.*;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import sun.audio.*;


import sun.audio.AudioStream;

public abstract class Mine {
	private static int DIMENSION_X = 1000, DIMENSION_Y = 768;
	protected static final int MINE_WIDTH = 20, MINE_HEIGHT = 20;
	protected final int VALUE = 2;
	protected Point mine_coordinates;
	protected Image mine_image;
	protected GamePlay gameplay;
	protected boolean finished;
	
	protected InputStream music;
	protected AudioStream audios;
	
	public Mine(GamePlay game, Point coordinates){
		this.mine_coordinates = coordinates;
		this.gameplay = game;
		setImage("mine.png");
		finished = false;
	}
	
	public Mine(GamePlay game){
		this.gameplay = game;
		setImage("gmine.png");
		finished = false;
	}

	public abstract void drawMine(Graphics g);
	public abstract JLabel explode();
	
	public void setImage(String name){
		mine_image = null;
		try {
			URL imagePath = GamePlay.class.getResource(name);
			mine_image = Toolkit.getDefaultToolkit().getImage(imagePath);
		} catch (Exception e) {
			// image not exist
			e.printStackTrace();
		}
	}
	
	public Image getImage(){
		return this.mine_image;
	}
	
	public void setNullImage(){
		this.mine_image = null;
	}
	
	public void setTimer(int time){
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                gameplay.eraseFinishedMines();
		                finished = true;
		                gameplay.repaint();
		            }
		        }, 
		        time 
		);
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public Point getPosition(){
		return mine_coordinates;
	}

	public int getValue(){
		return VALUE;
	}
	
	public static int getWidth(){
		return MINE_WIDTH;
	}
	
	public static int getHeight(){
		return MINE_HEIGHT;
	}
	
	public void mineSound(){

		try{
			music = new FileInputStream(new File("mine_sound.wav"));
			audios = new AudioStream(music);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}


		AudioPlayer.player.start(audios);

	}
}
