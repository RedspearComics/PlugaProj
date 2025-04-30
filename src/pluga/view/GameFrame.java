package pluga.view;

import javax.swing.*;
import pluga.controller.Controller;
import java.awt.*;

public class GameFrame extends JFrame
{
	private Controller app;
	private GamePanel panel;
	
	public GameFrame(Controller app)
	{
		super();
		
		this.app = app;
		this.panel = new GamePanel(this.app);
		
		
		setupFrame();
	}
	
	private void setupFrame()
	{
		setContentPane(panel);
		setSize(1000, 800);
		setTitle("PLUGA");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setVisible(true);
	}
}
