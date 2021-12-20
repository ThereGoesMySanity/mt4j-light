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
package org.mt4j.input.inputData;

import java.awt.geom.Point2D;

import org.mt4j.input.inputSources.AbstractInputSource;

/**
 * This event class is used for
 * input events that have position data associated with them. This applies
 * for mouse input, finger or fiducial input for example. Also associated
 * with this event is a InputCursor object, which contains information about the
 * input over time and is a container for all input events during this time.
 * 
 * @author Christopher Ruff
 */
public abstract class AbstractCursorInputEvt extends MTInputEvent {
	public static final int INPUT_STARTED = 0;
	
	public static final int INPUT_UPDATED = 1;
	
	public static final int INPUT_ENDED = 2;

	/** The position x. */
	private float positionX;
	
	/** The position y. */
	private float positionY;
	
	/** The id. */
	private int id;
	
	/** The associated cursor. */
	private InputCursor associatedCursor;
	
	/**
	 * Instantiates a new touch event.
	 * 
	 * @param source the source
	 * @param positionX the position x
	 * @param positionY the position y
	 * @param id the id
	 * @param c the m
	 */
	public AbstractCursorInputEvt(AbstractInputSource source, float positionX, float positionY, int id, InputCursor c) {
		super(source); 
		this.id = id;
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		this.associatedCursor = c;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Gets the cursor.
	 * 
	 * @return the cursor
	 */
	public InputCursor getCursor() {
		return this.associatedCursor;
	}


	public void setCursor(InputCursor associatedcursor) {
		this.associatedCursor = associatedcursor;
	}
		
	/**
	 * This method should be called before firing this event to the global input processors.
	 * Here, the event is added to its cursor.
	 */
	@Override
	public void onFired() {
		super.onFired();
		if (this.getCursor() != null){
			this.getCursor().addEvent(this);
		}else{
//			System.out.println("couldnt add event to cursor - cursor null");
		}
	}
	
	public float getX(){
		return this.positionX;
	}
	
	public float getY(){
		return this.positionY;
	}
	
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Point2D.Float getPosition(){
		return new Point2D.Float(positionX, positionY);
	}
	
	
	public void setScreenX(float positionX) {
		this.positionX = positionX;
	}

	public void setScreenY(float positionY) {
		this.positionY = positionY;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return super.toString() + "; " + " PosX: " + positionX + " PosY: " + positionY + " InputSource: " + this.getSource(); 
	}

	@Override
	abstract public Object clone() throws CloneNotSupportedException;
}
