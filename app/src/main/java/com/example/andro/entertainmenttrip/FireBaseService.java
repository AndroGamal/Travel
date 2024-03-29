package com.example.andro.entertainmenttrip;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Timer;
import java.util.TimerTask;


public class FireBaseService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private static Timer timer;
    Query query;
    DatabaseReference firebase;
    NotificationManager notificationManager;
    //Handler handler;
    int i = 0;
    SharedPreferences prefs;
    public static void close() {
        try {
            timer.cancel();
        } catch (Exception e) {e.getStackTrace();
        } finally {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for (DataSnapshot son : dataSnapshot.getChildren()) {
                                trip o = son.getValue(trip.class);
                                notificationManager.notify(i, new NotificationCompat.Builder(FireBaseService.this).setSmallIcon(R.drawable.ic_menu_send).setContentText("Price : " + o.getPrice() + "$" + "        " + "Palace : " + o.getPalace()).setContentTitle(o.getName()).setDefaults(Notification.DEFAULT_ALL).setContentIntent(
                                        PendingIntent.getActivity(FireBaseService.this, 0, new Intent(FireBaseService.this, MainActivity.class), 0)).setAutoCancel(true).build());
                                i++;
                                o.setRead(true);
                                son.getRef().setValue(o);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }, 0, 600);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("Email",MODE_MULTI_PROCESS);
        notificationManager = (NotificationManager) getSystemService(FireBaseService.NOTIFICATION_SERVICE);
        firebase = FirebaseDatabase.getInstance().getReference("user").child(prefs.getString("email",new String())).child("trip");
        query = firebase.orderByChild("read").equalTo(false);
        timer = new Timer();
    }

}
