package com.mygdx.wiresgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Circle extends Shape implements Affectable{
	private int radius = 0;
	
	public Circle() {
		this(new Vector2(), 10);
	}
	
	public Circle(Vector2 pixelPos, int radius) {
		super(pixelPos, "Circle", 10);
		this.radius = radius;
	}

	@Override
	public void draw(GridMouseUtils mouseUtils, WiresGame game, Vector2 gridOffset) {		
		if (poweredOn) {
			game.sr.set(ShapeType.Filled);
			game.sr.setColor(Color.YELLOW);
		}
		else {
			game.sr.set(ShapeType.Line);
			game.sr.setColor(Color.RED);
		}
		game.sr.circle(super.getPixelPos1(mouseUtils).x - gridOffset.x, WiresGame.ORIGINAL_SCREEN_HEIGHT - super.getPixelPos1(mouseUtils).y - gridOffset.y, radius);
	}

	@Override
	public void drawToolBar(GridMouseUtils mouseUtils, WiresGame game, ToolBar toolbar, int boxNum) {
		game.sr.setColor(Color.BLACK);
		game.sr.set(ShapeType.Line);
		game.sr.circle(toolbar.calcXBoxCenter(boxNum), toolbar.calcYBoxCenter(), 10);
		
	}

}
