package spellchecker.content;

public class SpellCheckItem {

	private String token;
	private boolean isChecked;
	private boolean isFlagged;
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

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void flag(boolean isFlagged) {
		this.isFlagged= isFlagged;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "SpellCheckItem [token=" + token + ", isChecked=" + isChecked + ", isFlagged=" + isFlagged + ", offset="
				+ offset + "]";
	}

}
