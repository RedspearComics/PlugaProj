package pluga.model;

import java.awt.Color;

public class RunnerEnemy extends BasicEnemy
{
	public RunnerEnemy(int health, int x, int y, int speed)
	{
		super(health, x, y, speed);
		this.color = new Color(255,203,15);
	}
	
	@Override
	public void enemyCheck(int playerX, int playerY)
	{
		if (playerX != xVal)
		{
			//xVal += (Math.cos(Math.atan2(playerY - yVal, playerX - xVal)) * speed);
			xVal += (int) (-0.03 * (xVal - playerX));
		}
		if (playerY != yVal)
		{
			//yVal += (Math.sin(Math.atan2(playerY - yVal, playerX - xVal)) * speed);
			yVal += (int) (-0.03 *(yVal - playerY));
		}
	
	}
	@Override
	public Color getColor()
	{
		return this.color;
	}
}