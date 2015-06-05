package domino.logic;

import java.io.Serializable;


public class Extremity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int value;
	private boolean linkStatus;
	
	public Extremity(int value, boolean linkStatus) {
		this.value = value;
		this.linkStatus = linkStatus;
	}

	public final int getValue() {
		return value;
	}

	public final boolean isLinkStatus() {
		return linkStatus;
	}

	public final void setLinkStatus(boolean linkStatus) {
		this.linkStatus = linkStatus;
	}
	
}
