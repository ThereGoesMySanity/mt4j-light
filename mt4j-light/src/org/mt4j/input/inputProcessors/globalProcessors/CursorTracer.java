/***********************************************************************
 * mt4j Copyright (c) 2008 - 2009 Christopher Ruff, Fraunhofer-Gesellschaft All rights reserved.
 *  
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************/
package org.mt4j.input.inputProcessors.globalProcessors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.HashMap;
import java.util.Map;

import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputData.MTInputEvent;
import java.awt.geom.Point2D;

/**
 * The Class CursorTracer. A global input processor tracking all AbstractCursorInputEvt events and
 * displays a circle at that position.
 * 
 * @author Christopher Ruff
 */
public class CursorTracer extends AbstractGlobalInputProcessor{
	
	/** The cursor id to display shape. */
	private Map<InputCursor, RectangularShape> cursorIDToDisplayShape;
	
	private float ellipseRadius = 15;


	/**
	 * Instantiates a new cursor tracer.
	 * 
	 * @param mtApp the mt app
	 * @param currentScene the current scene
	 */
	public CursorTracer(AbstractMTApplication mtApp){
		this.cursorIDToDisplayShape = new HashMap<InputCursor, RectangularShape>();
		
//		if (PlatformUtil.isAndroid()){
//			ellipseRadius = 30;
//		}
	}
	/**
	 * Creates the display component.
	 * 
	 * @param applet the applet
	 * @param position the position
	 * 
	 * @return the abstract shape
	 */
	protected void drawDisplayComponent(Graphics2D g, RectangularShape displayShape, Point2D.Float position){
		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(100, 130, 220, 255));
		displayShape.setFrameFromCenter(position.x, position.y, position.x + ellipseRadius, position.y + ellipseRadius);
		g.draw(displayShape);
	}
	

	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.globalProcessors.AbstractGlobalInputProcessor#processInputEvtImpl(org.mt4j.input.inputData.MTInputEvent)
	 */
	@Override
	public void processInputEvtImpl(MTInputEvent inputEvent) {
		if (inputEvent instanceof AbstractCursorInputEvt) {
			AbstractCursorInputEvt cursorEvt = (AbstractCursorInputEvt)inputEvent;
			InputCursor c = cursorEvt.getCursor();
			Point2D.Float position = new Point2D.Float(cursorEvt.getX(), cursorEvt.getY());
			
			AbstractMTLayer<?> layer = (AbstractMTLayer<?>)c.getTarget();
			Graphics2D graphics = layer.getGraphics();

			RectangularShape displayShape = null;
			switch (cursorEvt.getId()) {
			case AbstractCursorInputEvt.INPUT_STARTED:
				displayShape = new Ellipse2D.Double();
				cursorIDToDisplayShape.put(c, displayShape);
				
				break;
			case AbstractCursorInputEvt.INPUT_UPDATED:
				displayShape = cursorIDToDisplayShape.get(c);
				break;
			case AbstractCursorInputEvt.INPUT_ENDED:
				displayShape = cursorIDToDisplayShape.remove(c);
				break;
			default:
				break;
			}
			if (displayShape != null){
				drawDisplayComponent(graphics, displayShape, position);
			}
		}
	}
}
