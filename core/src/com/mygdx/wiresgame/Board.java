package com.mygdx.wiresgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/*
Constructors:
ShapeRenderer - Creates empty object and adds the ShapeRenderer

getLineList - Gets the ArrayList that holds the Line objects
renderLineLists - Iterates through the Line list and renders them


*/
public class Board {
	HashMap<String, ArrayList<Shape>> shapeMap = new HashMap<>();
	WiresGame game;

	public Board(WiresGame game) {
		this.game = game;
	}

	public HashMap<String, ArrayList<Shape>> getShapeMap() {
		return shapeMap;
	}

	public void addShape(Shape shape) {
		String key1 = "" + (int) shape.getPos1().x + " " + (int) shape.getPos1().y;
		String key2 = "" + (int) shape.getPos2().x + " " + (int) shape.getPos2().y;
		addShape(shape, key1);
		addShape(shape, key2);
	}

	public void addShape(Shape shape, String key) {

		if (!shapeMap.containsKey(key)) { // makes an arraylist if one doesn't exist already
			shapeMap.put(key, new ArrayList<Shape>());
		}

		if (shapeMap.get(key).isEmpty()) { // adds a new shape if empty
			System.out.println("Key: " + key + " added shape: " + shape.toString());
			shapeMap.get(key).add(shape);
		}
		// arraylist is not empty and needs to be sorted and compared to make sure i'm
		// not overwriting what I don't want
		else {
			Collections.sort(shapeMap.get(key), Collections.reverseOrder());
			if (!doesShapeExist(shapeMap.get(key), shape)) {
				shapeMap.get(key).add(shape);
				System.out.println("Key: " + key + ", added shape in non empty arraylist: " + shape.toString());
				Collections.sort(shapeMap.get(key), Collections.reverseOrder());
			} else {
				System.out.println("Returned Key: " + key + " added shape: " + shape.toString());
				return;
			}
		}
	}
//	(shapeMap.get(key).get(0) instanceof Line == true || shapeMap.get(key).get(0).compareKey != shape.compareKey)
//	&& !doesLineExist(shapeMap.get(key), shape)

	private boolean doesShapeExist(ArrayList<Shape> list, Shape shape) {

		for (Shape item : list) {
//			System.out.println(item.toString());
			if (((Shape) item).equals(shape)) {
				System.out.println("Exists");
				return true;
			}
		}
		System.out.println("Doesn't exist");
		return false;
	}

	public void renderShapes(GridMouseUtils mouseUtils, Vector2 gridOffset) {
		// custom shapes
		for (HashMap.Entry<String, ArrayList<Shape>> entry : shapeMap.entrySet()) {
			ArrayList<Shape> value = entry.getValue();

			for (Shape item : value)
				item.draw(mouseUtils, game, gridOffset);
//		    System.out.println(key + " " + value);
		}
	}

	public void toggleShapes(GridMouseUtils mouseUtils) {
		String key = "" + mouseUtils.getNearestXLine() + " " + mouseUtils.getNearestYLine();

		if (shapeMap.get(key) == null)
			return;

		if (shapeMap.get(key).get(0) instanceof Toggleable) {

			((Toggleable) shapeMap.get(key).get(0)).togglePower();
			changeAdjPowerStates(key, shapeMap.get(key).get(0).poweredOn); // Toggleable should always be in the first
																			// position
		}
	}

	public void changeAdjPowerStates(String key, boolean changePowerTo) {
		// nothing except toggleable object exists, so return
		// avoids a NullPointerException
		if (shapeMap.get(key).size() == 1) {
//			System.out.println("Size was 1");
			return;
		}

		int yChange = 0;
		int xChange = 0;

		HashMap<String, Integer> visited = new HashMap<>();

		// original location
		changeAdjPowerStates(key, xChange, yChange, visited, changePowerTo);
	}

	public void changeAdjPowerStates(String key, int xChange, int yChange, HashMap<String, Integer> visited,
			boolean changePowerTo) {
		String[] tokens = key.split(" ");
		int xPos = Integer.parseInt(tokens[0]);
		int yPos = Integer.parseInt(tokens[1]);
		String newKey = new String("" + (xPos + xChange) + " " + (yPos + yChange));

//		System.out.println("Key: " + key);

		if (shapeMap.get(newKey) == null || visited.get(newKey) != null)
			return;

		visited.put(newKey, 1);

		for (int i = 0; i < shapeMap.get(newKey).size(); i++) {
			for (int j = 0; j < shapeMap.get(key).size(); j++) {
				if (shapeMap.get(newKey).get(i) instanceof Affectable
						&& shapeMap.get(newKey).get(i).poweredOn != changePowerTo
						&& shapeMap.get(key).get(j).equals(shapeMap.get(newKey).get(i))) { //third line not functional
					shapeMap.get(newKey).get(i).poweredOn = changePowerTo;
//				System.out.println("power changed");
				}
			}
		}

		changeAdjPowerStates(newKey, 1, 0, visited, changePowerTo);
		changeAdjPowerStates(newKey, -1, 0, visited, changePowerTo);
		changeAdjPowerStates(newKey, 0, 1, visited, changePowerTo);
		changeAdjPowerStates(newKey, 0, -1, visited, changePowerTo);
	}
}
