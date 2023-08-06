import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Game_Panel extends JPanel implements ActionListener{
	
	static final int wid=700;
	static final int hig=700;
	static final int size=25;
	static final int Game_unit=(wid*hig)/size;
	final int[] x=new int[Game_unit];
	final int[] y=new int[Game_unit];
	int body;
	int eaten=0;
	int applex;
	int appley;
	char direction;
	boolean running = false;
	static final int Delay=200;
	Timer timer;
	Random random;
	
	Game_Panel(){
		random = new Random();
		this.setPreferredSize(new Dimension(wid,hig));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		

		startGame();
	}
	public void startGame(){
		newApple();
		direction = 'R';
		body=10;
		Arrays.fill(x, 99);
		Arrays.fill(y, 99);
		int X=15;
		for(int i=0;i<body; i++) {
			x[i]=X--;
			y[i]=5;
		}
		
		running=true;
		timer = new Timer(Delay, this);
		timer.start();
		
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		// OPTIONAL LINES GRID
//		for(int i=0;i<hig/size;i++) {
//			g.drawLine(i*size, hig, i*size, 0);
//			g.drawLine(wid, i*size,0, i*size);
//		}
		
		
		g.setColor(Color.RED);
		g.fillOval(applex, appley, size, size);
		
	
		for(int i=1;i<body;i++) {
			g.setColor(Color.GREEN);
			g.fillRect(x[i]*size, y[i]*size, size, size);
		}
		g.setColor(Color.WHITE);
		g.fillRect(x[0]*size, y[0]*size, size, size);
	}
	public void newApple() {
		applex=random.nextInt(wid/size)*size;
		appley=random.nextInt(hig/size)*size;
	}
	public void move() {
		for(int i=body-1;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch (direction) {
		case 'R' :
			x[0]=x[0]+1;
			break;
		case 'D' :
			y[0]=y[0]+1;
			break;
		case 'L':
			x[0]=x[0]-1;
			break;
		case 'U':
			y[0]=y[0]-1;
			break;
		}
	}
	public void apple() {
	if(x[0]*size==applex && y[0]*size==appley) {
		 
		body++;
		newApple();
		
	}
	}
	public void collision() {
		for(int i=1;i<body;i++) {
			if(x[0]==x[i]&&y[0]==y[i]) {
			gameover();
			}
		}
		if(x[0]>27|| y[0]>27) {
			gameover();
		}
	}
	public void gameover() {
		timer.stop();
		running = false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		apple();
		collision();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				direction='U';
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				direction='D';
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				direction='L';
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				direction='R';
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE && !running) {
				startGame();
			}
			
		}
		
	}

}
