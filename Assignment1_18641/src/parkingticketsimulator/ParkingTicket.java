package parkingticketsimulator;

import parkingticketsimulator.ParkedCar;

/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */
public class ParkingTicket { // task for this class is to report the make, model, color and license number of an illegally parked car
	public ParkingTicket(ParkedCar car){
		
	}
	// 1. report the make, model, color and license number of an illegally parked car
	public void illegalCarParameters(ParkedCar car){
		System.out.print("This illegally car parameters are: ");
		System.out.print(car.getMake() + " ");
		System.out.print(car.getModel() + " ");
		System.out.print(car.getColor() + " ");
		System.out.println(car.getLicenseNumber());
		//return car.getMake() + car.getModel() + car.getColor() + car.getLicenseNumber();	
	}
	// 2. report the illegal car fine
	public void illegalCarFine(ParkedCar car,ParkingMeter meter){
		double money;
		double overTime;
		overTime = (car.getMinutesOfParking() - meter.getPurchasedTime())/ 60 ;
		if(overTime <= 1){
			System.out.println("This illegally car fine is:" + 25);
		}else{
			money = 25 + (overTime - 1) * 10;
			System.out.println("This illegally car fine is:" + money);
		}
	}
	//3. report ticket name and badge number of the police officer issuing the ticket.
	public void reprtPoliceOffice(PoliceOfficer office){
		office.getTicketName();
		office.getBadgeNumber();
	}
}
