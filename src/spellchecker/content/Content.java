package spellchecker.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spellchecker.util.IOUtils;

public class Content {
	
	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	private String contentRelativeFilePath = "content.txt";

	private List<String> contentLines;

	protected Map<Integer, SpellCheckItem> content = new HashMap<Integer, SpellCheckItem>();

	public Content() {
		contentLines = loadContent();
		buildContent();
	}

	public void buildContent() {
		String contentString = getContentAsString();
		String[] tmp = contentString.split("\\W+");

		int fromIndex = contentString.length() - 1;
		String contentStringTmp = contentString;

		for (int i = tmp.length - 1; i >= 0; i--) {
			String word = tmp[i];
			int offset = contentStringTmp.lastIndexOf(word, fromIndex);
			SpellCheckItem item = new SpellCheckItem(word, offset);
			System.out.println(item);
			content.put(Integer.valueOf(offset), item);
			contentStringTmp = contentStringTmp.substring(0, offset);
		}
	}

	public SpellCheckItem findSpellCheckItemByOffset(int offset) {
		return content.get(offset);
	}

	public synchronized void isFlagged(int offset, boolean flag) {
		content.get(offset).flag(flag);
	}

	public String getContentAsString() {
		String content = listToString(contentLines);
		return content;
	}

	public String listToString(List<String> contentLines) {
		StringBuilder b = new StringBuilder();
		for (String line : contentLines) {
			b.append(line);
			b.append(LINE_SEPARATOR);
		}
		return b.toString();
	}


	protected List<String> loadContent() {
		List<String> contentLines = IOUtils.readFileAllLines(contentRelativeFilePath);
		return contentLines;
	}

	public void saveContent(String contentLines) {
		System.out.println("Saving new content:\n" + contentLines);
		IOUtils.writeFile(contentRelativeFilePath, contentLines);
	}

	public Map<Integer, SpellCheckItem> getContent() {
		return content;
	}

}
