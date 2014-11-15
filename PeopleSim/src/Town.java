import java.util.Vector;

public class Town{
	private String name;
	private int maxPop = 0;
	private int currentPop = 0;
	private Vector<Person> inhabitants;
	public boolean isCity;
	
	public Town(String name){
		this.name = name;
		inhabitants = new Vector<Person>();
		if ((int)(Math.random() * 4) == 1){
			isCity = true;
		}
		
		if(isCity){
			maxPop = (int) (Math.random() * 500) + 20;
		}
		else{
			maxPop = (int) (Math.random() * 100) + 10;
		}
	}
	
	/**
	 * Adds a Person to this town's inhabitants
	 * @param person the person to add
	 * @return returns false if the city is over-populated
	 */
	public Boolean addInhabitant(Person person){
		inhabitants.add(person);
		person.moveToTown(this.name);
		currentPop++;
		if (currentPop <= maxPop){
			return true;
		}
		else{
			//System.out.println("Too many people in " + name + ", max is " + maxPop + ", current is " + currentPop + "!");
			return false;
		}
	}
	
	/**
	 * Handles updating the state of all of this Town's inhabitants 
	 */
	public void updateTown(){
		for(int i = 0; i < inhabitants.size() ; i++){
			inhabitants.get(i).updateSatisfaction(this);
			inhabitants.get(i).normalizeSatisfaction(this);
			if (inhabitants.get(i).shouldMove()){
				Town newTown = this;
				while (newTown == this){
					int rand = (int)(Math.random() * Execute.towns.length);
					newTown = Execute.towns[rand];
				}
				newTown.addInhabitant(inhabitants.get(i));
				currentPop--;
				this.inhabitants.remove(i);
			}
		}
	}	
	
	/**
	 * Returns true if this Town is overpopulated
	 * @return
	 */
	public boolean isOverPopulated(){
		return (maxPop < currentPop);
	}
	
	public String getName(){
		return name;
	}
	
	public void printInfo(){
		System.out.printf(name);
		if (this.isCity){
			System.out.println(" is a city");
		}
		else{
			System.out.println(" is a small town");
		}
		System.out.println("Current population: " + currentPop + " Max population: " + maxPop);
		if (inhabitants.size() != 0){
			System.out.println("Inhabitants: ");
			for( int i = 0; i < inhabitants.size(); i++){
				Person temp = inhabitants.get(i);
				System.out.println(temp.toString());
			}
		}
		else{
			System.out.println("No Inhabitants");
		}
	}
	
	public void printStats(){
		int totalInhabitants = this.inhabitants.size();
		double maleCount = 0;
		double satisfactionTotal = 0;
		
		for(int i = 0; i < this.inhabitants.size(); i++){
			if (this.inhabitants.get(i).isMale()){
				maleCount++;
			}
			satisfactionTotal += this.inhabitants.get(i).getSatisfaction(this.name);
		}
		
		if (this.isCity){
			System.out.println(this.name + " is a city that is:");
		}
		else{
			System.out.println(this.name + " is a small town that is:");
		}
		
		System.out.println(maleCount / totalInhabitants * 100 + 
				"% male, with an average satisfaction rating of: " +
				satisfactionTotal / totalInhabitants);
	}
	
}