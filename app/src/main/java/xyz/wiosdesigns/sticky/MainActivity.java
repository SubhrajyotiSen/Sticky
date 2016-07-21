package xyz.wiosdesigns.sticky;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private NotificationManager nm;
    private boolean sticky;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize views
        editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        //initialize Notification Manager
        nm  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences sharedPreferences = getSharedPreferences("sticky", MODE_PRIVATE);
        sticky = sharedPreferences.getBoolean("sticky",true);
        //click listener for button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if text is valid
                if (editText.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.empty_toast, Toast.LENGTH_SHORT).show();
                else
                    makeNotification();
            }
        });
        checkBox.setChecked(sticky);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                sticky = !sticky;
                SharedPreferences.Editor editor = getSharedPreferences("sticky",MODE_PRIVATE).edit();
                editor.putBoolean("sticky",sticky);
                editor.commit();
            }
        });
    }

    //method to build and display notification
    @SuppressWarnings("deprecation")
    private void makeNotification(){
                     Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,DisplayActivity.class);
        //clear Activity stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //put info in intent
        intent.putExtra("text",editText.getText().toString().trim());
        intent.putExtra("id",id);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //build the notification
        Notification.Builder builder = new Notification.Builder(this).setContentTitle(getString(R.string.notification_title))
                .setContentText(editText.getText().toString().trim())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
        Notification notification;

        //check Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = builder.build();
        }
        else
            notification = builder.getNotification();

        if (sticky)
            //make notification sticky
            notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        //start notification
        nm.notify(1,notification);
    }
}
