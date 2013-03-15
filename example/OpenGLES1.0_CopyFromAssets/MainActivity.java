package lp.iem.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshIO;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Mesh mesh = getMesh();
		// full screen app (we remove the title app)
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// param for full screen app
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create the GLView
		OGLView glview = new OGLView(getBaseContext(), mesh);

		setContentView(glview);
	}

	private String copyFromAssets(String from, String to) {
		try {
			byte[] buffer = new byte[1024];
			int len1 = 0;

			InputStream istr = (getResources().getAssets().open(from));
			FileOutputStream fos = openFileOutput(to, MainActivity.MODE_PRIVATE);

			while ((len1 = istr.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}
			
			fos.flush();
			fos.close();

			istr.close();

			return "/data/data/" + getPackageName() + "/files/" + to;
		} catch (Exception e) {
			Log.e("mesh", e.toString());
			return null;
		}
	}
	
	private boolean isStored(String filename){
		return new File("/data/data/" + getPackageName() + "/files/" + filename).exists();
	}
	
	private Mesh getMesh(){
		//check if files are on the hardware
		if(!isStored("bigguy.obj")) copyFromAssets("bigguy.obj", "bigguy.obj");
		
		
		//return MeshIO.read("/data/data/" + getPackageName() + "/files/cube.obj", "cube");
		return MeshIO.read("/data/data/" + getPackageName() + "/files/bigguy.obj", "bigguy");
		// Mesh mesh = MeshIO.read("/mnt/sdcard/triangle.obj", "triangle");
		// Mesh mesh = MeshIO.read("/mnt/sdcard/carre.obj", "carre");
		// Mesh mesh = MeshIO.read("/mnt/sdcard/monkey.obj", "monkey");
		//Mesh mesh = MeshIO.read("/mnt/sdcard/ducky.obj", "ducky");
		// Mesh mesh = MeshIO.read("/mnt/sdcard/sphere.obj", "sphere");
		// Mesh mesh = MeshIO.read("/mnt/sdcard/dragon.obj", "dragon");
	}

}
