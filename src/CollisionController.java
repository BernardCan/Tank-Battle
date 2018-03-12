import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionController {
	protected final static int LEFT = 0;
	protected final static int RIGHT = 1;
	protected final static int UP = 2;
	protected final static int DOWN = 3;
	private static final int BREAKABLE = 1, UNBREAKABLE = 0, TRANSPARENT = 2;
	private ArrayList<GamePlayMine>gamePlayMines;
	private ArrayList<TankMine>tankMines1;
	private ArrayList<TankMine>tankMines2;
	private ArrayList<Shell>shellsTank1;
	private ArrayList<Shell>shellsTank2;
	private Tank1 tank1;
	private Tank2 tank2;
	private ArrayList<Health>healths;
	private ArrayList<Obstacles>obstacles;
	private boolean soundSelected;

	
	CollisionController(GamePlay gamePlay){
		tankMines1 = gamePlay.getTank1().getMines();
		tankMines2 = gamePlay.getTank2().getMines();
		
		shellsTank1 = gamePlay.getTank1().getShells();
		shellsTank2 = gamePlay.getTank2().getShells();
		
		gamePlayMines = gamePlay.getMines();
		healths = gamePlay.getHealths();
		obstacles = gamePlay.getObstacles();

		tank1 = gamePlay.getTank1();
		tank2 = gamePlay.getTank2();
	}

	public boolean isCollision(int x1, int y1, int width1, int height1,
							   int x2, int y2, int width2, int height2){

		Rectangle r1 = new Rectangle(x1, y1, width1, height1);
		Rectangle r2 = new Rectangle(x2, y2, width2, height2);

		return r1.intersects(r2);
	}
	
	public void handleCollision(){
		int x, x1, x2;
		int y, y1, y2;
		
		int shellWidth = Shell.getWidth();
		int shellHeight = Shell.getHeight();
		
		int tankMineHeight = Mine.getHeight();
		int tankMineWidth = Mine.getWidth();
		
		int healthWidth = Health.getWidth();
		int healthHeight = Health.getHeight();
		
		int obstacleWidth = Obstacles.getWidth();
		int obstacleHeight = Obstacles.getHeight();
		
		int tankWidth = tank1.getWidth();
		int tankHeight = tank1.getHeight();
		
		//shell to obstacle
		for (int i = 0; i < shellsTank1.size(); i++){
			x1 = shellsTank1.get(i).getPosition().x; 
			y1 = shellsTank1.get(i).getPosition().y;
			for (int j = 0; j < obstacles.size(); j++){
				x2 = obstacles.get(j).getPosition().x;
				y2 = obstacles.get(j).getPosition().y;
				
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, obstacleWidth, obstacleHeight)){
					if (obstacles.get(j).getType() == BREAKABLE){
						obstacles.get(j).explode();
						if (shellsTank1.size() > 0)
							shellsTank1.remove(i);
						obstacles.remove(j);
					}
					else if (obstacles.get(j).getType() == UNBREAKABLE){
						if (shellsTank1.size() > 0)
							shellsTank1.remove(i);
						((Unbreakable)obstacles.get(j)).animation();
					}
				}
			}
		}
		for (int i = 0; i < shellsTank2.size(); i++){
			x1 = shellsTank2.get(i).getPosition().x; 
			y1 = shellsTank2.get(i).getPosition().y;
			for (int j = 0; j < obstacles.size(); j++){
				x2 = obstacles.get(j).getPosition().x;
				y2 = obstacles.get(j).getPosition().y;
				
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, obstacleWidth, obstacleHeight)){
					System.out.println("touched");
					if (obstacles.get(j).getType() == BREAKABLE){
						obstacles.get(j).explode();
						if (shellsTank2.size() > 0)
							obstacles.remove(j);
						shellsTank2.remove(i);
					}
					else if (obstacles.get(j).getType() == UNBREAKABLE){
						if (shellsTank2.size() > 0)
							shellsTank2.remove(i);
						((Unbreakable)obstacles.get(j)).animation();
					}
				}
			}
		}
		//shells to tanks
		x1 = tank1.getPosition().x;
		y1 = tank1.getPosition().y;
		x2 = tank2.getPosition().x;
		y2 = tank2.getPosition().y;
		for (int i = 0; i < shellsTank1.size(); i++){
			x = shellsTank1.get(i).getPosition().x;
			y = shellsTank1.get(i).getPosition().y;
			
			if (isCollision(x, y, shellWidth, shellHeight, x2, y2, tankWidth, tankHeight)){
				tank2.updateLife(-shellsTank1.get(i).getValue());
				shellsTank1.remove(i);
				if(!soundSelected)
					tank2.shellToTankSound();
			}
		}
		
		for (int i = 0; i < shellsTank2.size(); i++){
			x = shellsTank2.get(i).getPosition().x;
			y = shellsTank2.get(i).getPosition().y;
			
			if (isCollision(x, y, shellWidth, shellHeight, x1, y1, tankWidth, tankHeight)){
				tank1.updateLife(-shellsTank2.get(i).getValue());
				shellsTank2.remove(i);
				if(!soundSelected)
					tank1.shellToTankSound();
			}
		}
		
		//shell to TankMine
		for (int i = 0; i < shellsTank1.size(); i++){
			x1 = shellsTank1.get(i).getPosition().x;
			y1 = shellsTank1.get(i).getPosition().y;
			for (int j = 0; j < tankMines1.size(); j++){
				x2 = tankMines1.get(j).getPosition().x;
				y2 = tankMines1.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, tankMineWidth, tankMineHeight)){
					shellsTank1.remove(i);
					tankMines1.get(j).explode();
					tankMines1.remove(j);
				}
			}
		}
		for (int i = 0; i < shellsTank1.size(); i++){
			x1 = shellsTank1.get(i).getPosition().x;
			y1 = shellsTank1.get(i).getPosition().y;
			for (int j = 0; j < tankMines2.size(); j++){
				
				x2 = tankMines2.get(j).getPosition().x;
				y2 = tankMines2.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, tankMineWidth, tankMineHeight)){
					shellsTank1.remove(i);
					tankMines2.get(j).explode();
					tankMines2.remove(j);
				}
			}
		}
		for (int i = 0; i < shellsTank2.size(); i++){
			x1 = shellsTank2.get(i).getPosition().x;
			y1 = shellsTank2.get(i).getPosition().y;
			for (int j = 0; j < tankMines1.size(); j++){
				
				x2 = tankMines1.get(j).getPosition().x;
				y2 = tankMines1.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, tankMineWidth, tankMineHeight)){
					shellsTank2.remove(i);
					tankMines1.get(j).explode();
					tankMines1.remove(j);
				}
			}
		}
		for (int i = 0; i < shellsTank2.size(); i++){
			x1 = shellsTank2.get(i).getPosition().x;
			y1 = shellsTank2.get(i).getPosition().y;
			for (int j = 0; j < tankMines2.size(); j++){
				
				x2 = tankMines2.get(j).getPosition().x;
				y2 = tankMines2.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, tankMineWidth, tankMineHeight)){
					shellsTank2.remove(i);
					tankMines2.get(j).explode();
					tankMines2.remove(j);
				}
			}
		}
		
		//mine to tank
		x1 = tank1.getPosition().x;
		y1 = tank1.getPosition().y;
		x2 = tank2.getPosition().x;
		y2 = tank2.getPosition().y;
		for (int i = 0; i < tankMines1.size(); i++){
			x = tankMines1.get(i).getPosition().x;
			y = tankMines1.get(i).getPosition().y;
			
			if (isCollision(x, y, tankMineWidth, tankMineHeight, x1, y1, tankWidth, tankHeight)){
				tank1.updateLife(-tankMines1.get(i).getValue());
				if(!soundSelected)
					tank1.mineSound();
				tank1.explode();
				tankMines1.remove(i);
			}
		}
		for (int i = 0; i < tankMines1.size(); i++){
			x = tankMines1.get(i).getPosition().x;
			y = tankMines1.get(i).getPosition().y;
			
			if (isCollision(x, y, tankMineWidth, tankMineHeight, x2, y2, tankWidth, tankHeight)){
				tank2.updateLife(-tankMines1.get(i).getValue());
				if(!soundSelected)
					tank2.mineSound();
				tank2.explode();
				tankMines1.remove(i);
			}
		}
		for (int i = 0; i < tankMines2.size(); i++){
			x = tankMines2.get(i).getPosition().x;
			y = tankMines2.get(i).getPosition().y;
			
			if (isCollision(x, y, tankMineWidth, tankMineHeight, x1, y1, tankWidth, tankHeight)){
				tank1.updateLife(-tankMines2.get(i).getValue());
				if(!soundSelected)
					tank1.mineSound();
				tank1.explode();
				tankMines2.remove(i);
			}
		}
		for (int i = 0; i < tankMines2.size(); i++){
			x = tankMines2.get(i).getPosition().x;
			y = tankMines2.get(i).getPosition().y;
			
			if (isCollision(x, y, tankMineWidth, tankMineHeight, x2, y2, tankWidth, tankHeight)){
				tank2.updateLife(-tankMines2.get(i).getValue());
				if(!soundSelected)
					tank2.mineSound();
				tank2.explode();
				tankMines2.remove(i);
			}
		}
		
		
		//shell to gamePlayMine
		for (int i = 0; i < shellsTank1.size(); i++){
			x1 = shellsTank1.get(i).getPosition().x;
			y1 = shellsTank1.get(i).getPosition().y;
			for (int j = 0; j < gamePlayMines.size(); j++){
				x2 = gamePlayMines.get(j).getPosition().x;
				y2 = gamePlayMines.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, healthWidth, healthHeight)){
					gamePlayMines.get(j).explode();
					gamePlayMines.remove(j);
					shellsTank1.remove(i);
				}
			}
		}
		for (int i = 0; i < shellsTank2.size(); i++){
			x1 = shellsTank2.get(i).getPosition().x;
			y1 = shellsTank2.get(i).getPosition().y;
			for (int j = 0; j < gamePlayMines.size(); j++){
				x2 = gamePlayMines.get(j).getPosition().x;
				y2 = gamePlayMines.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, healthWidth, healthHeight)){
					gamePlayMines.get(j).explode();
					gamePlayMines.remove(j);
					shellsTank2.remove(i);
				}
			}
		}
		
		//shell to power-up
		for (int i = 0; i < shellsTank1.size(); i++){
			x1 = shellsTank1.get(i).getPosition().x;
			y1 = shellsTank1.get(i).getPosition().y;
			for (int j = 0; j < healths.size(); j++){
				x2 = healths.get(j).getPosition().x;
				y2 = healths.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, healthWidth, healthHeight)){
					healths.get(j).explode();
					healths.remove(j);
				}
			}
		}
		for (int i = 0; i < shellsTank2.size(); i++){
			x1 = shellsTank2.get(i).getPosition().x;
			y1 = shellsTank2.get(i).getPosition().y;
			for (int j = 0; j < healths.size(); j++){
				x2 = healths.get(j).getPosition().x;
				y2 = healths.get(j).getPosition().y;
				if (isCollision(x1, y1, shellWidth, shellHeight, x2, y2, healthWidth, healthHeight)){
					healths.get(j).explode();
					healths.remove(j);
				}
			}
		}
		
		//tank to health
		x1 = tank1.getPosition().x;
		y1 = tank1.getPosition().y;
		x2 = tank2.getPosition().x;
		y2 = tank2.getPosition().y;
		for (int i = 0; i < healths.size(); i++){
			x = healths.get(i).getPosition().x;
			y = healths.get(i).getPosition().y;
			if (isCollision(x, y, healthWidth, healthHeight, x1, y1, tankWidth, tankHeight)){
				tank1.updateLife(healths.get(i).getValue());
				healths.remove(i);
			}
			if (isCollision(x, y, healthWidth, healthHeight, x2, y2, tankWidth, tankHeight)){
				tank2.updateLife(healths.get(i).getValue());
				healths.remove(i);
			}
		}
		
		//tank to gamePlayMine
		x1 = tank1.getPosition().x;
		y1 = tank1.getPosition().y;
		x2 = tank2.getPosition().x;
		y2 = tank2.getPosition().y;
		for (int i = 0; i < gamePlayMines.size(); i++){
			x = gamePlayMines.get(i).getPosition().x;
			y = gamePlayMines.get(i).getPosition().y;
			if (isCollision(x, y, healthWidth, healthHeight, x1, y1, tankWidth, tankHeight) && tank1.getMineAmount() < tank1.getMaxNumMines()){
				tank1.updateMineAmount(1);
				gamePlayMines.remove(i);
			}
			if (isCollision(x, y, healthWidth, healthHeight, x2, y2, tankWidth, tankHeight) && tank2.getMineAmount() < tank2.getMaxNumMines()){
				tank2.updateMineAmount(1);
				gamePlayMines.remove(i);
			}
		}
	
		//tank to obstacle
				x1 = tank1.getPosition().x;
				y1 = tank1.getPosition().y;
				x2 = tank2.getPosition().x;
				y2 = tank2.getPosition().y;
				for (int i = 0; i < obstacles.size(); i++){
					x = obstacles.get(i).getPosition().x;
					y = obstacles.get(i).getPosition().y;
					if(obstacles.get(i).getType() != TRANSPARENT){
						if (isCollision(x, y, obstacleWidth, obstacleHeight, x1, y1, tankWidth, tankHeight)){
							if (tank1.getDirection() == LEFT)
								tank1.move(RIGHT, 7);
							else if (tank1.getDirection() == RIGHT)
								tank1.move(LEFT, 7);
							else if (tank1.getDirection() == UP){
								tank1.move(DOWN, 7);
							}
							else if (tank1.getDirection() == DOWN)
								tank1.move(UP, 7);
						}
						if (isCollision(x, y, obstacleWidth, obstacleHeight, x2, y2, tankWidth, tankHeight)){
							if (tank2.getDirection() == LEFT)
								tank2.move(RIGHT, 7);
							else if (tank2.getDirection() == RIGHT)
								tank2.move(LEFT, 7);
							else if (tank2.getDirection() == UP)
								tank2.move(DOWN, 7);
							else if (tank2.getDirection() == DOWN)
								tank2.move(UP, 7);
						}
					}
				}
		
		//shell to borders of the map
		for (int i = 0; i < shellsTank1.size(); i++)
			if (shellsTank1.get(i).reachedBorders())
				shellsTank1.remove(i);
		for (int i = 0; i < shellsTank2.size(); i++)
			if (shellsTank2.get(i).reachedBorders())
				shellsTank2.remove(i);
		
	}
	
	public void setSound(boolean soundSelected){
		this.soundSelected = soundSelected;
	}
}
