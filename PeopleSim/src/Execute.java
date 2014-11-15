import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Execute{
	static Town[] towns = new Town[6];
	static Vector<Person> people;
	static Vector<String> firstNames;
	static Vector<String> lastNames;
	
	public static void main(String[] args){
		initialize();
		System.out.println("--------Begin Original Stats--------");
		//outputPeopleHistory();
		//outputTownInfo();
		outputTownStats();
		System.out.println("--------End Original Stats----------\n");
		for(int i = 0; i < 20; i++){
			updateTick();
			//outputStats();
		}
		//outputPeopleHistory();
		//outputTownInfo();
		outputTownStats();
	}
	
	public static void updateTick(){
		for(int i = 0; i < towns.length; i++){
			towns[i].updateTown();
		}
	}
	
	public static void initialize(){
		firstNames = new Vector<String>();
		lastNames = new Vector<String>();
		people = new Vector<Person>();
		readInNames();
		towns[0] = new Town("City of Townsville");
		towns[1] = new Town("Dinkleberg");
		towns[2] = new Town("Hopscotch");
		towns[3] = new Town("Cerulean");
		towns[4] = new Town("Test Town, please ignore");
		towns[5] = new Town("Applesauce");
		
		for(int i = 0; i < 500; i++){
			int firstName = (int)(Math.random() * firstNames.size());
			int lastName = (int)(Math.random() * lastNames.size());
			Person temp = new Person(firstNames.get(firstName), lastNames.get(lastName));
			people.add(temp);
			int choice = (int) (Math.random() * towns.length);
			if (!towns[choice].addInhabitant(temp)){
				break;
			}
		}
	}
	
	public static void readInNames(){
		try {
			BufferedReader firstIn = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/First_Names.csv"));
			while (firstIn.ready()) {
			  String s = firstIn.readLine();
			  firstNames.add(s);
			}
			firstIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			BufferedReader secondIn = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/Last_Names.csv"));
			while (secondIn.ready()) {
			  String s = secondIn.readLine();
			  lastNames.add(s);
			}
			secondIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void outputPeopleHistory(){
		for(int i = 0; i < people.size(); i++){
			people.get(i).printHistory();
			System.out.println();
		}
	}
	
	public static void outputTownInfo(){
		for(int i = 0; i < towns.length; i++){
			towns[i].printInfo();
			System.out.println();
		}
	}
	
	public static void outputTownStats(){
		for(int i = 0; i < towns.length; i++){
			towns[i].printStats();
			System.out.println();
		}
	}
}