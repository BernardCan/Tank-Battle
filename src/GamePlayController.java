
public class GamePlayController {
	
	private GamePlay gamePlay;
	private MapController mapController;
	
	GamePlayController(){
		CollisionController collisionController;
		
		gamePlay = new GamePlay();
		boolean soundSelected = gamePlay.getSound();
				
		mapController = new MapController(gamePlay);
		collisionController = new CollisionController(gamePlay);
		collisionController.setSound(soundSelected);
		gamePlay.addCollisionController(collisionController);
		
	}
	
	public GamePlay getGamePlay(){
		return gamePlay;
	}
	
}
