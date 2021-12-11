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
package org.mt4j.util;


import processing.core.PApplet;

/**
 * The Class HelperMethods.
 * @author Christopher Ruff
 */
public class HelperMethods {
	
	/**
	 * Turn angle.
	 * 
	 * @param current the current
	 * @param target the target
	 * 
	 * @return the float
	 */
	public static float turnAngle (float current, float target) {
		  // assuming current and target are both between 0 and TWO_PI
		  current =	PApplet.radians(current);
		  target  = PApplet.radians(target);
		  float angle = target - current;
		  if (angle > PApplet.PI) angle -= PApplet.TWO_PI;
		  if (angle < -PApplet.PI) angle += PApplet.TWO_PI;
		  return PApplet.degrees(angle);
		}
	
/**
 * * Quicksort stuff **********************.
 * 
 * @param a the a
 */	
    /**
     * Quicksort algorithm.
     * @param a an array of Comparable items.
     */
    public static void quicksort( Comparable [ ] a ) {
        quicksort( a, 0, a.length - 1 );
    }
    
    /** The Constant QuickSortCUTOFF. */
    private static final int QuickSortCUTOFF = 10;
    
    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * 
     * @param a an array of Comparable items.
     * @param low the left-most index of the subarray.
     * @param high the right-most index of the subarray.
     */
    private static void quicksort( Comparable [ ] a, int low, int high ) {
        if( low + QuickSortCUTOFF > high )
            insertionSort( a, low, high );
        else {
            // Sort low, middle, high
            int middle = ( low + high ) / 2;
            if( a[ middle ].compareTo( a[ low ] ) < 0 )
                swapReferences( a, low, middle );
            if( a[ high ].compareTo( a[ low ] ) < 0 )
                swapReferences( a, low, high );
            if( a[ high ].compareTo( a[ middle ] ) < 0 )
                swapReferences( a, middle, high );
            
            // Place pivot at position high - 1
            swapReferences( a, middle, high - 1 );
            Comparable pivot = a[ high - 1 ];
            
            // Begin partitioning
            int i, j;
            for( i = low, j = high - 1; ; ) {
                while( a[ ++i ].compareTo( pivot ) < 0 )
                    ;
                while( pivot.compareTo( a[ --j ] ) < 0 )
                    ;
                if( i >= j )
                    break;
                swapReferences( a, i, j );
            }
            
            // Restore pivot
            swapReferences( a, i, high - 1 );
            
            quicksort( a, low, i - 1 );    // Sort small elements
            quicksort( a, i + 1, high );   // Sort large elements
        }
    }
    
    /**
     * Method to swap to elements in an array.
     * 
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static final void swapReferences( Object [ ] a, int index1, int index2 ) {
        Object tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }
    
    
    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * 
     * @param a an array of Comparable items.
     * @param low the left-most index of the subarray.
     * @param high the high
     */
    private static void insertionSort( Comparable [ ] a, int low, int high ) {
        for( int p = low + 1; p <= high; p++ ) {
            Comparable tmp = a[ p ];
            int j;
            
            for( j = p; j > low && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

/** * Quicksort stuff END **********************. */
}
