package com.tdgame;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;


/**
 * 
 * To start double critters at the same time
 * @author TEAM 2
 *
 */
public class DoubleCritters implements CritterStrategy {
	
	/**
	 * Method used to start the wave of critters that includes both double and single row of critters.
	 */
	@Override
	public void startWave() {
		// TODO Auto-generated method stub
		Screen.waveType="Double";
		Screen.critters = new Critter[Screen.noOfCritters];
		Screen.critters2 = new Critter[Screen.noOfCritters];
		for(int i=0;i<Screen.critters.length;i++)
		{
			int random = new Random().nextInt(100);
			//Critter(			       	 imgWidth,  imgHeight, imgX, imgY, rectX, rectY, healthSpace, randomHealth)
			Screen.critters[i] = new Critter(50,      50,       25,   0,    -15,   -60,   12,         random, Screen.critterSpeed);
			Screen.critters2[i] = new Critter(50,     50,       10,  -15,     0,   -20,   0,          random, Screen.critterSpeed);
		}
		if(Screen.isFirst)
			Screen.crittersImgs[0] = new ImageIcon("../res/critter.gif").getImage();
		Screen.isFirst=false;
		
	}

}
