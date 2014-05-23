package com.remuladgryta.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtil {
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	public static BufferedImage composite(BufferedImage bg, BufferedImage fg){
		BufferedImage result = copyImage(bg);
		Graphics g = result.getGraphics();
		g.drawImage(fg, 0, 0, null);
		g.dispose();
		return result;
	}
}
