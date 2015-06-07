package com.utbm.info.vi51.buildingEvac.launcher;

import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.janusproject.jaak.environment.model.JaakEnvironment;
import org.janusproject.jaak.kernel.JaakKernel;
import org.janusproject.jaak.kernel.JaakKernelController;
import org.janusproject.jaak.spawner.JaakSpawner;
import org.janusproject.kernel.logger.LoggerUtil;

import com.utbm.info.vi51.buildingEvac.agent.Agent;
import com.utbm.info.vi51.buildingEvac.environment.Environment;
import com.utbm.info.vi51.buildingEvac.gui.BuildingFrame;
import com.utbm.info.vi51.buildingEvac.gui.FloorPanel;

public class Simulator
{

	private Agent[] agent;

	//private Environment environment;


		public static void main(String[] args) 
		{
			
			// Change the level of the messages displayed on the console
			LoggerUtil.setGlobalLevel(Level.WARNING);
	 
	    // Step 1: create the Jaak grid environment.
	    
	    JaakEnvironment environment = Environment.createEnvironment();
	 
	    // Step 2: create the spawner, i.e. the entry of the festival.
	    
	    JaakSpawner spawner = Environment.createSpawner(environment);
	    
	    // Step 3: initialize the Jaak kernel.
	   
	   JaakKernelController controller = JaakKernel.initializeKernel(environment, spawner);
	    controller.getTimeManager().setWaitingDuration(100);
            
	    // Step 4: create the UI.
	    FloorPanel panel[] = new FloorPanel[1];
	    panel[0] = Environment.createPanel(controller.getKernelAddress(),environment);
	    JFrame frame = new BuildingFrame(panel);
	    frame.setVisible(true);
	
		
	}

}
