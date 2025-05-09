package pluga.view;

import javax.swing.*;

import pluga.controller.Controller;
import pluga.model.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

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
	private int health;
	private ArrayList <BasicEnemy> enemyList;
	
	
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
		this.health = 50;
		this.enemyList = new ArrayList<BasicEnemy>();
		
		
		setupPanel();
		setupListeners();
		
	}
	private void setupPanel()
	{
		BasicEnemy enemy1 = new BasicEnemy(75,0,0,3);
		BasicEnemy enemy2 = new BasicEnemy(75, 1000, 800, 4);
		BasicEnemy tpEnemy1 = new TeleportingEnemy(50, 1000,0, 2);
		BasicEnemy rnEnemy1 = new RunnerEnemy(100, 400,400, 3);
		enemyList.add(enemy1);
		enemyList.add(enemy2);
		enemyList.add(tpEnemy1);
		enemyList.add(rnEnemy1);
	}
	
	private void setupLayout()
	{
		
	}
	
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
			{
				//mouseX = click.getX();
				//mouseY = click.getY();
			}

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
			{
				
			}

			public void mouseEntered(MouseEvent enter)
			{

			}

			public void mouseExited(MouseEvent exit)
			{
			
			}
		});
			
	}

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
		}
	}
	
	private Polygon drawCharacterAt(int xValue, int yValue)
	{
		int [] xVals = {(xValue -20), (xValue + 20), (xValue + 20), (xValue -20)};
		int [] yVals = {(yValue - 20), (yValue - 20), (yValue + 20), (yValue + 20)};
		
		Polygon shape = new Polygon(xVals, yVals, 4);
		
		return shape;
	}
	
	private Rectangle drawSword()
	{
		Rectangle shape = new Rectangle(xVal, yVal, 6, 160);
		
		return shape;
	}
	
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
		
		for (int index = 0; index < enemyList.size(); index++)
		{
			BasicEnemy currentEnemy = enemyList.get(index);
			currentEnemy.enemyCheck(xVal, yVal);
			drawingTool.setColor(currentEnemy.getColor());
			drawingTool.fill(currentEnemy.drawEnemy());
			
			if(swordTimer > 0)
			{
				
				if(currentEnemy.drawEnemy().intersects(drawSword()))
				{
					currentEnemy.hit();
					System.out.println(currentEnemy.getHealth());
				}
			}
			
			if(currentEnemy.getHealth() < 1)
			{
				enemyList.remove(index);
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
