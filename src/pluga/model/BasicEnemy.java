package pluga.model;

import java.awt.Polygon;

import pluga.view.GamePanel;

public class BasicEnemy
{
	private int xVal;
	private int yVal;
	private int health;
	private int speed;
	
	public BasicEnemy(int health, int x, int y, int speed)
	{
		this.health = health;
		this.xVal = x;
		this.yVal = y;
		this.speed = speed;
		
	}
	
	public void enemyCheck(int playerX, int playerY)
	{
		if (playerX > xVal)
		{
			if (Math.abs(playerX - xVal) < speed)
			{
				xVal += (playerX - xVal);
			}
			else
			{
				xVal += speed;
			}
		}
		if (playerX < xVal)
		{
			if (Math.abs(playerX - xVal) < speed)
			{
				xVal += (playerX - xVal);
			}
			else
			{
				xVal -= speed;
			}
		}
		
		if (playerY > yVal)
		{
			if (Math.abs(playerY - yVal) < speed)
			{
				yVal +=(playerY - yVal);
			}
			else
			{
				yVal += speed;
			}
		}
		if (playerY < yVal)
		{
			if (Math.abs(playerY - yVal) < speed)
			{
				yVal +=(playerY - yVal);
			}
			else
			{
				yVal -= speed;
			}
		}
	}
	
	public Polygon drawEnemy()
	{
		int [] xVals = {(xVal -20), (xVal + 20), (xVal + 20), (xVal -20)};
		int [] yVals = {(yVal - 20), (yVal - 20), (yVal + 20), (yVal + 20)};
		
		Polygon shape = new Polygon(xVals, yVals, 4);
		
		return shape;
	}
}
