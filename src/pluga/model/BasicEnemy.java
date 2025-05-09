package pluga.model;

import java.awt.Color;
import java.awt.Polygon;

import pluga.view.GamePanel;

public class BasicEnemy
{
	protected int xVal;
	protected int yVal;
	protected int health;
	protected int speed;
	protected Color color;
	
	public BasicEnemy(int health, int x, int y, int speed)
	{
		this.health = health;
		this.xVal = x;
		this.yVal = y;
		this.speed = speed;
		this.color = new Color(242,17,17);
		
	}
	
	public void enemyCheck(int playerX, int playerY)
	{
		if (playerX != xVal)
		{
			xVal += (Math.cos(Math.atan2(playerY - yVal, playerX - xVal)) * speed);
		}
		if (playerY != yVal)
		{
			yVal += (Math.sin(Math.atan2(playerY - yVal, playerX - xVal)) * speed);
		}
	}
	
	
	public Polygon drawEnemy()
	{
		int [] xVals = {(xVal -20), (xVal + 20), (xVal + 20), (xVal -20)};
		int [] yVals = {(yVal - 20), (yVal - 20), (yVal + 20), (yVal + 20)};
		
		Polygon shape = new Polygon(xVals, yVals, 4);
		
		return shape;
	}
	
	public void hit()
	{
		this.health--;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public Color getColor()
	{
		return this.color;
	}
}
