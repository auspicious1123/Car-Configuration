package coin;
/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */
public class Coin {
	public String sideUp ;
	// non-arg constructor that randomly determine the side of coin face up 
	public Coin(){ 
		sideUp = getSideUp();
	}
	// method toss that simulates the tossing of a coin
	public void toss(){ 
		int side = Math.random() > 0.5 ? 1 : 0;    
		if(side == 0) 
			sideUp = "heads";
		else
			sideUp = "tails";
	}
	// method getSideUp return the value of the sideUp filed
	public String getSideUp(){  
		toss();
		return sideUp;
	}
}
