package org.zooper.becuz.restmote;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;

public class Hackathon extends PApplet {

	private static Hackathon embed;
	
	public static Hackathon getInstance(){
		if (embed == null){
			embed = new Hackathon();
		}
		return embed;
	}
	
	
	PFont f;  
	
	// Constants
	int W = 1024;
	int H = 768;
	int MAX_BALOON_DX = 50;
	int MAX_BALOON_DY = 50;
	int NUM_BALOONS = 5;
	int MAX_BALOON_SPEED = 5;
	String[] colors = {"red", "white", "blue"};
	int[] colorsr = {255, 255, 0};
	int[] colorsg = {0, 255, 0};
	int[] colorsb = {0, 255, 255};
	
	List<Player> players = new ArrayList<Player>();
	
	int i = 0;

	private boolean startGame = false;
	
	public void setup() {
		size(W, H);
		f = loadFont( "Verdana-16.vlw" );
		initGame();
		startGame();
	}
	
	public void initGame(){
		players = new ArrayList<Player>();
	}
	
	public void startGame(){
		startGame = !startGame;
		if (startGame){
			for (int i = 0; i < 3; i++) {
				addPlayer();
			}
		}
	}
	
	public void movePlayer(Integer index, int xA, int yA) {
		Player p = players.get(index);
		p.deltaPosition(xA, yA);
	}
	
	public Player addPlayer(){
		Random randomGenerator = new Random();
		int index = players.size();
		if (index < colors.length){
			int color = color(colorsr[index],colorsg[index],colorsb[index]);
			Player p = new Player(index, color);
			int xP = randomGenerator.nextInt(W);
			int yP = randomGenerator.nextInt(H);
			p.setPosition(xP, yP);
			players.add(p);
			for (int j = 0; j < NUM_BALOONS; j++) {
				int x = randomGenerator.nextInt(W);
				int y = randomGenerator.nextInt(H);
				int dx = 80;//randomGenerator.nextInt(MAX_BALOON_DX);
				int dy = 80;//randomGenerator.nextInt(MAX_BALOON_DY);
				Baloon b = new Baloon(x, y, dx, dy, color);
				p.addBaloon(b);
			}
			return p;
		}
		return null;
	}
	
	public void setPlayerPosition(int indexPlayer, int x, int y){
		Player p = players.get(indexPlayer);
		p.setPosition(x, y);
	}
	
	public void playerClick(int indexPlayer){
		Player p = players.get(indexPlayer);
		if (p != null){
			for(Baloon b: p.getBaloons()){
				if (b.intersect(p.getX(), p.getY())){
					p.addScore();
				}
			}
		}
	}

	public void draw() {
		if (keyPressed) {
		    if (key == 'i' || key == 'I') {
		      initGame();
		    }
		    if (key == 's' || key == 'S') {
		    	startGame();
		    }
		}
		background(102);
		if (startGame){
			String scores = "";
			for(Player p: players){
				p.display();
				scores += colors[p.getIndex()] + ": " + p.getScore() + "  ";
			}
			textFont(f,16); // Step 4: Specify font to be used
			fill(0);        // Step 5: Specify font color
			color(255);
			text(scores, 20, 20); 
			println(scores);
		}
	}
	
	public void mousePressed() {
		println(mouseX + " " + mouseY);
		playerClick(0);
	}

//	public void createEllipse(int xx, int yy, int dx, int dy) {
//		ellipse(xx, yy, dx, dy);
//	}

	public class Baloon {
		int x;
		int y;
		int dx;
		int dy;
		int color;
		int directionX = 1; //1 or -1
		int directionY = 1; //1 or -1

		public Baloon(int x, int y, int dx, int dy, int color) {
			super();
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.color = color;
		}
		boolean intersect(int x, int y){
			if (x > this.x - dx && x < this.x + dx && 
					y > this.y - dy && y < this.y + dy){
				return true;
			}
			return false;
		}
		void display() {
			stroke(1);
			fill(color);
			ellipse(x, y, dx, dy);
		}

		void move() {
			x+= (directionX * MAX_BALOON_SPEED);
			y+= (directionY * MAX_BALOON_SPEED);
			if (x > W || x < 0) {
				directionX *= -1;
			}
			if (y > H || y < 0) {
				directionY *= -1;
			}
		}

	}

	public class Player {
		int x;
		int y;
		int index;
		int color;
		int score = 0;
		List<Baloon> baloons = new ArrayList<Baloon>();
		public Player(int index, int color) {
			super();
			this.index = index;
			this.color = color;
		}
		public void deltaPosition(int xA, int yA) {
			x += xA;
			y += yA;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		void addScore(){
			score++;
		}
		public int getScore() {
			return score;
		}
		public int getIndex() {
			return index;
		}
		public List<Baloon> getBaloons() {
			return baloons;
		}
		public void addBaloon(Baloon b) {
			baloons.add(b);
		}
		public void display() {
			noFill();
			stroke(color);
			ellipse(x, y, 30, 30);
			ellipse(x, y, 5, 5);
			for(Baloon b: getBaloons()){
				b.move();
				b.display();
			}
		}
		public void setPosition(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "hackathon" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}

	
}
