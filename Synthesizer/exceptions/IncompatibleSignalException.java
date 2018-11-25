package exceptions;

public class IncompatibleSignalException extends Exception {
	public IncompatibleSignalException(String why){
		super(why);
	}
}
