package com.mygdx.wiresgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Lever extends Shape implements Toggleable {
	private float width;
	private float height;
	
	public Lever() {
		this(new Vector2());
	}

	public Lever(Vector2 pixelPos1) {
		super(pixelPos1, "Lever", 10);
	}

	@Override
	public void draw(GridMouseUtils mouseUtils, WiresGame game, Vector2 gridOffset) {
		float cornerX = (super.getPixelPos1(mouseUtils).x - mouseUtils.getDeltaX() / 4) - gridOffset.x;
		float cornerY = (WiresGame.ORIGINAL_SCREEN_HEIGHT - super.getPixelPos1(mouseUtils).y - mouseUtils.getDeltaY() / 4) - gridOffset.y;
		width = mouseUtils.getDeltaX() / 2;
		height = mouseUtils.getDeltaY() / 2;
		
		game.sr.setColor(Color.RED);
		game.sr.set(ShapeType.Line);
		// outer box
		game.sr.rect(cornerX, cornerY, width, height);
		// lever box
		if (poweredOn) {
			game.sr.rect(cornerX - (width / 4), cornerY + (height / 3), width + 1 - width / 3, height / 3);
		}
		else {
			game.sr.rect(cornerX + (width / 2), cornerY + (height / 3), width - width / 3, height / 3);
		}
	}

	@Override
	public void drawToolBar(GridMouseUtils mouseUtils, WiresGame game, ToolBar toolbar, int boxNum) {
		width = mouseUtils.getDeltaX() / 2;
		height = mouseUtils.getDeltaY() / 2;
		
		game.sr.setColor(Color.BLACK);
		game.sr.set(ShapeType.Line);
		// outer box
		game.sr.rect(toolbar.calcXBoxCenter(4) - width / 2, toolbar.calcYBoxCenter() - height / 2, width, height);
		
		//powered on lever box
		game.sr.rect(toolbar.calcXBoxCenter(4) - (width / 4) - width / 2, toolbar.calcYBoxCenter() + (height / 3) - height / 2, width + 1 - width / 3, height / 3);
	}
	
	//part of Toggleable
	public void togglePower() {
		if(poweredOn)
			poweredOn = false;
		else
			poweredOn = true;
	}

}
