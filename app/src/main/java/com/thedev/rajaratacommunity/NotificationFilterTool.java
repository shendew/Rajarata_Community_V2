package com.thedev.rajaratacommunity;

import android.annotation.SuppressLint;
import android.widget.Toast;

public class NotificationFilterTool {
    String ret;

//    TODO: fix the filter error
    @SuppressLint("NotConstructor")
    public String NotificationFilterTool(String year, String fac, String event){


        if (year.equals("All Years")&&fac.equals("All Faculties")&&event.equals("All Events")){
            ret="1,1,1";
        }else if (year.equals("All Years")&& !fac.equals("All Faculties")&& !event.equals("All Events")){
            ret="1,"+fac+","+event;
        }
        else if (!year.equals("All Years")&& fac.equals("All Faculties")&& !event.equals("All Events")){
            ret=year+",1,"+event;
        }
        else if (!year.equals("All Years")&& !fac.equals("All Faculties")&& event.equals("All Events")){
            ret=year+","+fac+",1";
        }
        else if (year.equals("All Years")&& fac.equals("All Faculties")&& !event.equals("All Events")){
            ret="1,1,"+event;
        }
        else if (year.equals("All Years")&& !fac.equals("All Faculties")&& event.equals("All Events")){
            ret="1,"+fac+",1";
        }
        else if (!year.equals("All Years")&& fac.equals("All Faculties")&& event.equals("All Events")){
            ret=year+",1,1";
        }

        else {
            ret=year+","+fac+","+event;
        }


        return ret;
    }
}
