package Exceptions;

@SuppressWarnings("serial")
public class EmptyStackException extends Exception{
	public EmptyStackException(String msg) {
		super(msg);
	}
}