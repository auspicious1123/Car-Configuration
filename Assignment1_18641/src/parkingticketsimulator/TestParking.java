package parkingticketsimulator;

import parkingticketsimulator.ParkedCar;
/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */
public class TestParking {
	public static void main(String[] arg){
		// test case1 car1 parking time is not expired
	    System.out.println("This is test case 1: ");
		ParkedCar car1 = new ParkedCar("make1","model1","color1","license1",100);  // this car is not expired
		ParkingMeter meter1 = new ParkingMeter(150);
		ParkingTicket ticket1 = new ParkingTicket(car1);
		PoliceOfficer p1 = new PoliceOfficer("ticket01", "badge01"); 
		p1.issueTicket(car1, meter1,ticket1);   // call the method check the car whether is expired and give the issue ticket
		System.out.println();
		
		// test case2 car2 parking time is expired less than 1 hour
		System.out.println("This is test case 2: ");
		ParkedCar car2 = new ParkedCar("make2","model2","color2","license2",180);   // this car is expired
		ParkingTicket ticket2 = new ParkingTicket(car2);
		ParkingMeter meter2 = new ParkingMeter(150);
		PoliceOfficer p2 = new PoliceOfficer("ticket02", "badge02"); 
		p2.issueTicket(car2, meter2, ticket2);
		System.out.println();
		
		// test case3 car2 parking time is expired less than 1 hour
		System.out.println("This is test case 3: ");
		ParkedCar car3 = new ParkedCar("make3","model3","color3","license3",300);   // this car is expired
		ParkingTicket ticket3 = new ParkingTicket(car3);
		ParkingMeter meter3 = new ParkingMeter(150);
		PoliceOfficer p3 = new PoliceOfficer("ticket03", "badge03"); 
		p3.issueTicket(car3, meter3, ticket3);
		System.out.println();
	}
}
