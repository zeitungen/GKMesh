package lp.iem.activities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lp.iem.gk.Point;
import lp.iem.mesh.Mesh;


import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;

public class OGLRenderer implements Renderer {
	
	public static final int FLOAT_SIZE = 4;
	public static final int SHORT_SIZE = 2;
	public static final int COORDS_PER_VERTEX = 3;
	private final int vertexStride = COORDS_PER_VERTEX * 4;
	
	private Context context;
	
	Mesh mesh;
	float angleX = 0;
	float angleY = 0;  
	float z = -10.0f;
	float speedX = 0;
	float speedY = 0;
    
    FloatBuffer vertexBuffer;  // Buffer for vertex-array
	ShortBuffer indexBuffer;    // Buffer for index-array
	float[] vertices;
	short[] indices;
	
	private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" + // the matrix must be included as a modifier of gl_Position
            "  gl_Position = vPosition * uMVPMatrix;" +
            "}";
    
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";
    
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    
    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };
    
	public OGLRenderer(Context context, Mesh mesh){
		this.context = context;
		this.mesh = mesh;
		
		vertices = Point.getFloatArray(mesh.M_positions());
		indices = mesh.M_indicesShortArray();
	}
	
	public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);		
		
		// initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indexBuffer = dlb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
        
        // prepare shaders and OpenGL program
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);   		
	}
	
    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        //Matrix.setLookAtM(mVMatrix, 0, 0f, 0, z, 0f, 0f, 0, 0f, 1.0f, 0.0f);
        Matrix.setIdentityM(mVMatrix, 0);
        Matrix.translateM(mVMatrix, 0, 0, 0, z);
        
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
        
        //
        Matrix.setRotateM(mRotationMatrix, 0, angleX, 1, 0, 0);
        Matrix.rotateM(mRotationMatrix, 0, angleY, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

        // Draw
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        // Draw the square
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);       
    }
    
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 7);
        //Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -fovy, fovy, 1f, 100);
        //perspectiveM(mProjMatrix, 0, 65, ratio, 0.1f, 100.f);

    }
    
    /**
     * Google function (API 14)
     * @param m
     * @param offset
     * @param fovy
     * @param aspect
     * @param zNear
     * @param zFar
     */
    public static void perspectiveM(float[] m, int offset, float fovy, float aspect, float zNear, float zFar) {
          float f = 1.0f / (float) Math.tan(fovy * (Math.PI / 360.0));
          float rangeReciprocal = 1.0f / (zNear - zFar);

          m[offset + 0] = f / aspect;
          m[offset + 1] = 0.0f;
          m[offset + 2] = 0.0f;
          m[offset + 3] = 0.0f;

          m[offset + 4] = 0.0f;
          m[offset + 5] = f;
          m[offset + 6] = 0.0f;
          m[offset + 7] = 0.0f;

          m[offset + 8] = 0.0f;
          m[offset + 9] = 0.0f;
          m[offset + 10] = (zFar + zNear) * rangeReciprocal;
          m[offset + 11] = -1.0f;

          m[offset + 12] = 0.0f;
          m[offset + 13] = 0.0f;
          m[offset + 14] = 2.0f * zFar * zNear * rangeReciprocal;
          m[offset + 15] = 0.0f;
      }
}
