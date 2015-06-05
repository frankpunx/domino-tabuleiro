package domino.logic;

import java.io.Serializable;



/**
 * Este tipo enumerado indica qual a orientacao da peca. Mais concretamente, indica qual a orientacao do primeiro valor da peca
 * @author ADC
 *
 */
public enum Rotation implements Serializable {

	// Rotacao de 0 graus
	NORTH, 
	
	// Rotacao de 90 graus
	EAST, 
	
	// Rotacao de 180 graus
	SOUTH, 
	
	// Rotacao de 270
	WEST;

	
	public static final Rotation calculateRotation(Rotation r, int nRots) {
		//nRots = nRots < 0? nRots + 4 : nRots;
		int newRot = (r.ordinal() + nRots) % 4;

		return Rotation.values()[newRot];
	}
	
	public static final Rotation getOpositeRotation(Rotation r) {
		return calculateRotation(r, 2);
	}
}
