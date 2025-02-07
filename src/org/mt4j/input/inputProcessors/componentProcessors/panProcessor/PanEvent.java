/***********************************************************************
 * mt4j Copyright (c) 2008 - 2009, C.Ruff, Fraunhofer-Gesellschaft All rights reserved.
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
package org.mt4j.input.inputProcessors.componentProcessors.panProcessor;

import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import java.awt.geom.Point2D;


/**
 * The Class PanEvent.
 * @author Christopher Ruff
 */
public class PanEvent extends MTGestureEvent {

	/** The first motion. */
	private InputCursor firstMotion;
	
	/** The translation vector. */
	private Point2D.Float translationVector;

	/**
	 * Instantiates a new pan event.
	 * 
	 * @param source the source
	 * @param id the id
	 * @param targetComponent the target component
	 * @param firstFinger the first finger
	 * @param translationVector the translation vector
	 * @param camera the camera
	 */
	public PanEvent(IInputProcessor source, int id, AbstractMTLayer<?> targetComponent, InputCursor firstFinger, Point2D.Float translationVector) {
		super(source, id, targetComponent);
		this.firstMotion = firstFinger;
		this.translationVector = translationVector;
	}

	/**
	 * Gets the first motion.
	 * 
	 * @return the first motion
	 */
	public InputCursor getFirstCursor() {
		return firstMotion;
	}

	/**
	 * Gets the translation vector.
	 * 
	 * @return the translation vector
	 */
	public Point2D.Float getTranslationVector() {
		return translationVector;
	}
}
