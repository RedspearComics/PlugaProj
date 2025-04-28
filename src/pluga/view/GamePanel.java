package pluga.view;

import javax.swing.*;

import pluga.controller.Controller;

public class GamePanel extends JPanel
{
	private Controller app;
	private SpringLayout layout;
	
	
	public GamePanel(Controller app)
	{
		super();
		
		this.app = app;
		this.layout = new SpringLayout();
	}
}
