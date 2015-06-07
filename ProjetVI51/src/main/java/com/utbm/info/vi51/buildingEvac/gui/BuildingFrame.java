package com.utbm.info.vi51.buildingEvac.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.kernel.agent.Kernels;

import com.utbm.info.vi51.buildingEvac.gui.listener.AlarmListener;

public class BuildingFrame extends JFrame 
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 8305560036282283539L;
    
    
    public BuildingFrame(FloorPanel panel[])
    {
    	if(panel != null && panel.length != 0 )
    	{
        	GridStateChannel channel = panel[0].getChannel();
        	
                	
        	setTitle("Building Evacuation");
        	getContentPane().setLayout(new BorderLayout());
        	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        	
        	addWindowListener(new Closer());
      
        	
        	JScrollPane scrollPane = new JScrollPane(panel[0]);
        	LogPanel logPanel = new LogPanel((10 + panel[0].getCELL_SIZE()
    		        * (panel[0].getWidth())) / 4, 10 + panel[0].getCELL_SIZE()
    		        * (panel[0].getHeight()));
        	
        	JPanel alarmPanel = new JPanel();
        	JButton alarm = new JButton("Alarm");
        	alarm.addActionListener(new AlarmListener());
        	alarmPanel.add(alarm);
        	
    		getContentPane().add(scrollPane, BorderLayout.CENTER);
    		getContentPane().add(alarmPanel, BorderLayout.LINE_START);
    		getContentPane().add(logPanel, BorderLayout.LINE_END);
    		
    		
    		//TODO : Voir pour cas plusieurs �tages
    		
    		setPreferredSize(new Dimension(10+panel[0].getCELL_SIZE()*(channel.getGridWidth()), 
					   10+panel[0].getCELL_SIZE()*(channel.getGridHeight())));
    		pack();
    	}
    	else
    	{
    		
    	}

    }
    
	private static class Closer extends WindowAdapter
	{

		public Closer()
		{
		}

		@Override
		public void windowClosing(WindowEvent event)
		{
			Kernels.killAll();
			System.exit(0);
		}
	}


}
