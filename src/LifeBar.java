import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import javax.swing.*;

import sun.audio.*;

public class LifeBar extends JPanel{

	private int GreenBar_HEIGHT = 27;
	private final int Mine_Width = 20;
	private final int Mine_Height = 20;
	private int greenBar_WIDTH;
	private GamePlay gamePlay;
	private Image greenBarImg;
	private Image mineIMG;
	private int num;
	private InputStream music;
	private AudioStream audios;
	
	//mines
	private int mineNum;
	private boolean soundSelected;
	
	public Point green_coordinates;
	
	LifeBar(){
		
	}
	
	LifeBar(GamePlay gamePlay, Point green_coordinates, int num){
		
		this.gamePlay = gamePlay;
		greenBar_WIDTH = 210;
		this.setBackground(new java.awt.Color(255, 0, 51));
		
		this.setSize(350,27);
		this.green_coordinates = green_coordinates;
		this.num = num;
		
		String destination;
		if(num == 1)
			destination = "GreenHealth.png";
		else destination = "BlueHealth.png";
		
		greenBarImg = null;
		try {
			URL imagePath = GamePlay.class.getResource(destination);
			greenBarImg = Toolkit.getDefaultToolkit().getImage(imagePath);
		} catch (Exception e) {
			// image not exist
			e.printStackTrace();
		}
		
		mineIMG = null;
		try {
			URL imagePath = GamePlay.class.getResource("mine.png");
			mineIMG = Toolkit.getDefaultToolkit().getImage(imagePath);
		} catch (Exception e) {
			// image not exist
			e.printStackTrace();
		}	
		
		//mineNum = 3;
	}
	
	public void decreaseLife(int value){
		if(num == 1){
			greenBar_WIDTH += 30 * value;
		}
		
		if(num == 2){
			//greenBar_WIDTH -= 30;
			green_coordinates = new Point(green_coordinates.x - (30*value), 0);
		}

		this.gamePlay.repaint();
	}
	
	//increase life
	public void increseLife(){
		if(num == 1){
			if(greenBar_WIDTH < 210)
				greenBar_WIDTH += 30;
		}
		
		if(num == 2){
			if(green_coordinates.x > 790)
				green_coordinates = new Point(green_coordinates.x - 30, 0);
		}
		
		
		if(!soundSelected)
			healthSound();
		
		gamePlay.repaint();
	}
	
	public void drawBar(Graphics g) {
		//super.paintComponent(g);
		g.setFont(new Font("Kristen ITC", Font.BOLD, 32));
		g.drawImage(greenBarImg, green_coordinates.x, green_coordinates.y, greenBar_WIDTH, GreenBar_HEIGHT, gamePlay);
		if(num == 1){
			g.setColor(new Color(102, 204, 0));
			g.drawString("Oyuncu-" + num , green_coordinates.x + 220, 27);
		}
		if(num == 2){
			g.setColor(new Color(51, 204, 255));
			g.drawString("Oyuncu-" + num , green_coordinates.x - 180, 27);
		}
		
		//adding mines
		if(num == 1){
			for(int i = 0; i < mineNum; i++)
				g.drawImage(mineIMG, i*Mine_Width, 27, Mine_Width, Mine_Height, gamePlay);
		}
		
		if(num == 2){
			for(int i = 0; i < mineNum; i++)
				g.drawImage(mineIMG, 970-(i*Mine_Width), 27, Mine_Width, Mine_Height, gamePlay);
		}
	}	
	
	public void setMineNum(int mineAmount){
		mineNum = mineAmount;
	}

	public void healthSound(){

		try{
			music = new FileInputStream(new File("health_sound.wav"));
			audios = new AudioStream(music);
		}
		catch(IOException e1){
			JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
		}


		AudioPlayer.player.start(audios);

	}
	
	public void setSoundSelected(boolean soundSelected){
		this.soundSelected = soundSelected;
	}
}
