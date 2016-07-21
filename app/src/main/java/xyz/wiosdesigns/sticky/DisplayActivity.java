package xyz.wiosdesigns.sticky;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();

        //get notification info
        String text = intent.getStringExtra("text");
        final int id = intent.getIntExtra("id",1);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        //build the alert dialog
        final AlertDialog builder = new AlertDialog.Builder(this).setTitle(R.string.confirmation_title)
                .setMessage(text)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //dismiss the notification
                        NotificationManager nm  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.cancel(id);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //only finish Activity
                        finish();
                    }
                })
                .create();

        //show the dialog
        builder.show();
    }
}
