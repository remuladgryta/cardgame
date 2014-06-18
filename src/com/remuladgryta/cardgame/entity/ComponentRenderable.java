package com.remuladgryta.cardgame.entity;

import java.awt.image.BufferedImage;

public class ComponentRenderable extends EntityComponent {
	private BufferedImage image;
	@Override
	public String getName() {
		return "renderable";
	}
	public BufferedImage getImage() {
		return image;
	}
	public ComponentRenderable setImage(BufferedImage image) {
		this.image = image;
		return this;
	}

}
