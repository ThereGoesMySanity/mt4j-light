///***********************************************************************
// * mt4j Copyright (c) 2008 - 2010 Christopher Ruff, Fraunhofer-Gesellschaft All rights reserved.
// *  
// *   This program is free software: you can redistribute it and/or modify
// *   it under the terms of the GNU General Public License as published by
// *   the Free Software Foundation, either version 3 of the License, or
// *   (at your option) any later version.
// *
// *   This program is distributed in the hope that it will be useful,
// *   but WITHOUT ANY WARRANTY; without even the implied warranty of
// *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *   GNU General Public License for more details.
// *
// *   You should have received a copy of the GNU General Public License
// *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *
// ***********************************************************************/
//package org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.mt4j.AbstractMTLayer;
//import org.mt4j.components.visibleComponents.shapes.MTPolygon;
//import org.mt4j.input.inputData.InputCursor;
//import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
//import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Recognizer;
//import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
//import org.mt4j.util.MTColor;
//import org.mt4j.util.math.Tools3D;
//import org.mt4j.util.math.ToolsGeometry;
//import java.awt.geom.Point2D;
//import org.mt4j.util.math.Vertex;
//
//import processing.core.PApplet;
//
///**
// * The Class DollarGestureContext.
// */
//public class UnistrokeContext {
//	
//	/** The gesture aborted. */
//	protected boolean gestureAborted;
//	
//	/** The cursor. */
//	private InputCursor cursor;
//	
//	/** The new position. */
//	private Point2D.Float newPosition;
//
//	/** The visualizer. */
//	private MTPolygon visualizer;
//	
//	/** The vertex points. */
//	private Vertex[] vertexPoints;
//	
//	/** The points. */
//	private List<Point2D.Float> points;
//	
//	/** The points_resampled. */
//	private List<Point2D.Float> points_resampled;
//	
//	/** The plane normal. */
//	private Point2D.Float planeNormal;
//	
//	/** The point in plane. */
//	private Point2D.Float pointInPlane;
//	
//	/** The pa. */
//	private PApplet pa;
//	
//	/** The recognizer. */
//	private Recognizer recognizer;
//	
//	/** The dollar utils. */
//	private UnistrokeUtils dollarUtils;
//	
//	/** The target. */
//	private AbstractMTLayer<?> target;
//	
//	/**
//	 * Instantiates a new dollar gesture context.
//	 *
//	 * @param pa the pa
//	 * @param planeNormal the plane normal
//	 * @param pointInPlane the point in plane
//	 * @param cursor the cursor
//	 * @param recognizer the recognizer
//	 * @param dollarUtils the dollar utils
//	 * @param target the target
//	 */
//	public UnistrokeContext(PApplet pa, Point2D.Float planeNormal, Point2D.Float pointInPlane, InputCursor cursor, Recognizer recognizer, UnistrokeUtils dollarUtils, AbstractMTLayer<?> target) {
//		gestureAborted = false;
//		this.cursor = cursor;
//		this.planeNormal = planeNormal;
//		this.pointInPlane = pointInPlane;
//		this.pa = pa;
//		this.recognizer = recognizer;
//		this.dollarUtils = dollarUtils;
//		this.target = target;
//		
//		//		Point2D.Float newPos = ToolsGeometry.getRayPlaneIntersection(Tools3D	.getCameraPickRay(pa, camera, cursor.getCurrentEvent().getPosX(), cursor.getCurrentEvent().getPosY()),
//		//				planeNormal, pointInPlane);
//		Point2D.Float newPos = ToolsGeometry.getRayPlaneIntersection(
//				Tools3D.getCameraPickRay(pa, target, cursor.getCurrentEvtPosX(), cursor.getCurrentEvtPosY()), 
//				planeNormal, 
//				pointInPlane);
//
//		if (newPos == null) {
//			System.out.println("DollarGestureContext"
//					+ " intersection with plane was null in class: "
//					+ this.getClass().getName());
//			gestureAborted = true;
//
//			return;
//		}
//		
//		points = new ArrayList<Point2D.Float>();
//		vertexPoints = new Vertex[] {};
//		
//		this.newPosition = newPos;
//		points.add(newPos);
//
//		visualizer =  new MTPolygon(pa, new Vertex[] {new Vertex(0,0), new Vertex(1,1), new Vertex(2,2)});
//		visualizer.setPickable(false);
//		visualizer.setDepthBufferDisabled(false);
////		visualizer.attachCamera(new MTCamera(pa));
//	}
//
//
//	/**
//	 * Gets the visualizer.
//	 *
//	 * @return the visualizer
//	 */
//	public MTPolygon getVisualizer(){
//		return this.visualizer;
//	}
//
//
//	/**
//	 * Update.
//	 *
//	 * @param m the m
//	 */
//	public void update(InputCursor m) {
//		if (!gestureAborted) {
//			Point2D.Float newPos = ToolsGeometry.getRayPlaneIntersection(
//					Tools3D.getCameraPickRay(pa, target, cursor.getCurrentEvtPosX(), cursor.getCurrentEvtPosY()), 
//					planeNormal, 
//					pointInPlane);
//			
//			this.newPosition = newPos;
//
//			points.add(this.newPosition);
//			int numPoints = points.size();
//
//			List<Vertex> tempList = new ArrayList<Vertex>();
//
//			if (points.size() > 64) {
//				numPoints = 64 + (int)Math.log(points.size() - 64);
//			}
//
//			points_resampled = new ArrayList<Point2D.Float>();
//			for (Point2D.Float point: points) {
//				points_resampled.add(new Point2D.Float(point.getX(), point.getY()));
//			}
//
//			points_resampled = dollarUtils.Resample(points_resampled, numPoints, Direction.CLOCKWISE);
//
//			tempList = new ArrayList<Vertex>();
//			for (Point2D.Float point: points_resampled) { //TODO avoid loop copy?
//				tempList.add(new Vertex(point.getX(), point.getY()));
//			}
//
//			vertexPoints = (Vertex[]) tempList.toArray(new Vertex[0]);
//
//			if (vertexPoints != null) 
//				visualizer.setVertices(vertexPoints);
//			visualizer.setNoFill(true);
//			visualizer.setStrokeWeight(5);
//			visualizer.setStrokeColor(new MTColor(255,255, 0, 192));
//		}
//	}
//
//	/**
//	 * Recognize gesture.
//	 *
//	 * @return the dollar gesture
//	 */
//	public UnistrokeGesture recognizeGesture(){
//		if (points_resampled != null){
//			return recognizer.Recognize(points_resampled);
//		}else{
//			return UnistrokeGesture.NOGESTURE;
//		}
//	}
//
//
//	/**
//	 * Gets the points.
//	 *
//	 * @return the points
//	 */
//	protected List<Point2D.Float> getPoints() {
//		return points;
//	}
//
//	/**
//	 * Sets the points.
//	 *
//	 * @param points the new points
//	 */
//	protected void setPoints(List<Point2D.Float> points) {
//		this.points = points;
//	}
//
//
//	public boolean isGestureAborted() {
//		return this.gestureAborted;
//	}
//}