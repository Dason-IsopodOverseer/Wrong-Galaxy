public abstract class EnemyEntity extends Entity {    
    protected final int fallingSpeed = 300;
    protected boolean isTurning = false;
    protected boolean turningRight = false;
    protected int turnTarget;
    private String type = "";
    
	// the following variables control entity status
	public byte health = 1;
	protected double moveSpeed = 0;
	
    public EnemyEntity(final Game g, final String r, final int newX, final int newY, int speed) {
        super(r, newX, newY, true);
        game = g;
        type = r;
        dx = speed; // uses parent's speed
    }
    
    public void setMap() {
    	map = game.getTileMap();
    }
    
    public void move(long delta) {
    	//if (type != "master") {
    		//dy = fallingSpeed;
    	//}
    		dy = fallingSpeed;
        	
        	if (isTurning) {
        		double temp = dx;
        		dx = (turningRight) ? 50:-50;
        		super.move(delta);
        		if ((turningRight && x > turnTarget) || (!turningRight && x < turnTarget)) {
        			isTurning = false;
        		} else {
        			dx = temp;
        		}
        	} else {
        		super.move(delta);
        	}
    	super.move(delta);
    } // move
    

    protected void turnAround(int dx) {
    	isTurning = true;
    	if (dx > 0) {
    		turningRight = false;
    		turnTarget = (int) x - 100; // 100 is now the minimum platform width
    	} else if (dx < 0) {
    		turningRight = true;
    		turnTarget = (int) x + 100; // 100 is now the minimum platform width
    	} else {
    		System.out.println("dx = 0 " + this);
    	}
    }
    
    
    protected boolean isTileCompletelyBelow(long delta) {
    	
    	// if entity's bottom-left or bottom-right corner is in a tile
     	try {
     		return map.getTile(right / tileSize, (int) (bottom + (delta * dy) / 1000 + 1) / tileSize) != null && map.getTile(left / tileSize, (int) (bottom + (delta * dy) / 1000 + 1) / tileSize) != null;
     	} catch (Exception e) {
     		return false;
     	} // catch
    } // isTileCompletelyBelow
    
   
    
    public void moveBack (){
    	if (!game.facingRight) {
			// if enemy would hit left edge
			if (x - 30 < 1) {
				x = 1;
			}
			// if luke would hit a tile to the left
			else if (map.getTile(((int) x - 31) / game.tileSize, bottom / game.tileSize) != null || map.getTile(((int) x - 31) / game.tileSize, top / game.tileSize) != null) {
				x = ((int) (x - 30) / game.tileSize * game.tileSize) + game.tileSize;
				System.out.println("left");
			}
			else {
				x = x - 30;
			}
		}
		// if enemy is on the luke's right
		else {
			// if enemy would hit right edge
			if (x + 31 > game.GAMEWIDTH - this.getWidth() - 1) {
				x = game.GAMEWIDTH - this.getWidth() - 1;
			}
			// if luke would hit a tile
			else if (map.getTile(((int) this.x + this.getWidth() + 31) / game.tileSize, bottom / game.tileSize) != null || map.getTile(((int) this.x + this.getWidth() + 31) / game.tileSize, top / game.tileSize) != null) {
				x = (int) (this.x + this.getWidth() + 30) / game.tileSize * game.tileSize - this.getWidth();
				System.out.println("right");
			}
			else {
				x = x + 30;
			}
		}
    }
}