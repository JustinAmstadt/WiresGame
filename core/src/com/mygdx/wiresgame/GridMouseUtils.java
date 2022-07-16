package com.mygdx.wiresgame;

import com.badlogic.gdx.math.Vector2;

public class GridMouseUtils {
	private int deltaX;
	private int deltaY;
	private int leftVertLine;
	private int topHorLine;
	private int nearestXLine;
	private int nearestYLine;
	private int leftXLine = 0;
	private int topYLine = 1;
	
	WiresGame game;
	
	public GridMouseUtils(WiresGame game) {
		this.game = game;
	}
	
	public void calcMouseMovedVals(int screenX, int screenY, Vector2 gridOffset) {
		//leftmost vertical line
		leftVertLine = (int) Math.floor(gridOffset.x / deltaX + 1) * deltaX - (int)gridOffset.x;
		
		//gives you the vertical line that your mouse is nearest to
		nearestXLine = (screenX - leftVertLine + (deltaX / 2)) / deltaX + (int)Math.floor(gridOffset.x / deltaX) + 1;
		
		//topmost horizontal line
		topHorLine = (int) Math.floor(gridOffset.y / deltaY + 1) * deltaY - (int)gridOffset.y;
		topHorLine = deltaY - topHorLine;
		
		//gives you the horizontal line that your mouse is nearest to
		nearestYLine = (screenY - topHorLine + (deltaY / 2)) / deltaY + (int)Math.floor(-gridOffset.y / deltaY) + 1;
		
		leftXLine = (int) Math.floor(gridOffset.x / deltaX + 1);
		topYLine = -(int) Math.floor(gridOffset.y / deltaY - 1) - 1;
		
		//Whenever the grid and the screen align, the nearest y line add one for some reason
		if(gridOffset.y % deltaY == 0) {
			nearestYLine--;
			topHorLine = deltaY;
			topYLine++;
		}
		
		//System.out.println("nearestXLine: " + nearestXLine + ", leftVertLine: " + leftVertLine + ", nearestYLine: " 
		//+ nearestYLine + ", topHorLine: " + topHorLine + ", Left X Line: " + leftXLine + ", topYLine: " + topYLine);
		
	}
	
	public int getPixelPosOfNearestXLine(Vector2 gridOffset) {
		int pixelX = leftVertLine + Math.abs(Math.abs(nearestXLine) - Math.abs(leftXLine)) * deltaX;
		//pixelX += gridOffset.x % deltaX;
		pixelX += gridOffset.x;
		
		if(pixelX < 0)
			pixelX += deltaX;
		return pixelX;
	}
	
	public int getPixelPosOfNearestYLine(Vector2 gridOffset) {
		int pixelY = topHorLine + Math.abs(Math.abs(topYLine) - Math.abs(nearestYLine)) * deltaY;
		//pixelY -= gridOffset.y % deltaY;
		pixelY -= gridOffset.y;
		return pixelY;
	}
	
	public int getDeltaX() {
		return deltaX;
	}
	
	public int getDeltaY() {
		return deltaY;
	}
	
	public int getLeftVertLine() {
		return leftVertLine;
	}
	
	public int getTopHorLine() {
		return topHorLine;
	}
	
	public int getNearestXLine() {
		return nearestXLine;
	}
	
	public int getNearestYLine() {
		return nearestYLine;
	}
	
	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}
	
	public void setDeltaY(int DeltaY) {
		this.deltaY = DeltaY;
	}
	
	public void setTopHorLine(int topHorLine) {
		this.topHorLine = topHorLine;
	}
	
	public void setLeftVertLine(int leftVertLine) {
		this.leftVertLine = leftVertLine;
	}
	
	public void setNearestXLine(int nearestXLine) {
		this.nearestXLine = nearestXLine;
	}
	
	public void setNearestYLine(int nearestYLine) {
		this.nearestYLine = nearestYLine;
	}
}
