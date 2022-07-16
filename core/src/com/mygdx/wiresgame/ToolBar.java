package com.mygdx.wiresgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class ToolBar {
	private float height;
	private float width;
	private float singleBoxWidth;

	private int highlightedBoxNum = 0;

	WiresGame game;
	GridMouseUtils mouseUtils;

	Shape[] toolbarShapes = {new Circle(), new HorizontalLine(), new VerticalLine(), new Lever()};
	
	public ToolBar(WiresGame game, GridMouseUtils mouseUtils) {
		this.game = game;
		this.mouseUtils = mouseUtils;

		height = WiresGame.ORIGINAL_SCREEN_HEIGHT / 10 + 1;
		width = WiresGame.ORIGINAL_SCREEN_WIDTH;
		singleBoxWidth = width / 12;
	}

	public void drawToolBar() {
		// white out space
		game.sr.setColor(Color.WHITE);
		game.sr.set(ShapeType.Filled);
		game.sr.rect(1, 1, width - 1, height);

		// draw outline box
		game.sr.setColor(Color.BLACK);
		game.sr.set(ShapeType.Line);
		game.sr.rect(1, 1, width - 1, height);

		// draw dividing lines
		float currentlyDrawing = singleBoxWidth;
		while (currentlyDrawing < WiresGame.ORIGINAL_SCREEN_WIDTH) {
			game.sr.line(new Vector2(1 + currentlyDrawing, 1), new Vector2(1 + currentlyDrawing, height));
			currentlyDrawing += singleBoxWidth;
		}

		highlightBox();
		
		// draw items
		int i = 1;
		for(Shape shape : toolbarShapes) {
			shape.drawToolBar(mouseUtils, game, this, i);
			++i;
		}

	}

	public void updateHightlightBox(int change) {
		if (highlightedBoxNum + change < 0 || highlightedBoxNum + change > 11)
			return;

		highlightedBoxNum += change;
	}

	private void highlightBox() {
		if (highlightedBoxNum == 11) {
			game.sr.setColor(Color.RED);
			game.sr.set(ShapeType.Line);
			game.sr.rect(1 + highlightedBoxNum * singleBoxWidth, 1f, singleBoxWidth - 1, height);
		} else {
			game.sr.setColor(Color.RED);
			game.sr.set(ShapeType.Line);
			game.sr.rect(1 + highlightedBoxNum * singleBoxWidth, 1f, singleBoxWidth, height);
		}
	}

	public float calcXBoxCenter(int boxNum) {
		return (1 + boxNum * singleBoxWidth) + singleBoxWidth / 2;
	}

	public float calcYBoxCenter() {
		return height / 2;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getSingleBoxWidth() {
		return singleBoxWidth;
	}

	public int getHighlightedBoxNum() {
		return highlightedBoxNum;
	}
}
