import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Person{
	
	private String firstName;
	private String lastName;
	private Boolean isMale;
	private HashMap satisfaction;
	private LinkedList<String> livingHistory;
	private int preference; //0 is preference for full rural, 9 is full city
	private static int minSatisfact = 1;
	private static int maxSatisfact = 100;
	
	public Person(String first, String second){
		isMale = false;
		if ((int)(Math.random() * 2) == 1){
			isMale = true;
		}
		firstName = first;
		lastName = second;
		preference = (int) (Math.random() * 10);
		satisfaction = new HashMap<String, Integer>(Execute.towns.length);
		for(int i = 0; i < Execute.towns.length; i++){
			satisfaction.put(Execute.towns[i].getName(), 50);
		}
		livingHistory = new LinkedList<String>();
	}
	
	/**
	 * Determines if the Person should move from their current Town
	 * @return true if the Person should move
	 */
	public boolean shouldMove(){
		int variable = (int) (Math.random() * (int) (satisfaction.get(livingHistory.peek())));
		if (variable < 5){
			return true;
		}
		return false;
	}
	
	/**
	 * Handles updating the satisfaction ratings of all towns
	 * @param currentTown - the town currently inhabited 
	 * @return
	 */
	public boolean updateSatisfaction(Town currentTown){
		boolean improvedSatisfaction = updateCurrentCitySatisfaction(currentTown);
						
		for(int i = 0; i < Execute.towns.length; i++){
			if(Execute.towns[i].getName() != currentTown.getName()){
				String tempTown = Execute.towns[i].getName();
				if ((int) satisfaction.get(tempTown) < 50){
					satisfaction.put(tempTown, (int) (satisfaction.get(tempTown)) + 1);
				}
				else if ((int) satisfaction.get(tempTown) > 50){
					satisfaction.put(tempTown, (int) (satisfaction.get(tempTown)) - 1);
				}
			}
		}
		
		return improvedSatisfaction;
	}
	
	/**
	 * Handles updating the satisfaction value of the town currently inhabited by the Person
	 * @param currentTown - false if the satisfaction rating has decreased
	 */
	public boolean updateCurrentCitySatisfaction(Town currentTown){
		boolean improvedSatisfaction = true;
		int satistfactMod = Math.abs(preference - 5) + 3;
		
		int currentTownSatisfact = (int) (satisfaction.get(currentTown.getName()));
		if (currentTown.isCity && preference > 5){
			satisfaction.put(currentTown.getName(),
					currentTownSatisfact + (int)(Math.random() * satistfactMod) - 1);
		}
		else if(!currentTown.isCity && preference < 5){
			satisfaction.put(currentTown.getName(),
					currentTownSatisfact + (int)(Math.random() * satistfactMod) - 1);
		}
		else{
			if (preference != 5){
				satisfaction.put(currentTown.getName(),
						currentTownSatisfact - (int)(Math.random() * satistfactMod) + 1);
			}
		}
		if (currentTownSatisfact > (int) (satisfaction.get(currentTown.getName()))){
			improvedSatisfaction = false;
		}
		applyOverpopulationPenalty(currentTown);
		
		return improvedSatisfaction;
	}
	
	/**
	 * Determines if an over-population penalty to satisfaction must be applied, and applies it if so.
	 * @param currentTown, the town currently inhabited by the Person
	 */
	public void applyOverpopulationPenalty(Town currentTown){
		int currentTownSatisfact = (int) (satisfaction.get(currentTown.getName()));
		if(currentTown.isOverPopulated()){
			int overPopulationPenalty = (int) (Math.random() * 5);
			satisfaction.put(currentTown.getName(),
					currentTownSatisfact - overPopulationPenalty);
		}
	}
	
	/**
	 * Ensures that the satisfaction value for the currently inhabited town is between minSatisfact and maxSatisfact, inclusive.
	 * @param currentTown
	 */
	public void normalizeSatisfaction(Town currentTown){
		int currentTownSatisfact = (int) (satisfaction.get(currentTown.getName()));
		if(currentTownSatisfact > maxSatisfact){
			satisfaction.put(currentTown.getName(), maxSatisfact);
		}
		if(currentTownSatisfact < minSatisfact){
			satisfaction.put(currentTown.getName(), minSatisfact);
		}
	}
	
	/**
	 * Updates the Person's history to reflect their move
	 * @param townName - the town to which the Person is moving
	 */
	public void moveToTown(String townName){
		livingHistory.addFirst(townName);
	}
	
	public String toString(){
		String value = firstName + " " + lastName + " is a ";
		if (isMale){
			value += "man";
		}
		else{
			value += "woman";
		}
		int satisfact = getSatisfaction(livingHistory.peek());
		if (satisfact >= minSatisfact){
			value += ", currently living in " + livingHistory.peek() + " with a satisfaction of: " + satisfact;
		}
		else{
			value += ", is not currently living in a town";
		}
		value += ", and has a city preference of " + preference;
		return value;
	}
	
	public void printHistory(){
		System.out.println(firstName + " " + lastName + " has lived in:");
		for(int i = 0; i < livingHistory.size(); i++){
			System.out.println(livingHistory.get(i));
		}
	}
	
	public boolean isMale(){
		return isMale;
	}
	
	/**
	 * Returns the satisfaction rating associated with the specific town
	 * @param townName - the name of the town to check
	 * @return an integer between minSatisfact and maxSatisfact, inclusive
	 */
	public int getSatisfaction(String townName){
		if (satisfaction.containsKey(townName)){
			return (int) satisfaction.get(townName);
		}
		return -1;
	}
}