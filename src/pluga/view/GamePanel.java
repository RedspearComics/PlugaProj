package pluga.view;

import javax.swing.*;

import pluga.controller.Controller;
import pluga.model.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import javax.sound.sampled.*;

public class GamePanel extends JPanel
{
	private Controller app;
	private SpringLayout layout;
	private BufferedImage screenImage;
	private int xVal;
	private int yVal;
	private int mouseX;
	private int mouseY;
	private boolean isRunning;
	private Timer gameTick;
	private ActionListener gameTickListener;
	private boolean [] directions;
	private int swordTimer;
	private int itemTimer;
	private int health;
	private int kills;
	private ArrayList <BasicEnemy> enemyList;
	
	private HashMap<Integer, Integer> itemSet;
	
	
	public GamePanel(Controller app)
	{
		super();
		
		this.app = app;
		this.layout = new SpringLayout();
		this.screenImage = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
		this.setMinimumSize(new Dimension(1000, 800));
		this.xVal = 500;
		this.yVal = 400;
		this.mouseX = 0;
		this.mouseY = 0;
		this.isRunning = true;
		this.gameTick = new Timer((1000 / 60), gameTickListener);
		this.setFocusable(true);
		this.directions = new boolean [4];
		this.swordTimer = 0;
		this.itemTimer = 1200;
		this.health = 50;
		this.kills = 0;
		this.enemyList = new ArrayList<BasicEnemy>();
		
		this.itemSet = new HashMap<Integer, Integer>();
		
		
		setupPanel();
		setupListeners();
		
	}
	private void setupPanel()
	{
		BasicEnemy enemy = new BasicEnemy(75,0,0,3);
		BasicEnemy enemy0 = new BasicEnemy(75, 1000, 800, 4);
		BasicEnemy tpEnemy1 = new TeleportingEnemy(50, 1000,0, 2);
		BasicEnemy rnEnemy1 = new RunnerEnemy(100, 400,400, 1);
		enemyList.add(enemy);
		enemyList.add(enemy0);
		enemyList.add(tpEnemy1);
		enemyList.add(rnEnemy1);
		itemSet.put(0, 1);
		itemSet.put(1, 3);
		itemSet.put(2, 5);
		itemSet.put(3, 10);
	}
	
	private void setupLayout()
	{
		
	}
	/**
	 * Sets up mouse and key listeners
	 */
	private void setupListeners()
	{
		gameTick.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent timerFire)
			{
				gameTick();
			}
		});

		gameTick.start();

		this.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent press)
			{
				
				int key = press.getKeyCode();
				
				if (key == KeyEvent.VK_W)
				{
					directions[0] = true;
				}
				if (key == KeyEvent.VK_S)
				{
					directions[1] = true;
				}
				if (key == KeyEvent.VK_A)
				{
					directions[2] = true;
				}
				if (key == KeyEvent.VK_D)
				{
					directions[3] = true;
				}
			}
			public void keyReleased(KeyEvent release)
			{
				int key = release.getKeyCode();
				
				if (key == KeyEvent.VK_W)
				{
					directions[0] = false;
				}
				if (key == KeyEvent.VK_S)
				{
					directions[1] = false;
				}
				if (key == KeyEvent.VK_A)
				{
					directions[2] = false;
				}
				if (key == KeyEvent.VK_D)
				{
					directions[3] =false;
				}
			}
			public void keyTyped(KeyEvent type)
			{}
		});
		
		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent click)
			{}

			public void mousePressed(MouseEvent press)
			{
				
				if (swordTimer <= 0)
				{
					mouseX = press.getX();
					mouseY = press.getY();
					swordTimer = 20;
				}
			}

			public void mouseReleased(MouseEvent release)
			{}

			public void mouseEntered(MouseEvent enter)
			{}

			public void mouseExited(MouseEvent exit)
			{}
		});
			
	}
	/**
	 * Runs each frame. calls update canvas and does bounds checks. 
	 */
	private void gameTick()
	{
		if (isRunning)
		{
			if(directions[0] == true)
			{
				yVal -= 6;
			}
			if(directions[1] == true)
			{
				yVal += 6;
			}
			if(directions[2] == true)
			{
				xVal -= 6;
			}
			if(directions[3] == true)
			{
				xVal += 6;
			}
			
			if(xVal > 1000)
			{
				xVal = 1000;
			}
			if(xVal < 0)
			{
				xVal = 0;
			}
			if (yVal > 770)
			{
				yVal = 770;
			}
			if (yVal < 0)
			{
				yVal = 0;
			}
			
			if(swordTimer < 0)
			{
				swordTimer = 0;
			}
			
			if(health <= 0)
			{
				isRunning = false;
			}
			updateCanvas();
			swordTimer--;
			itemTimer--;
		}
	}
	/**
	 * Draws the character at a specified point on the screen
	 * @param xValue The midpoint x value of the character
	 * @param yValue The midpoint y value of the character
	 * @return the character polygon
	 */
	private Polygon drawCharacterAt(int xValue, int yValue)
	{
		int [] xVals = {(xValue -20), (xValue + 20), (xValue + 20), (xValue -20)};
		int [] yVals = {(yValue - 20), (yValue - 20), (yValue + 20), (yValue + 20)};
		
		Polygon shape = new Polygon(xVals, yVals, 4);
		
		return shape;
	}
	/**
	 * Draws a sword
	 * @return a rectangle shape, which is the sword in question
	 */
	private Rectangle drawSword()
	{
		Rectangle shape = new Rectangle(xVal, yVal, 6, 160);
		
		return shape;
	}
	/** 
	 * draws a small item on the screen when called
	 * @param xValue the x value of the top left corner of the item
	 * @param yValue the y value of the top left corner of the item
	 * @return the item shape
	 */
	private Rectangle drawItem(int xValue, int yValue)
	{
		Rectangle item = new Rectangle(xValue, yValue, 20, 40);
		
		return item;
	}
	/**
	 * called every frame by gameTick(). draws the player, enemies, sword, and item.
	 */
	private void updateCanvas()
	{
		Graphics2D drawingTool = (Graphics2D) screenImage.getGraphics();
		
		AffineTransform restore = drawingTool.getTransform();
		
		drawingTool.setColor(new Color(16, 18, 23));
		drawingTool.fill(new Rectangle(0, 0, 1000, 800));
		
		drawingTool.setColor(new Color(23, 77, 194));
		drawingTool.fill(drawCharacterAt(xVal, yVal));
		
		
		
		if (swordTimer > 0)
		{
				
//			double rotation = Math.tan((double)(yVal - mouseY) / (double)(xVal - mouseX));
			double rotation = Math.atan2(mouseY - yVal, mouseX - xVal) - (Math.PI / 2);
			
			drawingTool.rotate(rotation, xVal, yVal);
			
			drawingTool.setColor(new Color(43,45,171));
			drawingTool.fill(drawSword());
			
			
			drawingTool.setTransform(restore);

			
		}
		
		if (itemTimer < 0)
		{
			int itemType = itemSet.get((int) Math.random() * itemSet.size());
			drawingTool.setColor(new Color(57,237,12));
			drawingTool.fill(drawItem(300,300));

			if (drawCharacterAt(xVal, yVal).intersects(drawItem(300,300)))
			{
				itemTimer = 1200;
				health += itemType;
				
			}
		}
		
		for (int index = 0; index < enemyList.size(); index++)
		{
			BasicEnemy currentEnemy = enemyList.get(index);
			currentEnemy.enemyCheck(xVal, yVal);
			drawingTool.setColor(currentEnemy.getColor());
			drawingTool.fill(currentEnemy.drawEnemy());
			
			if(swordTimer > 0)
			{
				
				if(currentEnemy.drawEnemy().intersects(new Rectangle(xVal - 100, yVal - 100, 200, 200)))
				{
					currentEnemy.hit();
					System.out.println(currentEnemy.getHealth());
				}
			}
			
			if(currentEnemy.getHealth() < 1)
			{
				if(currentEnemy instanceof TeleportingEnemy)
				{
					enemyList.add(new TeleportingEnemy((int) (Math.random() * 200), (int) (Math.random() * 1000), (int) (Math.random() * 800), 1 +(int) (Math.random() * 5)));
					
					if (Math.random() > 0.70)
					{
					enemyList.add(new TeleportingEnemy((int) (Math.random() * 200),(int) (Math.random() * 1000), (int) (Math.random() * 800), 1 +(int) (Math.random() * 5)));
					}
					
				}
				else if (currentEnemy instanceof RunnerEnemy)
				{
					System.out.println("test");
					enemyList.add(new RunnerEnemy((int) (Math.random() * 200), (int) (Math.random() * 1000), (int) (Math.random() * 800), 1 +(int) (Math.random() * 2)));
					
					if (Math.random() > 0.95)
					{
					enemyList.add(new RunnerEnemy((int) (Math.random() * 200), (int) (Math.random() * 1000), (int) (Math.random() * 800), 1 +(int) (Math.random() * 2)));
					}
				
				}
				else
				{
					enemyList.add(new BasicEnemy((int) (Math.random() * 200), (int) (Math.random() * 1000), (int) (Math.random() * 800), 1+(int) (Math.random() * 5)));
					
					if (Math.random() > 0.90)
					{
					enemyList.add(new BasicEnemy((int) (Math.random() * 200), (int) (Math.random() * 1000), (int) (Math.random() * 800), 1+(int) (Math.random() * 5)));
					}
				}
				
				enemyList.remove(index);
				index--;
			}
		}

		drawingTool.dispose();
		repaint();
	}
	
	@Override 
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		graphics.drawImage(screenImage, 0, 0, null);
	}
}
