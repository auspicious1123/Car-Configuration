package coin;
import coin.Coin;

public class TestCoin {
	public static void main(String[] arg){
		// create an instance and display the initial side 
		Coin  a = new Coin(); 
		System.out.println("The initial side is " + a.sideUp); 
		
		int countHeads = 0; // use for counting the times of heads
		int countTails = 0; // use for counting the times of tails
		
		for(int i = 1; i <= 20; i++){
			a.toss();
			System.out.println("The " + i + "th toss is " + a.getSideUp());
			if(a.getSideUp() == "heads"){
				countHeads++;
			}else{
				countTails++;
			}
		}
		System.out.println("The number of times heads is " + countHeads);
		System.out.println("The number of times tails is " + countTails);
	}
}
