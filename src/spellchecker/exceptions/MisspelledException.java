package spellchecker.exceptions;

public class MisspelledException extends RuntimeException {
	public MisspelledException() {
		super();
	}

	public MisspelledException(String message) {
		super(message);
	}

}
