package com.jmunoz.sec01.liskov.bad;

public class Square extends Rectangle {
	
	public Square(int side) {
		super(side, side);
	}
	
	@Override
	public void setWidth(int width) {
		setSide(width);
	}

	@Override
	public void setHeight(int height) {
		setSide(height);
	}

	public void setSide(int side) {
		super.setWidth(side);
		super.setHeight(side);
	}

}
