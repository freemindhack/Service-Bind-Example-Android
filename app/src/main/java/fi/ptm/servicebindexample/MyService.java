package fi.ptm.servicebindexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by PTM on 17/10/15.
 */
public class MyService extends Service {

    // create Binder object for service
    private final IBinder binder = new MyBinder();

    // Binder methors are in this class (two last methods)
    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

	/* service methods */

    @Override
    public void onCreate() {
        Log.d("MyService","onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("MyService","onStartCommand()");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService","onBind()");
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.d("MyService","onDestroy()");
        super.onDestroy();
    }

	/* service binded methods */

    public double getRandom() {
        return Math.random();
    }

    public Person getPerson() {
        String[] names = {"Pekka","Kirsi","Matti","Jussi","Minna"};
        Person person = new Person(names[(int) (Math.random() * 4)],(int) (Math.random() * 100));
        return person;
    }
}
