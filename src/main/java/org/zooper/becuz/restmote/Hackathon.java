package org.zooper.becuz.restmote;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.annotate.JsonIgnore;

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
	
	Random randomGenerator = new Random();
	
	PFont f;  
	
	// Constants
	int W = 1024;
	int H = 768;
	int MAX_BALOON_DX = 50;
	int MAX_BALOON_DY = 50;
	int NUM_BALOONS = 5;
	int MAX_BALOON_SPEED = 3;
	
	String[] colors = {"red", "green", "blue", "yellow", "violet"};
	int[] colorsr = {255, 0, 0, 255, 143};
	int[] colorsg = {0, 255, 0, 255, 0};
	int[] colorsb = {0, 0, 255, 0, 255};
	
	List<Player> players = new ArrayList<Player>();
	
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
//			for (int i = 0; i < 2; i++) {
//				addPlayer();
//			}
		}
	}
	
	public void movePlayer(Integer index, int xA, int yA) {
		Player p = players.get(index);
		p.deltaPosition(xA, yA);
	}
	
	public Player addPlayer(){
		int index = players.size();
		if (index < colors.length){
			int color = color(colorsr[index],colorsg[index],colorsb[index]);
			Player p = new Player(index, color, colors[index]);
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
//					println("addScore");
					p.addScore();
					b.reset();
					break;
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
			try {
				for(Player p: players){
					p.display();
					scores += colors[p.getIndex()] + ": " + p.getScore() + "  ";
				}
			} catch (ConcurrentModificationException e){
				e.printStackTrace();
			}
			textFont(f,16); // Step 4: Specify font to be used
			fill(0);        // Step 5: Specify font color
			color(255);
			text(scores, 20, 20); 
//			println(scores);
		}
	}
	
	public void mousePressed() {
		println(mouseX + " " + mouseY);
		Player p = players.get(0);
		p.setPosition(mouseX, mouseY);
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
		int dpx = 10;
		int dpy = 10;
		int color;
		int directionX = 1; //1 or -1
		int directionY = 1; //1 or -1
		int speedX = randomGenerator.nextInt(MAX_BALOON_SPEED) + 1;
		int speedY = randomGenerator.nextInt(MAX_BALOON_SPEED) + 1;

		public Baloon(int x, int y, int dx, int dy, int color) {
			super();
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.color = color;
		}
		
		boolean intersect(int x, int y){
//			println("x :" + x + "y :" + y);
			int xM = this.x - dx/2;
			int xP = this.x + dx/2;
			int yM = this.y - dy/2;
			int yP = this.y + dy/2;
//			println("xM :" + xM + "xP :" + xP + "yM :" + yM + "yM :" + yM);
			if (x > xM && x < xP && 
					y > yM && y < yP){
				println("intersect true");
				return true;
			}
			println("intersect false");
			return false;
		}
		
		void reset(){
			x = randomGenerator.nextInt(W);
			y = randomGenerator.nextInt(H);
			speedX = randomGenerator.nextInt(MAX_BALOON_SPEED) + 1;
			speedY = randomGenerator.nextInt(MAX_BALOON_SPEED) + 1;
		}
		
		void display() {
			stroke(1);
			fill(color);
			ellipse(x, y, dx, dy);
			ellipse(
					x,//x + dx/2 + dpx/2 , 
					y + dy/2 + dpy/2, 
					dpx, 
					dpy);
			line(
					x,// + dx/2 + dpx, 
					y + dy/2 + dpy, 
					x,// + dx/2 + dpx, 
					y + dy/2 + dpy + 25);
		}

		
		
		void move() {
			x+= (directionX * speedX);
			y+= (directionY * speedY);
			if (x > W || x < 0) {
				directionX *= -1;
			}
			if (y > H || y < 0) {
				directionY *= -1;
			}
		}

	}

	public class Player {
		@JsonIgnore
		int x;
		
		@JsonIgnore
		int y;
		
		int index;
		
		int color;
		
		String colorS;
		
		@JsonIgnore
		int score = 0;
		
		@JsonIgnore
		List<Baloon> baloons = new ArrayList<Baloon>();
		
		public Player(int index, int color, String colorS) {
			super();
			this.index = index;
			this.color = color;
			this.colorS = colorS;
		}
		public void deltaPosition(int xA, int yA) {
			x += xA;
			y += yA;
			setPosition(x, y);
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
		public int getColor() {
			return color;
		}
		public String getColorS() {
			return colorS;
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
			if (x > W) this.x = W;
			if (x < 0) this.x = 0;
			if (y > H) this.y = H;
			if (y < 0) this.y = 0;
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
