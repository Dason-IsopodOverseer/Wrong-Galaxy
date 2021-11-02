public class BorgEntity extends EnemyEntity {
	private boolean isQueen = false;
	private byte count = 0;
	
    public BorgEntity(final Game g, final String r, final int newX, final int newY) {
        super(g, r, newX, newY, 50);
        moveSpeed = 35;
        
     	// set queen attributes
        if (r.equals("queen")) {
        	isQueen = true;
        	health = 3;
        	moveSpeed /= 2; // queen is slower than drones
        }
        dx = moveSpeed;
    }
    
    public void move(long delta) {
    	super.move(delta);
    	
    	// check if this entity should turn around
		if (isTileBelow(delta) && !isTileCompletelyBelow(delta)) {
			dx = -dx;
			x += (delta * dx) / 500;
		} // if
		
		// configure additional movements and attacks depending on type of enemy
		if (isQueen && frame == 0) {
			count++;
			
			// queen sometimes turns around randomly
			int random = (int)(Math.random() * 10);
			if (random == 0) {
				dx *= -1;
			}
			
			// queen spawns a drone periodically
			if (count % 10 == 0) {
				count = 0;
				Entity b = new BorgEntity(game, "borg", this.getX(), this.getY());
				game.entities.add(b);
				b.setMap();
			}
		}
    } // move
    
    public void collidedWith(final Entity other) {
    }
}