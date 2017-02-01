package spellchecker.content;

/** Encapsulates the property of an item to spell-check */
public class SpellCheckItem {

	// a word to spell-check
	private String token;

	// true if misspelled (not in a dictionary)
	private boolean isFlagged;

	// relative location from the beginning of the text
	private int offset;

	public SpellCheckItem(String token, int offset) {
		this.token = token;
		this.offset = offset;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void flag(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "SpellCheckItem [token=" + token + ", isFlagged=" + isFlagged + ", offset=" + offset + "]";
	}

}
