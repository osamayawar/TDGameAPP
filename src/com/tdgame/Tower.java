package com.tdgame;

import java.awt.Image;
import java.util.Random;

import javax.swing.JButton;

/** Tower Interface (or Abstract class) that is the super-type of all 
 * types of objects produced by the TowerFactory. 
 *
 */
public abstract class Tower extends JButton{

	public String imageFile = "";
	public String imgPath="";
	public Image image=null;
	public int id=0;
	public int cost=0;
	public int range=0;
	public int ammunition=0;
	public String type="";
	public int refundRate=0;
	public int costToAddAmmunition=0;
	public int rateOfFire=0;
	public int xPosInTowerMap=0;
	public int yPosInTowerMap=0;
	public int actualAmmunition=0;
	public int critterRadius = 0;
	public Critter[] blackListedCritters = null;
	public int towerRadius = 0;
	public double sqOfDistanceOfCritterFromTower;
	public int totalTargetEnemies;
	public double minDistance;
	public int attackStrategy=1;
	public String strategyName="NEARESTTOTOWERCRITTER";
	TowerFire towerFire=new TowerFire();
	void fire() {
	}

	public void chngStrategy(String strategy){
		strategyName=strategy;
		switch(strategy){
		case "NEARESTTOTOWERCRITTER":attackStrategy=1;break;
		case "NEARESTTOENDPOINTCRITTER":attackStrategy=2;break;
		case "STRONGESTCRITTER":attackStrategy=3;break;
		case "WEAKESTCRITTER":attackStrategy=4;break;
		case "RANDOM":attackStrategy=0;break;
		default:break;
		}
	}
	public void setTowerProperties(int id, int cost, int ammunition, int range,
			String type, int rateOfFire, String path, int damageToCritters, int level) {
		this.id = id;
		this.ammunition=ammunition;
		this.actualAmmunition=ammunition;
		this.cost = cost;
		this.range = range;
		this.type=type;
		this.refundRate=(int)((cost*25)/100);
		this.rateOfFire=rateOfFire;
		this.costToAddAmmunition=(int)((cost*25)/100);
		this.imgPath=path;

		this.damageToCritters=damageToCritters;
		this.level=level;
		this.costToIncreaseLevel = (int)((cost*50)/100);
		//this.attackStrategy = strategy;
	}
	public void setSplashDamageEffect(){
		this.splashEffect = true;
	}
	public void increaseAmmunition(int in_ammunition) {
		ammunition+=in_ammunition;
	}
	public void setPosition(int xPos, int yPos) {
		xPosInTowerMap=xPos;
		yPosInTowerMap=yPos;
	}
	public int getCost() {
		return cost;
	}
	public int getRange() {
		return range;
	}
	public int getAmmunition() {
		return ammunition;
	}
	public String getType() {
		return type;
	}
	public int getRefundRate() {
		return refundRate;
	}
	public int getCostToAddAmmunition() {
		return costToAddAmmunition;
	}
	public int getRateOfFire() {
		return rateOfFire;
	}
	public int getActualAmmunition() {
		return actualAmmunition;
	}
	public int getXPosInTowerMap() {
		return xPosInTowerMap;
	}
	public int getYPosInTowerMap() {
		return yPosInTowerMap;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void decreaseAmmunition(int in_ammunition) {
		ammunition-=in_ammunition;
	}
	public int getTowerLevel() {
		return level;
	}
	public int getCostToIncreaseLevel() {
		return costToIncreaseLevel;
	}
	public void increaseLevel(){
		this.range++;
		if((this.rateOfFire % 2) == 0){
			this.rateOfFire +=  (int)((this.rateOfFire*50)/100) ;
		} else {
			this.rateOfFire += ((int)(this.rateOfFire*50)/100) + 1;
		}
		this.level++;
	}

	public boolean shootingThread = false;

	public int damageToCritters = 0;
	public int level=0;
	public int costToIncreaseLevel=0;

	//public int attackTime = 0; //Lifetime of the attack(bullet or bomb) on the map/screen
	public int attackDelay = 0;//time delay between each attack(time for reloading bullets when finished)

//	public int maxAttackTime = 10;
	public int maxAttackDelay = 200;
	public int dradius =0;

	public Critter targetCritter;
	public boolean splashEffect = false;


	public Critter findTargetCritter(Critter[] critters, int towerXPos, int towerYPos) {
		if(critters!=null){
			System.out.println("In findTargetCritters...\ncritters!=null\t" + (critters!=null));
			blackListedCritters = new Critter[critters.length];
			System.out.println("After BlackListedCritters");
			towerRadius = this.range;
			double critterXPos = 0;
			double critterYPos = 0;
			for(int i=0; i<critters.length; i++){
				System.out.println(critters[i]);
				if(critters[i] != null)
				{
					System.out.println("inside if");
					critterXPos = critters[i].x;
					critterYPos = critters[i].y;
					System.out.println("CritterX\tCritterY\n" + critterXPos + "\t" + critterYPos);
					
					double dx = (critterXPos - towerXPos*50)/50;
					double dy = (critterYPos - towerYPos*50)/50;
					
					System.out.println("Critter distanceX\tdistanceX\n" + dx + "\t" + dy);
					
					dradius = towerRadius + critterRadius;
					
					System.out.println("radius (towerRadius - critterRadius)\n" + towerRadius + " + " + critterRadius + " = " + (towerRadius + critterRadius));
					System.out.println("enemy at index\t" + i);
					//int tempmayur=critters[i].x-(towerXPos*50)-50;
					//if(tempmayur< 50){
					sqOfDistanceOfCritterFromTower = (dx*dx) + (dy*dy);
//					System.out.println("OSAMA OSAMA OSAMA" + sqOfDistanceOfCritterFromTower);
					if(sqOfDistanceOfCritterFromTower < (dradius * dradius)){
						System.out.println(i + "in range...adding to eInRange list");
						System.out.println("critterx crittery\n" + critters[i].x + "\t" + critters[i].y);
						blackListedCritters[i] = critters[i];
						blackListedCritters[i].distanceOfBlackListedCritter = sqOfDistanceOfCritterFromTower;
					}else{
						if(critters[i].towerFixed == true){
							critters[i].damageTime = 20;
						}
						critters[i].towerFixed = false;
						
						System.out.println(i + "not in range...");
					}
				}
				
				
				
			}
			
			if(attackStrategy == Screen.RANDOMCRITTER){
				//System.exit(0);
				towerFire.setFireStrategy(new RandomFire());
				return towerFire.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
			} else if(attackStrategy == Screen.STRONGESTCRITTER){
				System.out.println("Strongest Strategy");
				towerFire.setFireStrategy(new StrongestCritter());
				return towerFire.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
			} else if(attackStrategy == Screen.WEAKESTCRITTER){
				towerFire.setFireStrategy(new WeakestCritter());
				return towerFire.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
			} else if(attackStrategy == Screen.NEARESTTOTOWERCRITTER){
				towerFire.setFireStrategy(new NearToTower());
				return towerFire.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
			} else if(attackStrategy == Screen.NEARESTTOENDPOINTCRITTER){
				towerFire.setFireStrategy(new NearToEnd());
				return towerFire.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
			}
		} else {
			return null;
		}
		
		return null;
	}
	/*@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void setObserver(Screen screen) {
		// TODO Auto-generated method stub
	}*/

	

	public int getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}

	

	public int getMaxAttackDelay() {
		return maxAttackDelay;
	}

	public void setMaxAttackDelay(int maxAttackDelay) {
		this.maxAttackDelay = maxAttackDelay;
	}

	public void towerAttack(int x, int y, Critter critter){
		System.out.println("Reducing health...\nOriginal\t" + critter.health +"\nNow\t" + (critter.health-this.getDamageToCritters()));
		critter.health-=this.getDamageToCritters();
		Screen.user.player.money += this.getDamageToCritters()*100;
	}

	public int getDamageToCritters() {
		return damageToCritters;
	}

	public void setDamageToCritters(int damageToCritters) {
		this.damageToCritters = damageToCritters;
	}

	public Critter getTargetCritter() {
		return targetCritter;
	}

	public void setTargetCritter(Critter targetCritter) {
		this.targetCritter = targetCritter;
	}
	
	// method added by sumeet
	public void setTowerStrategy(String strategy) {
		switch (strategy)
		{
			case "NEARESTTOTOWERCRITTER":
				this.attackStrategy = 1;
				this.strategyName = "NEARESTTOTOWERCRITTER";
				break;
			case "NEARESTTOENDPOINTCRITTER": 
				this.attackStrategy = 2;	
				this.strategyName = "NEARESTTOENDPOINTCRITTER";
				break;//nearest critter to the end point
			case "STRONGESTCRITTER":
				this.attackStrategy = 3; 
				this.strategyName="STRONGESTCRITTER";
				break;//Critter with max health------ missile & tank
			case "WEAKESTCRITTER":
				this.attackStrategy = 4;
				this.strategyName = "WEAKESTCRITTER";
				break;//Critter with min health ----- laser & fire
			case "RANDOMCRITTER":
				this.attackStrategy = 5;
				this.strategyName = "RANDOMCRITTER";
				break;
		}
	}
	
	public String getTowerStrategy(){
		return strategyName;
	}

}

