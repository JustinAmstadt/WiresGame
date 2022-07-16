package com.mygdx.wiresgame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Shape implements Comparable<Shape> {
	private Vector2 pos1; // grid position
	private Vector2 pos2; // grid position
	protected boolean poweredOn = false;
	private String name = new String();
	protected int compareKey;

	protected Shape(Vector2 pos1, String name, int compareKey) {
		this(pos1, pos1, name, compareKey);
	}

	protected Shape(Vector2 pos1, Vector2 pos2, String name, int compareKey) {
		this.pos1 = pos1.cpy();
		this.pos2 = pos2.cpy();
		this.name = String.valueOf(name);
		this.compareKey = compareKey;
	}

	public abstract void draw(GridMouseUtils mouseUtils, WiresGame game, Vector2 gridOffset);

	public abstract void drawToolBar(GridMouseUtils mouseUtils, WiresGame game, ToolBar toolbar, int boxNum);

	public Vector2 getPixelPos1(GridMouseUtils mouseUtils) {
		Vector2 pixelPos = new Vector2();

		pixelPos.x = pos1.x * mouseUtils.getDeltaX();
		pixelPos.y = pos1.y * mouseUtils.getDeltaY();

		return pixelPos;
	}

	public Vector2 getPixelPos2(GridMouseUtils mouseUtils) {
		Vector2 pixelPos = new Vector2();

		pixelPos.x = pos2.x * mouseUtils.getDeltaX();
		pixelPos.y = pos2.y * mouseUtils.getDeltaY();

		return pixelPos;
	}

	public Vector2 getPos1() {
		return pos1;
	}

	public Vector2 getPos2() {
		return pos2;
	}

	public String getName() {
		return String.valueOf(name);
	}

	@Override
	public String toString() {
		return this.name + ", vec1: " + pos1.toString() + ", vec2: " + pos2.toString();
	}

	@Override
	public int compareTo(Shape o) {
		Integer shape1 = new Integer(this.compareKey);
		Integer shape2 = new Integer(o.compareKey);
		return shape1.compareTo(shape2);
	}

	@Override
	public boolean equals(Object obj) {
		// if the coordinates are the same and the name is the same
		if (this.getPos1().x == ((Shape) obj).getPos1().x && this.getPos1().y == ((Shape) obj).getPos1().y
				&& this.getPos2().x == ((Shape) obj).getPos2().x && this.getPos2().y == ((Shape) obj).getPos2().y
				&& this.name.equals(((Shape) obj).name)) {
			System.out.println("Object comparison was true");
			return true;
		} else
			System.out.println("Object comparison was false");
			return false;
	}
}
