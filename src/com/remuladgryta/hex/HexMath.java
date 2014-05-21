package com.remuladgryta.hex;

import java.util.LinkedList;
import java.util.List;

public class HexMath {

	public static TileCoord tileFromPixel(PixelCoord coord, double tileSize) {
		return tileFromCube(cubeFromPixel(coord, tileSize));
	}
	
	public static TileCoord  tileFromCube(CubeCoord coord){
		return new TileCoord(coord.getG(), coord.getR());
	}

	public static PixelCoord pixelFromTile(TileCoord coord, double tileSize) {
		return pixelFromCube(cubeFromTile(coord), tileSize);
	}
	
	public static PixelCoord pixelFromCube(CubeCoord coord, double tileSize){
		double y = 3f/2f * tileSize * coord.getB();
		double x = Math.sqrt(3) * tileSize * ( coord.getB()/2 + coord.getR());
		return new PixelCoord(x,y);
	}
	
	public static CubeCoord cubeFromPixel(PixelCoord coord, double tileSize){
		double b = (2*coord.getY()) / (3 * tileSize);
		double r = (Math.sqrt(3) * coord.getX()-coord.getY())/(3 * tileSize);
		double g = -b-r;
		return new CubeCoord(r,g,b);
	}
	
	public static CubeCoord cubeFromTile(TileCoord coord){
		return new CubeCoord(coord.getP(), coord.getQ(), -coord.getP()-coord.getQ());
	}
	
	public static CubeCoord round(CubeCoord coord){
	    long rR = Math.round(coord.getR());
	    long rG = Math.round(coord.getG());
	    long rB = Math.round(coord.getB());

	    double R_diff = Math.abs(rR - coord.getR());
	    double G_diff = Math.abs(rG - coord.getG());
	    double B_diff = Math.abs(rB - coord.getB());

	    if (R_diff > G_diff && R_diff > B_diff){
	        rR = -rG-rB;
	    }
	    else if( G_diff > B_diff){
	        rG = -rR-rB;
	    }
	    else{
	        rB = -rR-rG;
	    }

	    return new CubeCoord(rR, rG, rB);
	}
	
	public static List<CubeCoord> inRange(CubeCoord coord, int distance){
		LinkedList<CubeCoord> results = new LinkedList<CubeCoord>();
		results.add(coord);
		for(int k = 1;k<=distance;k++){
			CubeCoord H = coord.add(HexDirection.SOUTH_WEST.getOffset().scale(k));
			for(HexDirection dir : HexDirection.values()){
				for(int j =0; j<k;j++){
					results.add(H);
					H = H.neighbor(dir);
				}
			}
		}
		return results;
	}
}
