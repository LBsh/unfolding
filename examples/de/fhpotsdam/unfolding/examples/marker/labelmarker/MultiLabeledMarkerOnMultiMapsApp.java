package de.fhpotsdam.unfolding.examples.marker.labelmarker;

import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import codeanticode.glgraphics.GLConstants;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoRSSReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Similar to {@link MultiLabeledMarkerApp}, but marker appear on two maps.
 * 
 * Markers on both maps react to hover, but selection is always visible in both maps, as the markers are the same.
 */
public class MultiLabeledMarkerOnMultiMapsApp extends PApplet {

	Map map1;
	Map map2;

	public void setup() {
		size(800, 600, GLConstants.GLGRAPHICS);
		PFont font = loadFont("Miso-Light-12.vlw");

		map1 = new Map(this, "map", 50, 50, 500, 500);
		map1.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map1);

		map2 = new Map(this, "map", 575, 50, 150, 150);
		MapUtils.createDefaultEventDispatcher(this, map2);

		List<Feature> features = GeoRSSReader.loadData(this, "bbc-georss-test.xml");
		List<Marker> markers = MultiLabeledMarkerApp.createLabeledMarkers(font, features);
		map1.addMarkers(markers);
		map2.addMarkers(markers);
	}

	public void draw() {
		background(240);
		map1.draw();
		map2.draw();
	}

	public void mouseMoved() {
		checkInsideMarker(map1);
		checkInsideMarker(map2);
	}

	public void checkInsideMarker(Map map) {
		if (map.isHit(mouseX, mouseY)) {
			MarkerManager<Marker> mm = map.mapDisplay.getLastMarkerManager();

			// Deselect all marker
			for (Marker marker : mm.getMarkers()) {
				marker.setSelected(false);
			}

			// Select hit marker
			Marker marker = mm.getFirstHitMarker(mouseX, mouseY);
			if (marker != null) {
				marker.setSelected(true);
			}
		}
	}

}