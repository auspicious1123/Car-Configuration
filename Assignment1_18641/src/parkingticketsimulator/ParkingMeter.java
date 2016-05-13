package parkingticketsimulator;
/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */
public class ParkingMeter {  // to know the number of minutes of parking time that has be purchased
	private int purchasedTime;
	public ParkingMeter(){}
	public ParkingMeter(int purchasedTime){
		this.purchasedTime = purchasedTime;
	}
	public int setPurchasedTime(int purchasedTime){
		return purchasedTime;
	}
	public int getPurchasedTime(){
		return purchasedTime;
	}
}
