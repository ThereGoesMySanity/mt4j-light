/***********************************************************************
 * mt4j Copyright (c) 2008 - 2009 C.Ruff, Fraunhofer-Gesellschaft All rights reserved.
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

import java.util.List;

import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractComponentProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * The Class PanProcessorTwoFingers. Multi-touch gesture processor for panning the
 * canvas by moving the scene's camera. Should only be registered with MTCanvas components.
 * Fires PanEvent gesture events.
 * <br><strong>NOTE:</strong> Should be only used in combination with a MTCanvas component. 
 * @author Christopher Ruff
 */
public class PanProcessorTwoFingers extends AbstractCursorProcessor {
	
	/** The detect radius. */
	private float detectRadius;
	
	/**
	 * Instantiates a new pan processor two fingers.
	 * 
	 * @param panDetectRadius the pan detect radius
	 */
	public PanProcessorTwoFingers(float panDetectRadius){
		this.detectRadius = panDetectRadius;
		this.setLockPriority(2);
	}
	

	@Override
	public void cursorStarted(InputCursor c, AbstractCursorInputEvt positionEvent) {
		InputCursor[] locked = getLockedCursorsArray();
		if (locked.length >= 2){ //gesture with 2 fingers already in progress
			logger.debug(this.getName() + " has already enough cursors for this gesture - adding to unused ID:" + c.getId());
		}else{//not in progress yet
			List<InputCursor> availableCursors = getFreeComponentCursors();
			logger.debug(this.getName() + " Available cursors: " + availableCursors.size());
			
			if (availableCursors.size() >= 2){
				InputCursor otherCursor = (availableCursors.get(0).equals(c))? availableCursors.get(1) : availableCursors.get(0);
				//See if we can obtain a lock on both cursors
				if (this.canLock(otherCursor, c)){
					float newDistance = (float) otherCursor.getPosition().distance(c.getPosition());
					if (newDistance < detectRadius) {
						this.getLock(otherCursor, c);
						logger.debug(this.getName() + " we could lock both cursors! And fingers in zoom distance!");
						this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_STARTED, positionEvent.getCurrentTarget(), otherCursor, c, new Point2D.Float(0,0)));
					}else{
						logger.debug(this.getName() + " cursors not close enough to start gesture. Distance: " + newDistance);
					}
				}else{
					logger.debug(this.getName() + " we could NOT lock both cursors!");
				}
			}
		}
	}

	@Override
	public void cursorUpdated(InputCursor c, AbstractCursorInputEvt positionEvent) {
		List<InputCursor> locked = getLockedCursors();
		if (locked.contains(c)){ //in progress with this cursors
			InputCursor firstCursor = locked.get(0);
			InputCursor secondCursor = locked.get(1);
			Point2D.Float distance = (c.equals(firstCursor))? getNewTranslation(positionEvent.getTarget(), firstCursor, secondCursor) : getNewTranslation(positionEvent.getTarget(), secondCursor, firstCursor);
			if (c.equals(firstCursor)){
				this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_UPDATED, positionEvent.getCurrentTarget(), firstCursor, secondCursor, (Float) distance.clone()));
			}else{
				this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_UPDATED, positionEvent.getCurrentTarget(), firstCursor, secondCursor, (Float) distance.clone()));
			}
		}
	}
	
	
	@Override
	public void cursorEnded(InputCursor c, AbstractCursorInputEvt positionEvent) {
		logger.debug(this.getName() + " INPUT_ENDED RECIEVED - cursor: " + c.getId());
		List<InputCursor> locked = getLockedCursors();
		if (locked.contains(c)){
			InputCursor leftOverCursor = (locked.get(0).equals(c))? locked.get(1) : locked.get(0);
			InputCursor futureCursor = getFarthestFreeCursorToLimited(leftOverCursor, detectRadius);
			if (futureCursor != null){
				float newDistance = (float) leftOverCursor.getPosition().distance(futureCursor.getPosition());
				if (newDistance < detectRadius) {//Check if other cursor is in distance 
					this.getLock(futureCursor);
					logger.debug(this.getName() + " we could lock another cursor! (ID:" + futureCursor.getId() +")");
					logger.debug(this.getName() + " continue with different cursors (ID: " + futureCursor.getId() + ")" + " " + "(ID: " + leftOverCursor.getId() + ")");
				}else{
					this.endGesture(c, leftOverCursor, positionEvent.getCurrentTarget());
				}
			}else{
				this.endGesture(c, leftOverCursor, positionEvent.getCurrentTarget());
			}
		}
	}
	
	
	/**
	 * End gesture.
	 * 
	 * @param inputEndedCursor the input ended cursor
	 * @param leftOverCursor the left over cursor
	 * @param comp the comp
	 */
	private void endGesture(InputCursor inputEndedCursor, InputCursor leftOverCursor, AbstractMTLayer<?> comp){
		this.unLock(leftOverCursor);
		this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_ENDED, comp, inputEndedCursor, leftOverCursor, new Point2D.Float(0,0)));
	}
	
	

	@Override
	public void cursorLocked(InputCursor c, IInputProcessor lockingProcessor) {
		if (lockingProcessor instanceof AbstractComponentProcessor){
			logger.debug(this.getName() + " Recieved cursor LOCKED by (" + ((AbstractComponentProcessor)lockingProcessor).getName()  + ") - cursor ID: " + c.getId());
		}else{
			logger.debug(this.getName() + " Recieved cursor LOCKED by higher priority signal - cursor ID: " + c.getId());
		}
		
		List<InputCursor> locked = getLockedCursors();
		if (locked.contains(c)){
			InputCursor leftOverCursor = (locked.get(0).equals(c))? locked.get(1) : locked.get(0);
			this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_CANCELED, c.getCurrentTarget(), c, leftOverCursor, new Point2D.Float(0,0)));			
			this.unLockAllCursors();
			logger.debug(this.getName() + " cursor:" + c.getId() + " cursor LOCKED. Was an active cursor in this gesture - we therefor have to stop this gesture!");
		}
	}

	
	
	@Override
	public void cursorUnlocked(InputCursor c) {
		logger.debug(this.getName() + " Recieved UNLOCKED signal for cursor ID: " + c.getId());
		
		if (getLockedCursors().size() >= 2){ //we dont need the unlocked cursor, gesture still in progress
			return;
		}
		
		this.unLockAllCursors();
		
		List<InputCursor> availableCursors = getFreeComponentCursors();
		if (availableCursors.size() >= 2){ //we can try to resume the gesture
			InputCursor firstCursor = availableCursors.get(0);
			InputCursor secondCursor = getFarthestFreeCursorToLimited(firstCursor, detectRadius);

			//See if we can obtain a lock on both cursors
			if (this.canLock(firstCursor, secondCursor)){
				float newDistance = (float) firstCursor.getPosition().distance(secondCursor.getPosition());
				if (newDistance < detectRadius) {//Check if other cursor is in distance 
					this.getLock(firstCursor, secondCursor);
					logger.debug(this.getName() + " we could lock cursors: " + firstCursor.getId() +", " + secondCursor.getId());
					logger.debug(this.getName() + " continue with different cursors (ID: " + firstCursor.getId() + ")" + " " + "(ID: " + secondCursor.getId() + ")");
					this.fireGestureEvent(new PanTwoFingerEvent(this, MTGestureEvent.GESTURE_RESUMED, c.getCurrentTarget(), firstCursor, secondCursor, new Point2D.Float(0,0)));
				}else{
					logger.debug(this.getName() + " distance was too great between cursors: " + firstCursor.getId() +", " + secondCursor.getId() + " distance: " + newDistance);
				}
			}else{
				logger.debug(this.getName() + " we could NOT lock cursors: " + firstCursor.getId() +", " + secondCursor.getId());
			}
		}
	}
	
	
	
	
	/**
	 * Gets the new translation.
	 * 
	 * @param comp the comp
	 * @param movingCursor the moving cursor
	 * @param otherCursor the other cursor
	 * 
	 * @return the new translation
	 */
	private Point2D.Float getNewTranslation(AbstractMTLayer<?> comp, InputCursor movingCursor, InputCursor otherCursor){
		Point2D.Float fromFirstFinger = movingCursor.getPreviousEvent().getPosition();
		
		Point2D.Float fromSecondFinger = otherCursor.getCurrentEvent().getPosition();
		
		Point2D.Float oldMiddlePoint = getMiddlePointBetweenFingers(fromSecondFinger, fromFirstFinger);
		
		Point2D.Float toFirstFinger = movingCursor.getCurrentEvent().getPosition();
		
		Point2D.Float newMiddlePoint = getMiddlePointBetweenFingers(toFirstFinger ,  fromSecondFinger);
		Point2D.Float distance = new Point2D.Float(newMiddlePoint.x - oldMiddlePoint.x, newMiddlePoint.y - oldMiddlePoint.y);
		return distance;
	}
	
	
	/**
	 * Gets the middle point between fingers.
	 * 
	 * @param firstFinger the first finger
	 * @param secondFinger the second finger
	 * 
	 * @return the middle point between fingers
	 */
	private Point2D.Float getMiddlePointBetweenFingers(Point2D.Float firstFinger, Point2D.Float secondFinger){
		Point2D.Float bla = new Point2D.Float((secondFinger.x - firstFinger.x) * 0.5f, (secondFinger.y - firstFinger.y) * 0.5f); //= direction vector of 1. to 2. finger
		return (new Point2D.Float(firstFinger.x + bla.x, firstFinger.y + bla.y));
	}
	
	
	
	@Override
	public String getName() {
		return "two finger pan detector";
	}


	

}
