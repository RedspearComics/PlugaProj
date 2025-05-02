package pluga.view;

import javax.swing.*;

import pluga.controller.Controller;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

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
	
	
	public GamePanel(Controller app)
	{
		super();
		
		this.app = app;
		this.layout = new SpringLayout();
		this.screenImage = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
		this.setMinimumSize(new Dimension(1000, 800));
		this.xVal = 500;
		this.yVal = 400;
		this.isRunning = true;
		this.gameTick = new Timer((1000 / 60), gameTickListener);
		this.setFocusable(true);
		this.directions = new boolean [4];
		
		setupListeners();
		
	}
	private void setupPanel()
	{
		
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
				yVal -= 5;
			}
			if(directions[1] == true)
			{
				yVal += 5;
			}
			if(directions[2] == true)
			{
				xVal -= 5;
			}
			if(directions[3] == true)
			{
				xVal += 5;
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
			
			updateCanvas();
		}
	}
	
	private Polygon drawCharacterAt(int xValue, int yValue)
	{
		int [] xVals = {(xValue -20), (xValue + 20), (xValue + 20), (xValue -20)};
		int [] yVals = {(yValue - 20), (yValue - 20), (yValue + 20), (yValue + 20)};
		
		Polygon shape = new Polygon(xVals, yVals, 4);
		
		return shape;
	}
	
	private void updateCanvas()
	{
		Graphics2D drawingTool = (Graphics2D) screenImage.getGraphics();
		
		drawingTool.setColor(new Color(16, 18, 23));
		drawingTool.fill(new Rectangle(0, 0, 1000, 800));
		
		drawingTool.setColor(new Color(23, 77, 194));
		drawingTool.fill(drawCharacterAt(xVal, yVal));

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
