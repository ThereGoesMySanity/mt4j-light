//package org.mt4j.input.gestureAction;
//TODO: swing
//import org.mt4j.components.MTComponent;
//import org.mt4j.components.TransformSpace;
//import org.mt4j.AbstractMTLayer;
//import org.mt4j.components.interfaces.IMTController;
//import org.mt4j.input.inputProcessors.IGestureEventListener;
//import org.mt4j.input.inputProcessors.MTGestureEvent;
//import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
//import java.awt.geom.Point2D;
//
///**
// * The Class InertiaDragAction.
// */
//public class InertiaDragAction implements IGestureEventListener {
//
//	/** The limit. */
//	private float limit;
//	
//	/** The damping. */
//	private float damping;
//	
//	/** The integration time. */
//	private int integrationTime;
//	
//	/**
//	 * Instantiates a new inertia drag action.
//	 */
//	public InertiaDragAction(){
//		this(125, 0.85f, 25);
////		this(120, 0.85f, 100);
//	}
//
//	
//	/**
//	 * Instantiates a new inertia drag action.
//	 *
//	 * @param integrationTime the integration time
//	 * @param damping the damping
//	 * @param maxVelocityLength the max velocity length
//	 */
//	public InertiaDragAction(int integrationTime, float damping, float maxVelocityLength){
//		this.integrationTime = integrationTime;
//		this.limit = maxVelocityLength;
//		this.damping = damping;
//	}
//
//	/* (non-Javadoc)
//	 * @see org.mt4j.input.inputProcessors.IGestureEventListener#processGestureEvent(org.mt4j.input.inputProcessors.MTGestureEvent)
//	 */
//	public boolean processGestureEvent(MTGestureEvent ge) {
//		AbstractMTLayer<?> t = ge.getTarget();
//		if (t instanceof MTComponent) {
//			MTComponent comp = (MTComponent) t;
//			DragEvent de = (DragEvent)ge;
//			IMTController oldController;
//			switch (de.getId()) {
//			case DragEvent.GESTURE_STARTED:
//				break;
//			case DragEvent.GESTURE_RESUMED:
//				break;
//			case DragEvent.GESTURE_UPDATED:
//				break;
//			case DragEvent.GESTURE_CANCELED:
//				break;
//			case DragEvent.GESTURE_ENDED:
//				Point2D.Float vel = de.getDragCursor().getVelocityVector(integrationTime);
//				vel.scaleLocal(0.9f); //Test - integrate over longer time but scale down velocity vec
//				vel = vel.getLimited(limit);
//				oldController = comp.getController();
//				comp.setController(new InertiaController(comp, vel, oldController));
//				break;
//			default:
//				break;
//			}
//		}
//		return false;
//	}
//	
//	
//	
//	/**
//	 * The Class InertiaController.
//	 */
//	private class InertiaController implements IMTController{
//		
//		/** The target. */
//		private MTComponent target;
//		
//		/** The start velocity vec. */
//		private Point2D.Float startVelocityVec;
////		private float dampingValue = 0.90f;
////		private float dampingValue = 0.80f;
////		private float dampingValue = 0.45f;
//		/** The old controller. */
//private IMTController oldController;
//		
//		//TODO use animation instead and ease out?
//		
//		/** The animation time. */
//		private int animationTime = 1000;
//		
//		/** The current animation time. */
//		private int currentAnimationTime = 0;
//		
//		/** The move per milli. */
//		private float movePerMilli;
//		
//		/** The move vect norm. */
//		private Point2D.Float moveVectNorm;
//		
//		/** The move vect. */
//		private Point2D.Float moveVect;
//		
//		/**
//		 * Instantiates a new inertia controller.
//		 *
//		 * @param target the target
//		 * @param startVelocityVec the start velocity vec
//		 * @param oldController the old controller
//		 */
//		public InertiaController(MTComponent target, Point2D.Float startVelocityVec, IMTController oldController) {
//			super();
//			this.target = target;
//			this.startVelocityVec = startVelocityVec;
//			this.oldController = oldController;
//			
//			//Animation inertiaAnim = new Animation("Inertia anim for " + target, new MultiPurposeInterpolator(startVelocityVec.length(), 0, 100, 0.0f, 0.5f, 1), target);
//			/*
//			currentAnimationTime = 0;
//			movePerMilli = startVelocityVec.length()/animationTime;
//			moveVectNorm = startVelocityVec.getNormalized();
//			moveVect = new Point2D.Float();
//			*/
//			
//		}
//		
//		//TODO ? inertia animation is frame based, not time - so framerate decides how long it goes..
//		
//		////@Override
//		/* (non-Javadoc)
//		 * @see org.mt4j.components.interfaces.IMTController#update(long)
//		 */
//		public void update(long timeDelta) {
//			/*
//			currentAnimationTime += timeDelta;
//			if (currentAnimationTime < animationTime){
//				moveVect.setValues(moveVectNorm);
//				moveVect.scaleLocal(timeDelta * movePerMilli);
//				
//				target.translateGlobal(moveVect);	
//			}else{
//				target.setController(oldController);
//				return;
//			}
//			*/
//			
////			/*
//			if (Math.abs(startVelocityVec.x) < 0.05f && Math.abs(startVelocityVec.y) < 0.05f){
//				startVelocityVec.setValues(Point2D.Float.ZERO_VECTOR);
//				target.setController(oldController);
//				return;
//			}
//			startVelocityVec.scaleLocal(damping);
//			
//			Point2D.Float vec = new Point2D.Float(startVelocityVec);
//			vec.transformDirectionVector(target.getGlobalInverseMatrix()); //Transform direction vector into component local coordinates
//			target.translate(vec,TransformSpace.LOCAL);
//			
////			target.translateGlobal(startVelocityVec);
////			*/
//			
//			if (oldController != null){
//				oldController.update(timeDelta);
//			}
//		}
//	}
//
//}
