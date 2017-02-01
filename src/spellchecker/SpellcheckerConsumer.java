package spellchecker;

import java.util.concurrent.BlockingQueue;

import spellchecker.content.Dictionary;
import spellchecker.content.SpellCheckItem;

public class SpellcheckerConsumer implements Runnable {

	private BlockingQueue<SpellCheckItem> todoQueue;
	private BlockingQueue<SpellCheckItem> doneQueue;
	
	private Dictionary dictionary;

	public SpellcheckerConsumer(BlockingQueue<SpellCheckItem> toDoQueue, BlockingQueue<SpellCheckItem>  doneQueue, Dictionary dictionary) {
		this.todoQueue = toDoQueue;
		this.doneQueue = doneQueue;
		this.dictionary = dictionary;
	}

	@Override
	public void run() {
		while (true) {
			SpellCheckItem item = null;
			try {
				item = (SpellCheckItem) todoQueue.take();
				String word = item.getToken();
				item.flag(dictionary.isMissed(word));
				System.out.println("Spellchecker.performSpellcheck() checked " + word);
				doneQueue.put(item);
			} catch (InterruptedException e) {
			}

		}

	}

}
