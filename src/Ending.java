import javax.swing.*;
import java.awt.*;

public class Ending extends JPanel{

	public Ending(){
		this.winnerPlayer = "";
	}
	
	public void setWinner(String winnerPlayer){
		this.winnerPlayer = winnerPlayer;
	}
	
	public void drawEnding(Graphics g){
		g.setFont(new Font("Kristen ITC", Font.BOLD, 38));
		if(num == 2)
			g.setColor(new Color(51, 204, 255));
		if(num == 1)
			g.setColor(new Color(102, 204, 0));
		g.drawString("Winner is: \n"+winnerPlayer, 350, 350);
	}
	
	public void setNum(int num){
		this.num = num;
	}
	
	private int num;
	private String winnerPlayer;
}
