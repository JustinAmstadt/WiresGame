package com.mygdx.wiresgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GridScreen extends ScreenAdapter {
	public static final int GRID_SIZE = 16;

	GridMouseUtils mouseUtils;
	WiresGame game;
	ToolBar toolbar;
	private Board board;
	private Vector2[] newLine = new Vector2[2];

	private Vector2 lastTouch = new Vector2(0, 0); // last time a user pressed on their mouse
	private Vector2 gridOffset = new Vector2(0, 0);

	private ArrayList<Integer> keysPressed = new ArrayList<>();

	private boolean toggleCoordinates = true;

	public GridScreen(WiresGame game) {
		this.game = game;
		board = new Board(game);
		mouseUtils = new GridMouseUtils(game);
		toolbar = new ToolBar(game, mouseUtils);

		mouseUtils.setDeltaX(40);
		mouseUtils.setDeltaY(30);
		mouseUtils.setLeftVertLine(mouseUtils.getDeltaX());
		mouseUtils.setTopHorLine(mouseUtils.getDeltaY());
	}

	@Override
	public void render(float delta) {
//		if(game.getCurrentScreenHeight() > game.ORIGINAL_SCREEN_HEIGHT + gridOffset.y) {
//			gridOffset.y += game.getCurrentScreenHeight() - game.ORIGINAL_SCREEN_HEIGHT;
//		}
//		System.out.println("Grid Offset Y: " + gridOffset.y);
		
		ScreenUtils.clear(1, 1, 1, 1);
		game.sr.begin(ShapeRenderer.ShapeType.Line);
		game.sr.setColor(1, 0, 0, 1);

		drawGrid();
		board.renderShapes(mouseUtils, gridOffset);
		toolbar.drawToolBar();

		game.sr.end();

		game.batch.begin();
		drawCoordinates();
		game.batch.end();

	}

	private void drawGrid() {
		int xLoc = ((int) Math.floor(gridOffset.x / mouseUtils.getDeltaX()) + 1) * mouseUtils.getDeltaX()
				- (int) gridOffset.x; // gets pixel pos of first vertical wall on left
//		System.out.println("xLoc: " + xLoc);

		// vertical lines
		while (xLoc < game.getCurrentScreenWidth()) {
			game.sr.line(xLoc, 1, xLoc, game.getCurrentScreenHeight(), Color.BLACK, Color.BLACK);
			xLoc += mouseUtils.getDeltaX();
//			System.out.println("xLoc:" + xLoc);
		}

		// System.out.println(Math.floor(gridOffset.x / deltaX)); //offset by grid line
		// from origin horizontal

		int yLoc = ((int) Math.floor(gridOffset.y / mouseUtils.getDeltaY()) + 1) * mouseUtils.getDeltaY()
				- (int) gridOffset.y;
//		System.out.println("yLoc: " + yLoc);

		while (yLoc < game.getCurrentScreenHeight()) {
			game.sr.line(1, yLoc, game.getCurrentScreenWidth(), yLoc, new Color(0, 0, 0, 1), new Color(0, 0, 0, 1));
			yLoc += mouseUtils.getDeltaY();
		}
	}

	private void drawCoordinates() {
		if (toggleCoordinates) {
			game.font.setColor(Color.BLACK);
			game.font.draw(game.batch, "X: " + mouseUtils.getNearestXLine(), 20, game.ORIGINAL_SCREEN_HEIGHT - 20);
			game.font.draw(game.batch, "Y: " + mouseUtils.getNearestYLine(), 20, game.ORIGINAL_SCREEN_HEIGHT - 35);
		}
	}

	private void placeShapeOnGridPoint() {

		switch (toolbar.getHighlightedBoxNum()) {
		case 0:
			break;
		case 1:
			// circle
			board.addShape(new Circle(new Vector2(mouseUtils.getNearestXLine(), mouseUtils.getNearestYLine()), 10));
			break;
		case 2:
			// horizontal line
			Vector2 hv1 = new Vector2(mouseUtils.getNearestXLine(), mouseUtils.getNearestYLine());
			Vector2 hv2 = new Vector2(hv1.x + 1, hv1.y);
			board.addShape(new HorizontalLine(hv1, hv2));
			break;
		case 3:
			// vertical line
			Vector2 vv1 = new Vector2(mouseUtils.getNearestXLine(), mouseUtils.getNearestYLine());
			Vector2 vv2 = new Vector2(vv1.x, vv1.y - 1);
			board.addShape(new VerticalLine(vv1, vv2));
			break;
		case 4:
			// lever
			board.addShape(new Lever(new Vector2(mouseUtils.getNearestXLine(), mouseUtils.getNearestYLine())));
		}

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				keysPressed.add(keycode);

				switch (keycode) {
				case Input.Keys.F3:
					if (toggleCoordinates)
						toggleCoordinates = false;
					else
						toggleCoordinates = true;
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {

				keysPressed.remove(Integer.valueOf(keycode));

				return true;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// ignore if its not left mouse button or first touch pointer
				if (button != Input.Buttons.LEFT || pointer > 0)
					return false;

//				System.out.print("Down: X: " + screenX + ", Y: " + screenY + ", Pointer: " + pointer + ", Button: "
//						+ button + "\n");

				placeShapeOnGridPoint();
				board.toggleShapes(mouseUtils);

				lastTouch.set(screenX, screenY);

				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if (button != Input.Buttons.LEFT || pointer > 0)
					return false;

//				System.out.print("Up: X: " + screenX + ", Y: " + screenY + ", Pointer: " + pointer + ", Button: " + button + "\n");

				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				Vector2 newTouch = new Vector2(screenX, screenY);
				// delta will now hold the difference between the last and the current touch
				// positions
				// delta.x > 0 means the touch moved to the left, delta.x < 0 means a move to
				// the right
				Vector2 delta = new Vector2(lastTouch.x - newTouch.x, 0); // lastTouch - newTouch
				delta.y = newTouch.y - lastTouch.y;
				lastTouch = newTouch;

				gridOffset.add(delta);

				if (gridOffset.x < 0)
					gridOffset.x = 0;

				mouseUtils.calcMouseMovedVals(screenX, screenY, gridOffset);

				// System.out.println("Grid Offset: " + gridOffset.toString());

				return true;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				mouseUtils.calcMouseMovedVals(screenX, screenY, gridOffset);
				return true;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				toolbar.updateHightlightBox((int) amountY);
				return true;
			}
		});
	}
}
