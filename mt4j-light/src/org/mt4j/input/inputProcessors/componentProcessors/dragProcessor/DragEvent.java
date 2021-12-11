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
package org.mt4j.input.inputProcessors.componentProcessors.dragProcessor;

import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import java.awt.geom.Point2D;


/**
 * The Class DragEvent.
 * @author Christopher Ruff
 */
public class DragEvent extends MTGestureEvent {
	
	/** The drag cursor. */
	private InputCursor dragCursor;
	
	/** The from. */
	private Point2D.Float from;
	
	/** The to. */
	private Point2D.Float to;
	
	/** The translation vect. */
	private Point2D.Float translationVect;
	
	
	/**
	 * Instantiates a new drag event.
	 * 
	 * @param source the source
	 * @param id the id
	 * @param targetComponent the target component
	 * @param dragCursor the drag cursor
	 * @param from the from
	 * @param to the to
	 */
	public DragEvent(IInputProcessor source, int id, AbstractMTLayer<?> targetComponent, InputCursor dragCursor, Point2D.Float from, Point2D.Float to) {
		super(source, id, targetComponent);
		this.dragCursor = dragCursor;
		this.from = from;
		this.to = to;
		this.translationVect = new Point2D.Float(to.x - from.x, to.y - from.y);
	}

	/**
	 * Gets the drag cursor.
	 * 
	 * @return the drag cursor
	 */
	public InputCursor getDragCursor() {
		return dragCursor;
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public Point2D.Float getFrom() {
		return from;
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public Point2D.Float getTo() {
		return to;
	}

	/**
	 * Gets the translation vect.
	 * 
	 * @return the translation vect
	 */
	public Point2D.Float getTranslationVect() {
		return translationVect;
	}

	


}
