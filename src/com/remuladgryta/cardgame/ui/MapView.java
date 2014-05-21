package com.remuladgryta.cardgame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JPanel;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.entity.ComponentRenderable;
import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.IEvent;
import com.remuladgryta.cardgame.event.MapRenderStateChangedEvent;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.hex.HexMath;
import com.remuladgryta.hex.PixelCoord;
import com.remuladgryta.util.Config;

public class MapView extends JPanel implements MouseMotionListener,
		MouseListener, EventListener {
	private GameMap map;
	private Color hoverColor = new Color(0xF0FFFF);
	private Color targetColor = new Color(0xFFFF00);
	private Color playerColor = new Color(0x00FF00);
	private PixelCoord offset = new PixelCoord(0, 0);
	private double viewScale = 32;
	private CubeCoord hoveredTile = null, hoveredUnrounded = null;

	public MapView() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(600, 400));
	}

	public PixelCoord getOffset() {
		return offset;
	}

	public void setOffset(PixelCoord offset) {
		this.offset = offset;
	}

	public GameMap getMap() {
		return map;
	}

	@SuppressWarnings("unchecked")
	public void setMap(GameMap map) {
		this.map = map;
		map.getEngine().getEventDispatch()
				.addListener(this, MapRenderStateChangedEvent.class);
		map.getEngine().getEventDispatch()
				.addListener(this, PlayerStartTurnEvent.class);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(0.95f, 0.95f, 0.95f));
		g.fillRect(0, 0, getWidth(), getHeight());
		if (Config.debugUI && hoveredTile != null) {
			g.setColor(Color.BLACK);
			g.drawString("unrounded: " + hoveredUnrounded.toString(), 10, 20);
			g.drawString("rounded:   " + hoveredTile.toString(), 10, 30);

			paintTile(g, hoveredUnrounded, new LinkedList<Entity>());
			paintTile(g, hoveredTile, new LinkedList<Entity>());
		}

		if (map != null) {
			for (Entry<CubeCoord, List<Entity>> e : map.getTiles()) {
				paintTile(g, e.getKey(), e.getValue());
			}
		}
	}

	void paintTile(Graphics g, CubeCoord c, List<Entity> entities) {
		PixelCoord p = HexMath.pixelFromCube(c, viewScale).add(offset);
		Polygon hex = makeHex(p, viewScale);

		// fill hex background
		if (c.equals(hoveredTile)) {
			g.setColor(hoverColor);
		} else if (map.getEngine().getValidTargets().contains(c)) {
			g.setColor(targetColor);
		} else if (c.equals(map.getEngine().getCurrentPlayer().getEntity()
				.getLocation())) {
			g.setColor(playerColor);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillPolygon(hex);

		// draw tile outline
		g.setColor(Color.BLACK);
		g.drawPolygon(hex);

		// draw entities
		g.setColor(Color.RED);
		List<CubeCoord> entityLocs = HexMath.inRange(new CubeCoord(0, 0, 0), 1);
		for (int i = 0; i < entities.size() && i < entityLocs.size(); i++) {
			CubeCoord loc = entityLocs.get(i);
			PixelCoord locOffset = HexMath.pixelFromCube(loc, viewScale / 3);
			PixelCoord entityPos = p.add(locOffset);
			Polygon entityHex = makeHex(entityPos, viewScale / 3);

			Entity toDraw = entities.get(i);
			ComponentRenderable renderComponent = (ComponentRenderable) toDraw
					.getComponent("renderable");
			if (renderComponent == null) {
				g.fillPolygon(entityHex);
			} else {
				PixelCoord imageSize = new PixelCoord(Math.sqrt(3) / 2d
						* (viewScale * 2d/3d), viewScale * 2/3d);
				BufferedImage img = renderComponent.getImage();
				g.drawImage(img,
						(int)Math.round(entityPos.getX() - imageSize.getX() / 2),
						(int)Math.round(entityPos.getY() - imageSize.getY() / 2),
						(int)Math.round(entityPos.getX() + imageSize.getX() / 2),
						(int)Math.round(entityPos.getY() + imageSize.getY() / 2), 0, 0,
						img.getWidth(), img.getHeight(), null);

				// renderComponent.getImage()

			}
		}
	}

	Polygon makeHex(PixelCoord p, double scale) {
		Polygon hex = new Polygon();
		for (int i = 0; i < 6; i++) {
			double angle = 2 * Math.PI / 6 * (i + 0.5);
			double x_i = p.getX() + scale * Math.cos(angle);
			double y_i = p.getY() + scale * Math.sin(angle);
			hex.addPoint((int) x_i, (int) y_i);
		}
		return hex;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PixelCoord coord = new PixelCoord(e.getPoint().getX(), e.getPoint()
				.getY());
		coord = coord.add(offset.invert());
		hoveredUnrounded = HexMath.cubeFromPixel(coord, viewScale);
		hoveredTile = HexMath.round(hoveredUnrounded);

		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (hoveredTile != null && map.hasTile(hoveredTile)) {
			map.getEngine().tileInteracted(hoveredTile);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(IEvent event) {// MapRenderStateChanged or
											// PlayerStartTurn event
		repaint();
	}
}
