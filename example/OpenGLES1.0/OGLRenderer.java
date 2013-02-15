package lp.iem.activities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshIO;


import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

public class OGLRenderer implements Renderer {
	
	private Context context;
	
	Mesh mesh;
	float angleX = 0;
	float angleY = 0;  
	float z = -80.0f;
	float speedX = 0;
	float speedY = 0;
	
	public OGLRenderer(Context context, Mesh mesh){
		this.context = context;
		this.mesh = mesh;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		// draw code
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // clear the screen
		
		float[] lights = { 0.f, 0.f, 1000.f, 1.f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, FloatBuffer.wrap(lights));
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		
		gl.glDisable(GL10.GL_CULL_FACE);
		
		FloatBuffer vertexBuffer;  // Buffer for vertex-array
		ShortBuffer indexBuffer;    // Buffer for index-array
		float[] vertices = Point.getFloatArray(mesh.M_positions());
		short[] indices = mesh.M_indicesShortArray();

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert byte buffer to float
		vertexBuffer.put(vertices);         // Copy data into buffer
		vertexBuffer.position(0);           // Rewind

		ByteBuffer vbbi = ByteBuffer.allocateDirect(indices.length * 2);
		vbbi.order(ByteOrder.nativeOrder());
		indexBuffer = vbbi.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		// Enable vertex-array and define the buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
      
		// Draw the primitives via index-array
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		// gesture rotation
		gl.glLoadIdentity();
		gl.glTranslatef(1.5f, 0.0f, -6.0f);  
		gl.glTranslatef(0.0f, 0.0f, z);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// init code, depend of dimensions. Call when screen redimension
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.f, (float)width/(float)height, 1.0f, 1000.f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// independant code from view dimension
		gl.glClearColor(.5f, .5f, .5f, .5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.f);
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

}
