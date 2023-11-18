package com.thedev.rajaratacommunity;

import android.annotation.SuppressLint;

public class NotificationFilterTool {
    String ret;

    @SuppressLint("NotConstructor")
    public String NotificationFilterTool(String year, String fac, String event){

        if (year.equals("All Years")&&fac.equals("All Faculties")&&event.equals("All Eevents")){
            ret="1,1,1";
        }else if (year.equals("All Years")&& !fac.equals("All Faculties")&& !event.equals("All Eevents")){
            ret="1,"+fac+","+event;
        }
        else if (!year.equals("All Years")&& fac.equals("All Faculties")&& !event.equals("All Eevents")){
            ret=year+",1,"+event;
        }
        else if (!year.equals("All Years")&& !fac.equals("All Faculties")&& event.equals("All Eevents")){
            ret=year+","+fac+",1";
        }

        else if (year.equals("All Years")&& fac.equals("All Faculties")&& !event.equals("All Eevents")){
            ret=year+"1,1,"+event;
        }
        else if (year.equals("All Years")&& !fac.equals("All Faculties")&& event.equals("All Eevents")){
            ret="1,"+fac+",1";
        }
        else if (!year.equals("All Years")&& fac.equals("All Faculties")&& event.equals("All Eevents")){
            ret=year+",1,1";
        }

        else {
            ret=year+","+fac+","+event;
        }


        return ret;
    }
}
