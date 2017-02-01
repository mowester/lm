package spellchecker.content.factory;

import spellchecker.content.Dictionary;

//TODO: replace by abstract method?
public interface DictionaryAbstractFactory {
	
	Dictionary getDictionary();
	
	void saveDictionary(Dictionary dictionary );

}
