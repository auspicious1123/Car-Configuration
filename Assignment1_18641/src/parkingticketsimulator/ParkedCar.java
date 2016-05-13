package parkingticketsimulator;
/**
 * @author- Rui
 * JavaSmartPhoneHomework
 */
public class ParkedCar {
	private String make;
	private String model;
	private String color;
	private String licenseNumber;
	private int minutesOfParking;
	// constructor
	public ParkedCar(){ };
	public ParkedCar(String make, String model, String color, String licenseNumber, int minutesOfParking){
		this.make = make;
		this.model = model;
		this.color = color;
		this.licenseNumber = licenseNumber;
		this.minutesOfParking = minutesOfParking;
	}
	// setter and getter for make
	public void setMake(String make){
		this.make = make;
	}
	public String getMake(){
		return make;
	}
	// setter and getter for model
	public void setModel(String model){
		this.model = model;
	}
	public String getModel(){
		return model;
	}
	// setter and getter for color
	public void setColor(String color){
		this.color = color;
	}
	public String getColor(){
		return color;
	}
	// setter and getter for LicenseNumber
	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}
	public String getLicenseNumber(){
		return licenseNumber;
	}
	// setter and getter for number of minutes
	public void setMinutesOfParking(int minutesOfParking){
		this.minutesOfParking = minutesOfParking;
	}
	public int getMinutesOfParking(){
		return minutesOfParking;
	}
	// return the parameter of the parked car
//	public String[] getParameters(ParkedCar car){
//		String[] parameters = new String[4];
//		parameters[0] = car.make;
//		parameters[1] = car.model;
//		parameters[2] = car.color;
//		parameters[3] = car.licenseNumber;
//		return parameters;
//	}
	
}
