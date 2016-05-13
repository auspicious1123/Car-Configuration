package parkingticketsimulator;

/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */  
public class PoliceOfficer { 
		// 1. To know the name and badge number
		private String ticketName;
		private String badgeNumber;
		
		public PoliceOfficer(){};
		public PoliceOfficer(String ticketName, String badgeNumber){
			this.ticketName = ticketName;
			this.badgeNumber = badgeNumber;
		}
	
		// set parameter
		public void setTicketName(String ticketName){
			this.ticketName = ticketName;
		}
		public void setBadgeNumber(String badgeNumber){
			this.badgeNumber = badgeNumber;
		}
		//get parameter
		public void getTicketName(){
			System.out.println("This issue ticket name is :" + this.ticketName);
		}
		public void getBadgeNumber(){
			System.out.println("The issue ticket badge numebr is "+ this.badgeNumber);
		}
		// 2. examine whether a car's time has been expired
		public boolean examineCar(ParkedCar car, ParkingMeter meter){
			int parkingTime = car.getMinutesOfParking();
			int purchasedTime = meter.setPurchasedTime(100);  // set purchased time
			if(parkingTime > purchasedTime){
				return true;
			}else{
				return false;
			}
		}
		// 3. issue a parking ticket
		public void issueTicket(ParkedCar car, ParkingMeter meter,ParkingTicket ticket){
			if(examineCar(car, meter)){
				System.out.println("This car is expired");
				ticket.illegalCarFine(car, meter);
				ticket.illegalCarParameters(car);
				setBadgeNumber(this.badgeNumber);
				setTicketName(this.ticketName);
				ticket.reprtPoliceOffice(this);
			}else{
				System.out.println("This car is not expired");
			}
		}
}


