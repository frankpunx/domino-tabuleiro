package domino.logic;

import java.io.Serializable;


public class Piece implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8587803717805764828L;
	private Extremity firstExtremity;
	private Extremity secondExtremity;
	private String pieceString;

	private Rotation rotation;

	public Piece(int first, int second, Rotation rot) {

		if(first + second > 12 || first > second)
			throw new IllegalArgumentException("Invalid piece: [" + first + ", " + second + "]");

		this.firstExtremity = new Extremity(first, false);
		this.secondExtremity = new Extremity(second, false);

		rotation = rot;
		pieceString = "p" + first + second;
	}

	public final Extremity getFirstExtremity() {
		return firstExtremity;
	}

	public final Extremity getSecondExtremity() {
		return secondExtremity;
	}

	public final Rotation getRotation() {
		return rotation;
	}
	
	public final String getPieceString() {
		return pieceString;
	}

	public final void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	public final void setExtremityLinkedStatus(boolean firstExtremityLinked, boolean secondExtremityLinked) {

		this.firstExtremity.setLinkStatus(firstExtremityLinked);
		this.secondExtremity.setLinkStatus(secondExtremityLinked);
	}

	public final boolean isDoubleValues() {
		return firstExtremity.getValue() == secondExtremity.getValue();
	}

	public final boolean isLinkable(Piece p) {

		return (!firstExtremity.isLinkStatus() && (firstExtremity.getValue() == p.firstExtremity.getValue()|| firstExtremity.getValue() == p.secondExtremity.getValue())) || 
				(!secondExtremity.isLinkStatus() && (secondExtremity.getValue() == p.firstExtremity.getValue() || secondExtremity.getValue() == p.secondExtremity.getValue()));	
	}

	public final int getAvailableValue() {

		if(firstExtremity.isLinkStatus() && secondExtremity.isLinkStatus())
			return -2;

		else if(firstExtremity.isLinkStatus())
			return firstExtremity.getValue();

		else if(secondExtremity.isLinkStatus())
			return secondExtremity.getValue();

		else
			return -1;
	}
	
	public Piece clone() {
		return new Piece(firstExtremity.getValue(), secondExtremity.getValue(), rotation);
	}

	public String toString() {

		StringBuilder str = new StringBuilder();

		if(this.rotation == Rotation.WEST) {
			str.append(new Pair(firstExtremity.getValue(), secondExtremity.getValue()).toString());

		} else if(this.rotation == Rotation.EAST) {
			str.append(new Pair(secondExtremity.getValue(), firstExtremity.getValue()).toString());

		} else 
			System.err.println("Erro de rotacao");

		return str.toString();
		//return (rotation == Rotation.WEST? new Pair(firstExtremity.getValue(), secondExtremity.getValue()).toString() : new Pair(secondExtremity.getValue(), firstExtremity.getValue()).toString());// + " " + rotation + " first: " + firstExtremity.isLinkStatus() + ", second: " + secondExtremity.isLinkStatus();
	}
}
