package lp.iem.activities;

import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshIO;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        Mesh mesh = MeshIO.read("/mnt/sdcard/cube.obj", "cube");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/bigguy.obj", "bigguy");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/triangle.obj", "triangle");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/carre.obj", "carre");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/monkey.obj", "monkey");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/ducky.obj", "ducky");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/sphere.obj", "sphere");
        //Mesh mesh = MeshIO.read("/mnt/sdcard/dragon.obj", "dragon");
       
    	// Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if(!supportsEs2)
        	return; // OGLES 2 not supported !!
        
        // full screen app (we remove the title app)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // param for full screen app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // create the GLView
        OGLView glview = new OGLView(getBaseContext(), mesh);
        
        setContentView(glview);
    }
    
}
