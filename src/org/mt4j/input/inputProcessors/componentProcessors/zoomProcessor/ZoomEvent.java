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
package org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor;

import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;


/**
 * The Class ZoomEvent.
 * @author Christopher Ruff
 */
public class ZoomEvent extends MTGestureEvent {

	/** The first cursor. */
	private InputCursor firstCursor;
	
	/** The second cursor. */
	private InputCursor secondCursor;
	
	/** The cam zoom amount. */
	private float camZoomAmount;

	private float zoomRatio;
	private float distance;
	
	/**
	 * Instantiates a new zoom event.
	 * 
	 * @param source the source
	 * @param id the id
	 * @param targetComponent the target component
	 * @param firstCursor the first cursor
	 * @param secondCursor the second cursor
	 * @param camZoomAmount the cam zoom amount
	 */
	public ZoomEvent(IInputProcessor source, int id, AbstractMTLayer<?> targetComponent, InputCursor firstCursor, InputCursor secondCursor, float camZoomAmount) {
		super(source, id, targetComponent);
		this.firstCursor = firstCursor;
		this.secondCursor = secondCursor;
		this.camZoomAmount = camZoomAmount;
		this.distance = (float)firstCursor.getPosition().distance(secondCursor.getPosition());
		this.zoomRatio = distance / (distance - camZoomAmount);
	}

	/**
	 * Gets the cam zoom amount.
	 * 
	 * @return the cam zoom amount
	 */
	public float getCamZoomAmount() {
		return camZoomAmount;
	}

	public float getZoomRatio() {
		return zoomRatio;
	}

	public float getDistance() {
		return distance;
	}

	/**
	 * Gets the first cursor.
	 * 
	 * @return the first cursor
	 */
	public InputCursor getFirstCursor() {
		return firstCursor;
	}

	/**
	 * Gets the second cursor.
	 * 
	 * @return the second cursor
	 */
	public InputCursor getSecondCursor() {
		return secondCursor;
	}
}
