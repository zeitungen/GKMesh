package lp.iem.activities;

import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshIO;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        

        Mesh mesh = MeshIO.read("/mnt/sdcard/bigguy.obj", "bigguy");
        
        // full screen app (we remove the title app)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // param for full screen app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // create the GLView
        OGLView glview = new OGLView(getBaseContext(), mesh);
        
        setContentView(glview);
    }
    
}
