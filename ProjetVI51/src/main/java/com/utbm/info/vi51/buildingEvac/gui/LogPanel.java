package com.utbm.info.vi51.buildingEvac.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class LogPanel extends JPanel
{

	/**
	 * 
	 */
    private static final long serialVersionUID = -1886170021084154743L;
    private JTextArea logArea;
    


	public LogPanel(int width, int height)
    {
		//setPreferredSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width -10, height -10));
    	this.logArea = new JTextArea("");
    	this.logArea.setEditable(true);
    	this.logArea.setWrapStyleWord(true);
    	this.logArea.setAutoscrolls(true);
    	
    	this.logArea.setMinimumSize(new Dimension(width -10, height -10));
    	//this.logArea.setColumns(100);
    	this.logArea.setRows(100);
    	
    	this.addText("Console : ");
    	
    	for(int i = 0;i<500;i++)
    	{
    		this.addText("test");
    	}
 
    	this.add(this.logArea);
    	

    	
    }
    
    public LogPanel(JTextArea txt)
    {
    	this.logArea = txt;
    }
    
    public JTextArea getLogArea()
	{
		return logArea;
	}

	public void setLogArea(JTextArea logArea)
	{
		this.logArea = logArea;
	}
	
	public void addText(String text)
	{
		String Newligne=System.getProperty("line.separator"); 
		this.logArea.append(text + Newligne);
	}
    
    

}
