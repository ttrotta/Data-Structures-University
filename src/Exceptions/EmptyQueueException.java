package Exceptions;

@SuppressWarnings("serial")
public class EmptyQueueException extends Exception{
	public EmptyQueueException(String msg) {
		super(msg);
	}
}