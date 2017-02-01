package spellchecker.content.factory;

import java.util.List;

import spellchecker.content.*;
import spellchecker.util.IOUtils;

/**
 * Load a dictionary from a file that contains a list of words
 */
public class DictionaryFromFileFactory implements DictionaryAbstractFactory {

	// TODO: read from a property, or use Spring framework to inject the value
	// at runtime
	private String dictionaryRelativeFilePath = "dictionary.txt";// "dictionary-full.txt";

	@Override
	public Dictionary getDictionary() {
		List<String> terms = IOUtils.readFileAllLines(dictionaryRelativeFilePath);
		Dictionary dictionary = new DictionaryBasicImpl(terms);
		return dictionary;
	}

	@Override
	public void saveDictionary(Dictionary dictionary) {
		IOUtils.writeFile(dictionaryRelativeFilePath, dictionary.print());
	}

}
