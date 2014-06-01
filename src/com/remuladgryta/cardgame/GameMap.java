package com.remuladgryta.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.print.attribute.standard.MediaSize.Engineering;

import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.cardgame.event.MapRenderStateChangedEvent;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.hex.HexMath;
import com.remuladgryta.hex.TileCoord;

public class GameMap {
	HashMap<CubeCoord, List<Entity>> tiles;
	GameEngine gameEngine;
	public GameMap(GameEngine engine){
		tiles = new HashMap<CubeCoord, List<Entity>>();
		gameEngine = engine;
	}
	
	public GameMap(GameEngine engine, TileCoord size){
		this(engine);
		for(int p =0; p<=size.getP(); p++){
			for(int q =0; q<=size.getQ(); q++){
				tiles.put(HexMath.cubeFromTile(new TileCoord(p,q)), new LinkedList<Entity>());
			}
		}
	}
	
	public GameMap(GameEngine engine, int radius){
		this(engine);
		List<CubeCoord> tileList = HexMath.inRange(new CubeCoord(0,0,0), radius);
		for(CubeCoord tile : tileList){
			tiles.put(tile, new LinkedList<Entity>());
		}
	}
	
	public GameEngine getEngine(){
		return gameEngine;
	}
	
	/**
	 * 
	 * @param coord
	 * @return an immutable list of the current entities at a given tile. will not update to reflect changes on the map
	 */
	public List<Entity> entitiesAt(CubeCoord coord){
		return Collections.unmodifiableList(new ArrayList<Entity>(tiles.get(coord)));
	}

	public void moveEntity(Entity e, CubeCoord target) {
		if (entitiesAt(target).size() < 7) {// can't move to a full tile
			tiles.get(e.getLocation()).remove(e);
			tiles.get(target).add(e);
			e.setLocation(target);
			gameEngine.getEventDispatch().dispatch(
					new MapRenderStateChangedEvent());
		} else {
			throw new RuntimeException(
					"Entity tried to move to full tile");
		}
	}
	
	public void removeEntity(CubeCoord coord, Entity e){
		tiles.get(coord).remove(e);
		e.setLocation(null);
		gameEngine.getEventDispatch().dispatch(new MapRenderStateChangedEvent());
	}

	public void addEntity(CubeCoord coord, Entity e) {
		if (entitiesAt(coord).size() < 7) {
			tiles.get(coord).add(e);
			e.setLocation(coord);
			gameEngine.getEventDispatch().dispatch(
					new MapRenderStateChangedEvent());
		}else{
			throw new RuntimeException("Tried to add entity to full tile");
		}
	}
	
	public void addEntityAtRandom(Entity e){
		Set<CubeCoord> mapTiles = tiles.keySet();
		int index = gameEngine.getRand().nextInt(mapTiles.size());
		Iterator<CubeCoord> iter = mapTiles.iterator();
		CubeCoord c=null;
		for(int i=0;i<=index;i++){
			c=iter.next();
		}
		addEntity(c,e);
	}
	
	/**
	 * Note: Modifying the returned set will result in unpredictable behavior
	 * @return
	 */
	public Set<Entry<CubeCoord, List<Entity>>> getTileData(){
		return Collections.unmodifiableSet(tiles.entrySet());
	}
	
	public Set<CubeCoord> getTiles(){
		return Collections.unmodifiableSet(tiles.keySet());
	}

	public boolean hasTile(CubeCoord tile) {
		return tiles.containsKey(tile);
	}
}
