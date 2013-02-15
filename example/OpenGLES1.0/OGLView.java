package lp.iem.activities;

import lp.iem.mesh.Mesh;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class OGLView extends GLSurfaceView {
		OGLRenderer renderer;    // Custom GL Renderer
	   
	   // For touch event
	   private final float TOUCH_SCALE_FACTOR = 180.0f / 320.0f;
	   
	   private float oneFingerPreviousX;
	   private float oneFingerPreviousY;
	   
	   private float first2FingersPreviousX;
	   private float first2FingersPreviousY;
	   private float second2FingersPreviousX;
	   private float second2FingersPreviousY;
	   private float currentDistance;
	   
	   private int nbFingers;

	   // Constructor - Allocate and set the renderer
	   public OGLView(Context context, Mesh mesh) {
	      super(context);
	      renderer = new OGLRenderer(context, mesh);
	      this.setRenderer(renderer);
	      // Request focus, otherwise key/button won't react
	      this.requestFocus();  
	      this.setFocusableInTouchMode(true);
	      
	      this.nbFingers = 0;
	      this.currentDistance = 0;
	   }
	   
	   // Handler for key event
	   @Override
	   public boolean onKeyUp(int keyCode, KeyEvent evt) {
	      switch(keyCode) {
	         case KeyEvent.KEYCODE_DPAD_LEFT:   // Decrease Y-rotational speed
	            renderer.speedY -= 0.1f;
	            break;
	         case KeyEvent.KEYCODE_DPAD_RIGHT:  // Increase Y-rotational speed
	            renderer.speedY += 0.1f;
	            break;
	         case KeyEvent.KEYCODE_DPAD_UP:     // Decrease X-rotational speed
	            renderer.speedX -= 0.1f;
	            break;
	         case KeyEvent.KEYCODE_DPAD_DOWN:   // Increase X-rotational speed 
	            renderer.speedX += 0.1f;
	            break;
	         //*
	         case KeyEvent.KEYCODE_A:           // Zoom out (decrease z)
	            renderer.z -= 0.2f;
	            break;
	         case KeyEvent.KEYCODE_Z:           // Zoom in (increase z)
	            renderer.z += 0.2f;
	            break;
	         //*/
	      }
	      return true;  // Event handled
	   }

	   // Handler for touch event
	   @Override
	   public boolean onTouchEvent(final MotionEvent evt) {
		   
		   int finger = evt.getActionIndex();
		   int e = evt.getActionMasked();
		   
	   		if(e == MotionEvent.ACTION_DOWN || e == MotionEvent.ACTION_POINTER_DOWN){
			   nbFingers++;
   			}else if(e == MotionEvent.ACTION_UP || e == MotionEvent.ACTION_POINTER_UP){
			   nbFingers--;
   			}
		   
		   float currentX = evt.getX();
		   float currentY = evt.getY();
	
		   if(finger == 0 && nbFingers == 1){
			   float deltaX, deltaY;
			   switch (e) {	
		         	case MotionEvent.ACTION_MOVE:
		         		// Modify rotational angles according to movement
		         		deltaX = currentX - oneFingerPreviousX;
		         		deltaY = currentY - oneFingerPreviousY;
		         		renderer.angleX += deltaY * TOUCH_SCALE_FACTOR;
		         		renderer.angleY += deltaX * TOUCH_SCALE_FACTOR;
		         		break;
			   }
			   // Save current x, y
			   oneFingerPreviousX = currentX;
			   oneFingerPreviousY = currentY;   			   
		   }else if(nbFingers == 2){
			   switch(finger){
			   		case 0:
			   			if(e == MotionEvent.ACTION_MOVE){
			   				float distance = distanceBetween2Poitns(currentX, 
			   						currentY, second2FingersPreviousX, second2FingersPreviousY);
			   				float diff = distance - currentDistance;
			   				currentDistance = distance;
			   				renderer.z += diff * TOUCH_SCALE_FACTOR;
			   			}
			   			first2FingersPreviousX = currentX;
			   			first2FingersPreviousY = currentY;
			   			break;
			   		case 1:
			   			if(e == MotionEvent.ACTION_MOVE){
			   				float distance = distanceBetween2Poitns(currentX, 
			   						currentY, first2FingersPreviousX, first2FingersPreviousY);
			   				float diff = distance - currentDistance;
			   				currentDistance = distance;
			   				renderer.z += diff * TOUCH_SCALE_FACTOR;
			   			}
			   			second2FingersPreviousX = currentX;
			   			second2FingersPreviousY = currentY;
			   			break;	
			   }
		   }
		   return true; 
	   }	
	   
	   private float distanceBetween2Poitns(float xa, float ya, float xb, float yb){
		   return (float)Math.sqrt((xb-xa)*(xb-xa)+(yb-ya)*(yb-ya));
	   }
}
