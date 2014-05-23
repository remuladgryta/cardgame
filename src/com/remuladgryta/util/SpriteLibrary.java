package com.remuladgryta.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteLibrary {
	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static void load(URL url, String name){
		try{
		BufferedImage img = ImageIO.read(url);
		images.put(name, img);
		}catch(IOException e){
			System.out.println("Could not load image: " + name);
			e.printStackTrace();
		}
	}
	
	public static void add(BufferedImage img, String name){
		images.put(name, img);
	}
	
	public static BufferedImage get(String name){
		BufferedImage result = images.get(name);
		if(result == null){
			URL url = SpriteLibrary.class.getResource("/img/"+name+".png");
			load(url,name);
		}
		return images.get(name);
	}
}
