package spellchecker.content;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Multithreading only for reads, adding a new word is from a single UI's client
 * thread so there are no synchronizations needed.
 * 
 * Basic implementaion - case insencitive.
 */
public class DictionaryBasicImpl implements Dictionary {

	// read operation are more often than writes -> dictionary ordered
	private Map<String, String> dictionary = new TreeMap<String, String>();

	// TODO use strategy (upper case / as is, )

	public DictionaryBasicImpl(List<String> terms) {
		for (String term : terms) {
			dictionary.put(term, term);
		}
	}

	@Override
	public void addToDictionary(String word) {
		String tmp = word.toLowerCase();
		if (dictionary.containsKey(tmp)) {
			// do nothing
		} else {
			dictionary.put(tmp, tmp);
		}
	}

	@Override
	public boolean isMissed(String word) {
		boolean isMissed = true;
		String key = word.toLowerCase();
		if (dictionary.containsKey(key)) {
			isMissed = false;
		} else {
			isMissed = true;
		}
		//System.out.println(word + " is missEd ? " + isMissed);
		return isMissed;
	}

	@Override
	public long getSize() {
		return dictionary.size();
	}

	@Override
	public String print() {
		StringBuffer tmp = new StringBuffer();
		for (String word : dictionary.values()) {
			tmp.append(word);
			tmp.append("\n");
		}
		return tmp.toString();
	}

}
