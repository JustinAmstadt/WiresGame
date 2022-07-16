package com.mygdx.wiresgame;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WiresGame extends Game{
	public final static float SCALE = 32f;
	public final static float INV_SCALE = 1.f / SCALE;
	public final static float VP_WIDTH = 1280 * INV_SCALE;
	public final static float VP_HEIGHT = 720 * INV_SCALE;
	public final static int ORIGINAL_SCREEN_HEIGHT = 480;
	public final static int ORIGINAL_SCREEN_WIDTH = 640;
	
	private int currentScreenHeight = ORIGINAL_SCREEN_HEIGHT;
	private int currentScreenWidth = ORIGINAL_SCREEN_WIDTH;

	ShapeRenderer sr;
	ExtendViewport viewport;
	OrthographicCamera camera;
	
	Stage toolbarStage;
	Viewport toolbarStageViewport;
	
	SpriteBatch batch;
	BitmapFont font;
	HashMap<String, Texture> textures = new HashMap<String, Texture>();

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		textures.put("Circuit", new Texture("circuit.png"));
		textures.put("HOnPipe", new Texture("HOnPipe.png"));
		textures.put("HOffPipe", new Texture("HOffPipe.png"));
		textures.put("VOnPipe", new Texture("VOnPipe.png"));
		textures.put("VOffPipe", new Texture("VOffPipe.png"));
		textures.put("Power", new Texture("Power.png"));
		
		camera = new OrthographicCamera();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
		setScreen(new GridScreen(this));
		
	}

	@Override
	public void resize(int width, int height) {
		// viewport must be updated for it to work properly
		System.out.println("Width: " + width + ", Height: " + height);
		
		currentScreenHeight = height;
		currentScreenWidth = width;
		
		viewport.update(ORIGINAL_SCREEN_WIDTH, ORIGINAL_SCREEN_HEIGHT, true);
	}
	
	public int getCurrentScreenWidth() {
		return currentScreenWidth;
	}
	
	public int getCurrentScreenHeight() {
		return currentScreenHeight;
	}
	
	@Override
	public void dispose() {
		sr.dispose();
		batch.dispose();
		
		//dispose all textures in hash map
		for(String i : textures.keySet()) {
			textures.get(i).dispose();
		}
	}
	
	
}
