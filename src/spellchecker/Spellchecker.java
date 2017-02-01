package spellchecker;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import spellchecker.content.Content;
import spellchecker.content.Dictionary;
import spellchecker.content.SpellCheckItem;
import spellchecker.content.factory.DictionaryAbstractFactory;
import spellchecker.content.factory.DictionaryFromFileFactory;

//Facade or Service class
public class Spellchecker {

	private Content content;
	private Dictionary dictionary;
	private DictionaryAbstractFactory dictionaryFactory;

	private BlockingQueue<SpellCheckItem> todoQueue = new LinkedBlockingDeque<SpellCheckItem>();
	private BlockingQueue<SpellCheckItem> doneQueue = new LinkedBlockingDeque<SpellCheckItem>();

	private int threadNumber = 5;

	public Spellchecker() {
		dictionaryFactory = new DictionaryFromFileFactory();
		content = new Content();
		dictionary = dictionaryFactory.getDictionary();
	}

	public void performSpellcheck() {
		System.out.println("Spellchecker.performSpellcheck() ..");

		for (int i = 0; i < threadNumber; i++) {
			new Thread((new SpellcheckerConsumer(todoQueue, doneQueue, dictionary))).start();
		}

		for (SpellCheckItem item : getSpellCheckItems()) {
			publishSpellCheckItem(item);
		}

		int wordsCount = content.getWordsCount();
		while (wordsCount > 0) {
			receiveSpellCheckItem();
			wordsCount--;
		}
	}

	private void publishSpellCheckItem(SpellCheckItem item) {
		try {
			todoQueue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private SpellCheckItem receiveSpellCheckItem() {
		SpellCheckItem item = null;
		try {
			item = doneQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return item;
	}

	public void addToDictionary(String word) {
		dictionary.addToDictionary(word);
	}

	public void saveDictionary() {
		dictionaryFactory.saveDictionary(dictionary);
	}

	public void saveContent(String contentLines) {
		content.saveContent(contentLines);
	}

	public SpellCheckItem findSpellCheckItemByOffset(int offset) {
		return content.findSpellCheckItemByOffset(offset);
	}

	public String getContentAsString() {
		return content.getContentAsString();
	}

	public Collection<SpellCheckItem> getSpellCheckItems() {
		return content.getSpellCheckItems();
	}

}
