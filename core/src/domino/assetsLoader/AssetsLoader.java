package domino.assetsLoader;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import domino.logic.Rotation;

public class AssetsLoader {
	
	public static HashMap<String, Image> imageTable = new HashMap<String, Image>();
	
	// pieces positions and size
	public static Integer PIECE_WIDTH = 30;
	public static Integer PIECE_HEIGHT = 60;
	public static Integer X_INIT_POSITION = Gdx.graphics.getWidth()/2;
	public static Integer Y_INIT_POSITION = Gdx.graphics.getHeight()/2;
	public static Integer X_INCREMENT_POSITION = 72;
	
	// texture atlas and skin
	
	public static TextureAtlas atlasPieces, atlasButton;
	public static Skin skinPieces, skinButton;

	// playgame images

	public static Texture texturePlayTable;
	public static Sprite spritePlayTable;
	
	// pieces

	public static Image p11;
	public static Image p12;
	public static Image p13;
	public static Image p14;
	public static Image p15;
	public static Image p16;
	public static Image p22;
	public static Image p23;
	public static Image p24;
	public static Image p25;
	public static Image p26;
	public static Image p33;
	public static Image p34;
	public static Image p35;
	public static Image p36;
	public static Image p44;
	public static Image p45;
	public static Image p46;
	public static Image p55;
	public static Image p56;
	public static Image p66;
	public static Image p00;
	public static Image p01;
	public static Image p02;
	public static Image p03;
	public static Image p04;
	public static Image p05;
	public static Image p06;

	public static void load() {
		
		// texture atlas and skin
		
		atlasPieces = new TextureAtlas("ui/atlas_pieces.pack");
		atlasButton = new TextureAtlas("ui/atlas.pack");
		skinPieces = new Skin(Gdx.files.internal("ui/piecesSkin.json"), atlasPieces);
		skinButton = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlasButton);
		
		
		// play game images
		
		texturePlayTable = new Texture(Gdx.files.internal("backgroundPic/table.png"));
		spritePlayTable = new Sprite(texturePlayTable);
		spritePlayTable.flip(false, true);
		
		// pieces
		
		p11 = new Image(skinPieces, "11");
		setImageSize(p11);
		imageTable.put("p11", p11);
		p12 = new Image(skinPieces, "12");
		setImageSize(p12);
		imageTable.put("p12", p12);
		p13 = new Image(skinPieces, "13");
		setImageSize(p13);
		imageTable.put("p13", p13);
		p14 = new Image(skinPieces, "14");
		setImageSize(p14);
		imageTable.put("p14", p14);
		p15 = new Image(skinPieces, "15");
		setImageSize(p15);
		imageTable.put("p15", p15);
		p16 = new Image(skinPieces, "16");
		setImageSize(p16);
		imageTable.put("p16", p16);
		p22 = new Image(skinPieces, "22");
		setImageSize(p22);
		imageTable.put("p22", p22);
		p23 = new Image(skinPieces, "23");
		setImageSize(p23);
		imageTable.put("p23", p23);
		p24 = new Image(skinPieces, "24");
		setImageSize(p24);
		imageTable.put("p24", p24);
		p25 = new Image(skinPieces, "25");
		setImageSize(p25);
		imageTable.put("p25", p25);
		p26 = new Image(skinPieces, "26");
		setImageSize(p26);
		imageTable.put("p26", p26);
		p33 = new Image(skinPieces, "33");
		setImageSize(p33);
		imageTable.put("p33", p33);
		p34 = new Image(skinPieces, "34");
		setImageSize(p34);
		imageTable.put("p34", p34);
		p35 = new Image(skinPieces, "35");
		setImageSize(p35);
		imageTable.put("p35", p35);
		p36 = new Image(skinPieces, "36");
		setImageSize(p36);
		imageTable.put("p36", p36);
		p44 = new Image(skinPieces, "44");
		setImageSize(p44);
		imageTable.put("p44", p44);
		p45 = new Image(skinPieces, "45");
		setImageSize(p45);
		imageTable.put("p45", p45);
		p46 = new Image(skinPieces, "46");
		setImageSize(p46);
		imageTable.put("p46", p46);
		p55 = new Image(skinPieces, "55");
		setImageSize(p55);
		imageTable.put("p55", p55);
		p56 = new Image(skinPieces, "56");
		setImageSize(p56);
		imageTable.put("p56", p56);
		p66 = new Image(skinPieces, "66");
		setImageSize(p66);
		imageTable.put("p66", p66);
		p00 = new Image(skinPieces, "00");
		setImageSize(p00);
		imageTable.put("p00", p00);
		p01 = new Image(skinPieces, "01");
		setImageSize(p01);
		imageTable.put("p01", p01);
		p02 = new Image(skinPieces, "02");
		setImageSize(p02);
		imageTable.put("p02", p02);
		p03 = new Image(skinPieces, "03");
		setImageSize(p03);
		imageTable.put("p03", p03);
		p04 = new Image(skinPieces, "04");
		setImageSize(p04);
		imageTable.put("p04", p04);
		p05 = new Image(skinPieces, "05");
		setImageSize(p05);
		imageTable.put("p05", p05);
		p06 = new Image(skinPieces, "06");
		setImageSize(p06);
		imageTable.put("p06", p06);

	}
	
	public static void setImageSize(Image image) {
		image.setSize(PIECE_WIDTH, PIECE_HEIGHT);
		image.setOrigin(PIECE_WIDTH/2, PIECE_HEIGHT/2);
	}
	
	public static void setImagePosition(Image image, float xi,  float yi, Rotation rotation) {
	
		image.setPosition(xi, yi, Align.center);
		
		if(rotation == Rotation.NORTH)
			image.rotateBy(0);
		else if(rotation == Rotation.EAST)
			image.rotateBy(-90);
		else if(rotation == Rotation.SOUTH)
			image.rotateBy(180);
		else if(rotation == Rotation.WEST)
			image.rotateBy(90);
	}
	
	public static void dispose() {
		
		atlasPieces.dispose();
		skinPieces.dispose();
		atlasButton.dispose();
		skinButton.dispose();
		texturePlayTable.dispose();
		spritePlayTable.getTexture().dispose();
		
	}

}
