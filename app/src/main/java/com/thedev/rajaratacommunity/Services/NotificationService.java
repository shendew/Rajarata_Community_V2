package com.thedev.rajaratacommunity.Services;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Models.NotificationData;
import com.thedev.rajaratacommunity.NotificationPageActivity;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();
        getNotifications();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service was stopped", Toast.LENGTH_SHORT).show();
    }

    private void getNotifications() {
        Paper.init(getApplicationContext());
        String fac,year;
        fac=Paper.book().read("faculty");
        year=Paper.book().read("year");
        ArrayList<NotificationData> notifactions = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Notifications")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!notifactions.isEmpty()) {
                            notifactions.clear();
                        }
                        for (DataSnapshot data : snapshot.getChildren()) {

                            if (data.child("STATUS").getValue(String.class).equals("newnot")) {

                                String[] fil=data.child("FILT").getValue(String.class).split(",");


                                if (fil[0].equals(fac) && fil[1].equals(year)){
                                    Toast.makeText(NotificationService.this, "filter added", Toast.LENGTH_SHORT).show();

                                    String puredesc = data.child("NOT_DESC").getValue(String.class);
                                    String title = data.child("TITLE").getValue(String.class);
                                    String desc = puredesc;
                                    String image = data.child("IMAGE").getValue(String.class);
                                    String filt = data.child("FILT").getValue(String.class);
                                    notifactions.add(new NotificationData("0", title, "desc", image, desc, "", filt));

                                } else if (fil[0].equals(fac) && fil[1].equals("1")) {
                                    Toast.makeText(NotificationService.this, "filter fac added", Toast.LENGTH_SHORT).show();

                                    String puredesc = data.child("NOT_DESC").getValue(String.class);
                                    String title = data.child("TITLE").getValue(String.class);
                                    String desc = puredesc;
                                    String image = data.child("IMAGE").getValue(String.class);
                                    String filt = data.child("FILT").getValue(String.class);
                                    notifactions.add(new NotificationData("0", title, "desc", image, desc, "", filt));


                                } else if (fil[0].equals("1") && fil[1].equals(year)) {
                                    Toast.makeText(NotificationService.this, "filter year added", Toast.LENGTH_SHORT).show();

                                    String puredesc = data.child("NOT_DESC").getValue(String.class);
                                    String title = data.child("TITLE").getValue(String.class);
                                    String desc = puredesc;
                                    String image = data.child("IMAGE").getValue(String.class);
                                    String filt = data.child("FILT").getValue(String.class);
                                    notifactions.add(new NotificationData("0", title, "desc", image, desc, "", filt));

                                } else if (fil[0].equals("1") && fil[1].equals("1")) {

                                    String puredesc = data.child("NOT_DESC").getValue(String.class);
                                    String title = data.child("TITLE").getValue(String.class);
                                    String desc = puredesc;
                                    String image = data.child("IMAGE").getValue(String.class);
                                    String filt = data.child("FILT").getValue(String.class);
                                    notifactions.add(new NotificationData("0", title, "desc", image, desc, "", filt));
                                    Toast.makeText(NotificationService.this, "ALl added", Toast.LENGTH_SHORT).show();

                                }


                            }
                        }
                        Notification(notifactions);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void Notification(ArrayList<NotificationData> notifications) {

        for (int i = 0; i < notifications.size(); i++) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, "RUSTLE");
            builder.setContentText(notifications.get(i).getNOT_TITLE());
            builder.setContentTitle(notifications.get(i).getTITLE());

            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);

            builder.setAutoCancel(false);

            //Intent intent = new Intent(NotificationService.this, NotificationPageActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 2001, intent, PendingIntent.FLAG_UPDATE_CURRENT);




            Intent notificationIntent = new Intent(NotificationService.this, NotificationPageActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 2001, notificationIntent,
                            PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationService.this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            managerCompat.notify(i, builder.build());

        }


    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, NotificationPageActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        startForeground(101, new NotificationCompat.Builder(this,
                "default") // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }
}
