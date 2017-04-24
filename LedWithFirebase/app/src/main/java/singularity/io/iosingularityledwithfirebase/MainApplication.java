package singularity.io.iosingularityledwithfirebase;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

/**
 * Created by akjhh on 2017. 4. 22..
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        Log.d("akj", "inited");

    }
}
