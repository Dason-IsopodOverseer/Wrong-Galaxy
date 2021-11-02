import java.io.*;
import java.util.ArrayList; // import the ArrayList class

public class TileMap {

	private Sprite[][] tiles;
	public ArrayList<String> tileConfig = new ArrayList(); // stores the configuration of different tiles after being read by mapReader
	private Sprite a, b, c, d, e, f, g, n;
	private Game game;
	private int height = 0; // stores the height of the map
	private int width = 0; // stores the width of the map
	
	private int tileSize = 50;
	
	public TileMap(String tileFile, Game g) {
		
		// read the txt tileFile and populate the empty tileConfig arraylist
		// also gets width and height
		mapReader(tileFile);
		game = g;
		
		tiles = new Sprite[width][height];
		
		// set sprites a and b to corresponding tile images
		a = (SpriteStore.get()).getSprite("sprites/a.png");
		b = (SpriteStore.get()).getSprite("sprites/b.png");
		c = (SpriteStore.get()).getSprite("sprites/c.png");
		n = (SpriteStore.get()).getSprite("sprites/n.png");
		fillMap();
	}
	
	public int getWidth() {
		return tiles.length;
	}
	
	public int getHeight() {
		return tiles[0].length;
	}
	
	// get width - tiles.length
	// get height - tiles[0].length
	public Sprite getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, Sprite tile) {
		tiles[x][y] = tile;
	}
	
	// fills sprite array with appropriate tiles (based on string [] input)
	private Sprite[][] fillMap() {
		
	    // begin to parse!
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	        	String line = tileConfig.get(y);
	            char ch = line.charAt(x);
	            
	            // check if the char represents tile A, B, N, etc.
				if (ch == 'A') {
					tiles[x][y] = a;
				} else if (ch == 'B') {
					tiles[x][y] = b;
				} else if (ch == 'C') {
					tiles[x][y] = c;
				} else if (ch == 'N') {
					tiles[x][y] = n;
				} else if (ch == 'k') {
					tiles[x][y] = null;
					game.entities.add(new KlingonEntity(game, "kling", (x * tileSize), (y * tileSize)));
				} else if (ch == 'm') {
					tiles[x][y] = null;
					game.entities.add(new KlingonEntity(game, "master", (x * tileSize), (y * tileSize)));
				} else if (ch == 'b') {
					tiles[x][y] = null;
					game.entities.add(new BorgEntity(game, "borg", (x * tileSize), (y * tileSize)));
				} else if (ch == 'q') {
					tiles[x][y] = null;
					game.entities.add(new BorgEntity(game, "queen", (x * tileSize), (y * tileSize)));
				} 
				else {
					tiles[x][y] = null;
				}
	        }
	    }
		return tiles;
	}
	
	// reads the tileFile ad stores it in tileConfig
	private void mapReader(String tileFile) {
		
        // try to retrieve file contents
        try {
        	
        	// input
            String folderName = "/maps/";
            String resource = tileFile;

			// this is the path within the jar file
			InputStream input = TileMap.class.getResourceAsStream(folderName + resource);
			if (input == null) {
				
				// this is how we load file within editor (eg eclipse)
				input = TileMap.class.getClassLoader().getResourceAsStream(resource);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			in.mark(Short.MAX_VALUE);  // see api

		    while (true) {
		        String line = in.readLine();
		        
		        // no more lines to read
		        if (line == null) {
		            in.close();
		            break;
		        }
		        
		        // add every line except for comments
		        if (!line.startsWith("#")) {
		        	
		        	// * makes an entire row empty
		        	if (line.startsWith("*")) {
		        		line = "                                "; // 24 spaces
		        	}
		            tileConfig.add(line);
		            
		            // set the width
		            width = Math.max(width, line.length());
		        }
		    }
		    
		    // set the height
		    height = tileConfig.size();
        } catch (Exception e) {
        	System.out.println("File Input Error");
        } // end of try catch
    }
}

