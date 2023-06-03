package Exceptions;

@SuppressWarnings("serial")
public class InvalidOperationException extends Exception {
	public InvalidOperationException(String msg) {
		super(msg);
	}
}
