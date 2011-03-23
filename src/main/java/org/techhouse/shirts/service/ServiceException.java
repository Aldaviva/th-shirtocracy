package org.techhouse.shirts.service;


public abstract class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceException() {
		super();
	}
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public static final class DeadlinePassedException extends ServiceException {
		private static final long serialVersionUID = 1L;
	}

}
