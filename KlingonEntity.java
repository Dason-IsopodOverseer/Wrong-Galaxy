public class KlingonEntity extends EnemyEntity {
    private String type = "";
    private byte count = 0;
    private boolean jumping = false;
    private int jumpCount = 0;
	
    public KlingonEntity(final Game g, final String r, final int newX, final int newY) {
        super(g, r, newX, newY, 50);
        type = r;
        dx = 0;
        
        // set health
        if (type.equals("master")) {
        	health = 3;
        }
    }
    
    public void move(long delta) {
    	
    	// counter increments every 50 frames
    	if (frame % 50 == 0) {
    		count++;
    	}
    	if (count > 100) {
    		count = 0;
    	}
    	
    	// counts time elapsed after jumping
    	if (jumping = true) {
    		jumpCount++;
    	}

    	if (jumpCount > 500) {
    		dy = fallingSpeed;
    		jumping = false;
    		jumpCount = 0;
    	}

    	/* klingons chase after player, masters can jump
    	 * this checks to see if luke is within the enemy's sight
    	 */
    	if (!beingPushed) {
	    	if (game.luke.getY() + 200 >= y) {
	    		dx = 40;
	    		if (type.equals("master")) {
	    			dx *= 1.5;
	    		}
	    		
	    		if (game.luke.getX() < x) {
	        		dx *= -1;
	    		}
	    		
	    		// configure additional movements depending on type of enemy
	    		if (type.equals("master") && count >= 100 && this.isTileBelow(delta)) {
	        		dy = -400;
	        		jumping = true;
	        	}
	    	} 
    	}
    	super.move(delta);
    } // move
    
    public void collidedWith(final Entity other) {
    }
}