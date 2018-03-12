import java.awt.Point;
import java.util.ArrayList;
import java.awt.Graphics;

public class MapController {
	
	//attributes
	private ArrayList<Point>coordinates;
	private ArrayList<Obstacles> wall;
	private GamePlay gamePlay;
	private Tank1 tank1;
	private Tank2 tank2;
	
	
	//constructor
	public MapController(GamePlay gamePlay){
		this.gamePlay = gamePlay; 
		
		coordinates = new ArrayList<>();
		addCoordinates();
		
		wall = new ArrayList<Obstacles>();
		addObstacles();
		
		tank1 = gamePlay.getTank1();
		tank2 = gamePlay.getTank2();	
		
		gamePlay.addObjects(tank1, tank2, wall);
	}
	
	//methods
	public void addCoordinates(){
		//breakable left side
		int xCo = 200;
		int yCo = 250;
		for (int i = 0; i < 10; i++){
			coordinates.add(new Point (xCo, yCo));
			yCo +=27;
		}
		
		//unbreakable + breakable middle 
		xCo = 500;
		yCo = 250;
		for (int i = 10; i < 20; i++){
			coordinates.add(new Point (xCo, yCo));
			yCo+=27;
		}
		
		//breakable right
		xCo = 793;
		yCo = 250;
		for (int i = 20; i < 30; i++){
			coordinates.add(new Point (xCo, yCo));
			yCo+=27;
		}
		
		// unbreakable bottom
		xCo = 447;
		yCo= 600;
		for (int i = 30; i < 35; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		xCo = 447;
		yCo = 627;
 		for (int i = 35; i < 40; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
 		
 		xCo = 447;
		yCo = 654;
		for (int i = 40; i < 45; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//unbreakable top
		xCo = 447;
		yCo= 50;
		for (int i = 45; i < 50; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		xCo = 447;
		yCo = 77;
 		for (int i = 50; i < 55; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
 		
 		xCo = 447;
		yCo = 104;
		for (int i = 55; i < 60; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//transparent
		//top right
		xCo = 800;
		yCo = 50;
		for (int i = 60; i < 63; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		xCo = 800;
		yCo = 77;
		for (int i = 63; i < 66; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//bottom left
		xCo = 146;
		yCo = 600;
		for (int i = 66; i < 69; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		xCo = 146;
		yCo = 627;
		for (int i = 69; i < 72; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//top roof
		xCo = 200;
		yCo = 223;
		for (int i = 72; i < 95; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//lower roof
		xCo = 200;
		yCo = 520;
		for (int i = 95; i < 118; i++){
			coordinates.add(new Point (xCo, yCo));
			xCo+=27;
		}
		
		//transparent
				//top left
				xCo = 146;
				yCo = 50;
				for (int i = 118; i < 121; i++){
					coordinates.add(new Point (xCo, yCo));
					xCo+=27;
				}
				
				xCo = 146;
				yCo = 77;
				for (int i = 121; i < 124; i++){
					coordinates.add(new Point (xCo, yCo));
					xCo+=27;
				}
				
				//bottom right
				xCo = 800;
				yCo = 600;
				for (int i = 124; i < 127; i++){
					coordinates.add(new Point (xCo, yCo));
					xCo+=27;
				}
				
				xCo = 800;
				yCo = 627;
				for (int i = 127; i < 130; i++){
					coordinates.add(new Point (xCo, yCo));
					xCo+=27;
				}
		
	}
	
	public void addObstacles(){
		
		//breakable left
		for (int i = 0; i < 10; i++){
			if(i == 1 || i == 4 || i == 5 || i == 8)
				wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
			else
				wall.add(new Breakable(gamePlay, coordinates.get(i)));
		}
		
		//unbreakable + breakable middle
		for (int i = 10; i < 20; i++){
			 if(i == 14 || i == 15){
				wall.add(new Breakable(gamePlay, coordinates.get(i)));  
			   }
			  else
				wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
			}
		
		//breakable right
		for (int i = 20; i < 30; i++){
			if(i == 21 || i == 24 || i == 25 || i == 28)
				wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
			else
			  wall.add(new Breakable(gamePlay, coordinates.get(i)));
			}
		
		//bottom block
		for (int i = 30; i < 45; i++){
			if((i > 34 && i < 40) || (i == 32 || i == 42)  )
			   wall.add(new Breakable(gamePlay, coordinates.get(i)));
			else
			   wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
			}
		//top block
		for (int i = 45; i < 60; i++){
			if((i > 49 && i < 55) || (i == 47 || i == 57))
			   wall.add(new Breakable(gamePlay, coordinates.get(i)));
			else 
			   wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
				}
			
		//transparent top right and bottom left

		for (int i = 60; i < 72; i++ ){
			    wall.add(new Transparent(gamePlay, coordinates.get(i)));
		}
		
		//top roof
		for (int i = 72; i < 95; i++){
			if(i == 75 || i == 76 || i == 79 || i == 80 || i == 86 || i == 87 || i == 90 ||i == 91){
				wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
			}
			else 
				wall.add(new Breakable(gamePlay, coordinates.get(i)));
		}	
		//lower roof
			for (int i = 95; i < 118; i++){
				if(i == 98 || i == 99 || i == 102 || i == 103 || i == 109 || i == 110 || i == 113 ||i == 114){
					wall.add(new Unbreakable(gamePlay, coordinates.get(i)));
				}
				else 
					wall.add(new Breakable(gamePlay, coordinates.get(i)));	
			
		}
			
		//transparent bottom right and top left
			for (int i = 118; i < 130; i++ ){
			    wall.add(new Transparent(gamePlay, coordinates.get(i)));
		}
			
	}
}
 