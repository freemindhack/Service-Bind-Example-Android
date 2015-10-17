package fi.ptm.servicebindexample;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by PTM on 17/10/15.
 */

public class MainActivity extends Activity {
    private final int MENU_CALL_SERVICE = 1;
    private MyService serviceBinder;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        // Bind to the service
        Intent intent = new Intent(MainActivity.this,MyService.class);
        // Bind with ServiceConnection object
        bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    // connection between service and activity
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // connection is made
            serviceBinder = ((MyService.MyBinder)service).getService();
            textView.setText("Connection made, use menu to call service:\n\n");
        }
        public void onServiceDisconnected(ComponentName className) {
            // service unexpectedly disconnected
            serviceBinder = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_CALL_SERVICE, 0, "Call Service");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CALL_SERVICE:
                callService();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // call service
    public void callService() {
        // call public method in service
        Person person = serviceBinder.getPerson();
        String personString = "Person name is "+person.name + " and age is "+ person.age;
        textView.append(personString+"\n");
    }

    @Override
    public void onDestroy(){
        unbindService(connection);
        stopService(new Intent(this, MyService.class));
        super.onDestroy();
    }
}
