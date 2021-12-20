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
package org.mt4j.input.inputProcessors.componentProcessors.dragProcessor;

import java.util.HashMap;

import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * The Class MultipleDragProcessor. Fires drag events for every cursor on the component instead
 * of only one cursor, like the DragProcessor.
 * Fires DragEvent gesture events. 
 * <br>Note: At the moment this processor does not care about locks on cursors!
 * @author Christopher Ruff
 */
public class MultipleDragProcessor extends AbstractCursorProcessor {
	//TODO also use cursor locking mechanism?
	
	/** The motion to drag context. */
	private HashMap<InputCursor, DragContext> motionToDragContext;

	/**
	 * Instantiates a new multiple drag processor.
	 * 
	 * @param app the app
	 */
	public MultipleDragProcessor() {
		super();
		motionToDragContext = new HashMap<InputCursor, DragContext>();
		this.setLockPriority(1);
	}

	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor#cursorStarted(org.mt4j.input.inputData.InputCursor, org.mt4j.input.inputData.MTFingerInputEvt)
	 */
	@Override
	public void cursorStarted(InputCursor inputCursor,	AbstractCursorInputEvt positionEvent) {
		DragContext dc = new DragContext(inputCursor);
		AbstractMTLayer<?> comp = positionEvent.getTarget();
		if (!dc.gestureAborted){
			motionToDragContext.put(inputCursor, dc);
			this.fireGestureEvent(new DragEvent(this, DragEvent.GESTURE_STARTED, comp, inputCursor, dc.lastPosition, dc.newPosition));
		}
	}

	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor#cursorUpdated(org.mt4j.input.inputData.InputCursor, org.mt4j.input.inputData.MTFingerInputEvt)
	 */
	@Override
	public void cursorUpdated(InputCursor inputCursor, AbstractCursorInputEvt positionEvent) {
		AbstractMTLayer<?> comp = positionEvent.getTarget();
		DragContext dc = motionToDragContext.get(inputCursor);
		if (dc != null){
			dc.updateDragPosition();
			this.fireGestureEvent(new DragEvent(this, MTGestureEvent.GESTURE_UPDATED, comp, inputCursor, dc.lastPosition, dc.newPosition));
		}
	}
	
	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor#cursorEnded(org.mt4j.input.inputData.InputCursor, org.mt4j.input.inputData.MTFingerInputEvt)
	 */
	@Override
	public void cursorEnded(InputCursor inputCursor, AbstractCursorInputEvt positionEvent) {
		AbstractMTLayer<?> comp = positionEvent.getTarget();
		DragContext dc = motionToDragContext.get(inputCursor);
		if (dc != null){
			this.fireGestureEvent(new DragEvent(this, MTGestureEvent.GESTURE_ENDED, comp, inputCursor, dc.lastPosition, dc.newPosition));
			motionToDragContext.remove(inputCursor);
		}
	}

	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor#cursorLocked(org.mt4j.input.inputData.InputCursor, org.mt4j.input.inputProcessors.IInputProcessor)
	 */
	@Override
	public void cursorLocked(InputCursor cursor, IInputProcessor lockingprocessor) {

	}

	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor#cursorUnlocked(org.mt4j.input.inputData.InputCursor)
	 */
	@Override
	public void cursorUnlocked(InputCursor cursor) {

	}
	
	
	/**
	 * The Class DragContext.
	 */
	private class DragContext {
		
		/** The start position. */
		protected Point2D.Float startPosition;
		
		/** The last position. */
		protected Point2D.Float lastPosition;
		
		/** The new position. */
		protected Point2D.Float newPosition;

		/** The m. */
		private InputCursor m; 
		
		/** The gesture aborted. */
		protected boolean gestureAborted;

		/**
		 * Instantiates a new drag context.
		 * 
		 * @param m the m
		 */
		public DragContext(InputCursor m){
			this.m = m;
			gestureAborted = false;
			//Set the Drag Startposition
			Point2D.Float interSectP = m.getPosition();
			
			if (interSectP != null)
				this.startPosition = interSectP;
			else{
				gestureAborted = true; 
				this.startPosition = new Point2D.Float(0,0); //TODO ABORT GESTURE!
			}
			this.newPosition = (Float) startPosition.clone();
			this.updateDragPosition();
			//Set the Drags lastPostition (the last one before the new one)
			this.lastPosition	= (Float) startPosition.clone();
		}

		/**
		 * Update drag position.
		 */
		public void updateDragPosition(){
			Point2D.Float newPos = m.getPosition();
			if (newPos != null){
				lastPosition = newPosition;
				newPosition = newPos;
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.componentProcessors.AbstractComponentProcessor#getName()
	 */
	@Override
	public String getName() {
		return "Multiple Drag Processor";
	}

}
