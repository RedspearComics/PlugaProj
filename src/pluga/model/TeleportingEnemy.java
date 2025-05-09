package pluga.model;

import java.awt.Color;

public class TeleportingEnemy extends BasicEnemy
{
	public TeleportingEnemy(int health, int x, int y, int speed)
	{
		super(health, x, y, speed);
		this.color = new Color(126,43,189);
	}
	
	@Override
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
	
		if (Math.abs(xVal - playerX) < 50 && Math.abs(yVal - playerY) < 50)
		{
			xVal += -8 * (xVal - playerX);
			yVal += -8 * (yVal - playerY);
		}
	}
	@Override
	public Color getColor()
	{
		return this.color;
	}
}
