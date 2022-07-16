package com.mygdx.wiresgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/*
A line class to store the line data after it has been drawn

*/
abstract public class Line extends Shape implements Affectable, ExtendsPower {
	protected Line(Vector2 v1, Vector2 v2, String name) {
		super(v1, v2, name, 0);
	}

	protected Color getDrawColor() {
		if (poweredOn)
			return Color.YELLOW;
		else
			return Color.RED;
	}
}
