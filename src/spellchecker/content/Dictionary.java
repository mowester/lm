package spellchecker.content;

public interface Dictionary {

	void addToDictionary(String word);

	boolean isMissed(String word);
	
	long getSize();

	String print();
	



}
