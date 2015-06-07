package com.utbm.info.vi51.buildingEvac.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.envinterface.channel.GridStateChannelListener;
import org.janusproject.jaak.envinterface.perception.EnvironmentalObject;
import org.janusproject.jaak.environment.model.JaakEnvironment;

import com.utbm.info.vi51.buildingEvac.agent.AgentBlind;
import com.utbm.info.vi51.buildingEvac.agent.AgentID;
import com.utbm.info.vi51.buildingEvac.agent.AgentMotionless;
import com.utbm.info.vi51.buildingEvac.agent.AgentOld;
import com.utbm.info.vi51.buildingEvac.agent.AgentYoung;
import com.utbm.info.vi51.buildingEvac.environment.Chair;
import com.utbm.info.vi51.buildingEvac.environment.Desk;
import com.utbm.info.vi51.buildingEvac.environment.Wall;

public class FloorPanel extends JPanel implements GridStateChannelListener
{
	private final static int CELL_SIZE = 10;

	private final GridStateChannel channel;
	private final JaakEnvironment environment;
	
	
	private int width;
	private int height;
	
	private static final ImageIcon AGENT_BLIND_ICON = new ImageIcon("icons/agentBlind.png");
	private static final ImageIcon AGENT_OLD_ICON = new ImageIcon("icons/agentOld.png");
	private static final ImageIcon AGENT_YOUNG_ICON = new ImageIcon("icons/agentYoung.png");
	private static final ImageIcon AGENT_MOTIONLESS_ICON = new ImageIcon("icons/agentMotionless.png");
	private static final ImageIcon DEFAULT_ICON = new ImageIcon("icons/defaultIcon.png");

	
	public FloorPanel()
	{
		this.channel = null;
		this.environment = null;
	}
	
	
	
	public FloorPanel(GridStateChannel channel ,JaakEnvironment environment)
	{
		setBackground(new Color(255,255,255));
		this.channel = channel;
		this.channel.addGridStateChannelListener(this);
		this.width = this.channel.getGridWidth();
	
		this.height = this.channel.getGridHeight();
		this.environment = environment;
		//this.setPreferredSize(new Dimension(width, height));
	}
	


	/**
	 * 
	 */
    private static final long serialVersionUID = 266108554827406198L;


	@Override
    public void gridStateChanged()
    {
	    repaint();
	    
    }

	@Override
    public void jaakEnd()
    {
		this.channel.removeGridStateChannelListener(this);
		this.width = this.height = 0;

		repaint();   
    }

	@Override
    public void jaakStart()
    {
		repaint();
    }
	
	
	@Override
	public synchronized void paint(Graphics g)
	{
		// Standard drawing of the panel (mainly the background).
		super.paint(g);



		//Image image = FESTIVAL_GOER_ICON.getImage();
		
		
		AffineTransform identity = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;

		for (int x = 0; x < this.width; ++x)
		{
			for (int y = 0; y < this.height; ++y)
			{

				for (EnvironmentalObject eo : this.channel.getEnvironmentalObjects(x, y))
				{

					if (eo instanceof Chair)
					{
						g.setColor(new Color(247, 190, 129, 50)); // beige
						g.fillRoundRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE,
						        CELL_SIZE,3,3);
					}
					else if (eo instanceof Desk)
					{
						g.setColor(new Color(139,69,19)); // brun
						g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE,
						        CELL_SIZE);
						
					}
					else if (eo instanceof Wall)
					{
						System.out.println("x : " + x + " y : " + y + " Wall");
						g.setColor(new Color(46,46,46,10)); // gris fonc�
						g.fillRect(x *CELL_SIZE, y*CELL_SIZE , CELL_SIZE,   CELL_SIZE);
					}
					else
					{
						g.setColor(new Color(255,255,255)); // blanc
						g.fillRect(x *CELL_SIZE, y*CELL_SIZE , CELL_SIZE,   CELL_SIZE);
					}
					

				}

				if (this.channel.containsTurtle(x, y))
				{ // channel contient la grille de données
					// pour chaque agent, on dessine une image
					/*
					 * BufferedImage bufferedImage =
					 * ImageUtils.toBufferedImage(image); //bufferedImage =
					 * ImageUtils.changeColor(bufferedImage, 15); image =
					 * Toolkit
					 * .getDefaultToolkit().createImage(bufferedImage.getSource
					 * ());
					 */

					AffineTransform trans = new AffineTransform();
					trans.setTransform(identity);
					trans.translate(simu2screen_x(x), simu2screen_y(y)); // On
																		 // la
																		 // positionne
																		 // au
																		 // bon
																		 // endroit
																		 // x,y
					trans.rotate(this.channel.getOrientation(x, y) + Math.PI / 2); // Et on l'oriente dans son sens de marche

					Collection<TurtleBody> turtles = environment.getTurtles(x,y);
					for (TurtleBody tmpBody : turtles)
					{
						AgentID tmpData = (AgentID) tmpBody.getSemantic();
						
						//TODO : r�cup�rer le type d'agent associ� au turtlebody
						
						Image tmpImg;
						
						if (tmpData != null)
						{
							if (tmpData.getOwner() instanceof AgentBlind)
							{
								tmpImg = AGENT_BLIND_ICON.getImage();
								
							}
							else if (tmpData.getOwner() instanceof AgentMotionless)
							{
								tmpImg = AGENT_MOTIONLESS_ICON.getImage();
							}
							else if (tmpData.getOwner() instanceof AgentOld)
							{
								tmpImg = AGENT_OLD_ICON.getImage();
							}
							else if(tmpData.getOwner() instanceof AgentYoung)
							{
								tmpImg = AGENT_YOUNG_ICON.getImage();
							}
							else
							{
								tmpImg = DEFAULT_ICON.getImage();
							}
							
							g2d.drawImage(tmpImg, trans, this);
						}
					}
				}

			}
		}

	}
	
	private static int simu2screen_x(int x)
	{
		return x * CELL_SIZE + 5;
	}

	private static int simu2screen_y(int y)
	{
		return y * CELL_SIZE + 5;
	}
	
	public int getCELL_SIZE()
	{
		return CELL_SIZE;
	}

	public GridStateChannel getChannel()
	{
		return channel;
	}
	
	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public JaakEnvironment getEnvironment()
	{
		return environment;
	}


}
