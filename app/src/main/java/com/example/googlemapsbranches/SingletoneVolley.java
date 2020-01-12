package com.example.googlemapsbranches;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletoneVolley {

    public static RequestQueue requestQueue ;
    public static SingletoneVolley singleobject ;

    private SingletoneVolley (Context context){
        requestQueue = getRequestQueue(context);
    }

    //getObject() -> method to make only one object from this class
    //synchronized -> use to make tasks in queue (First in first out)
    public static synchronized SingletoneVolley getObject (Context context){
        if(singleobject == null ){
            // if this object -> null , create one and return it to use
            // if already created before just return it
            singleobject = new SingletoneVolley(context);
        }
        return singleobject;
    }

    public static RequestQueue getRequestQueue (Context context){
        // to create new RequestQueue to add any task in it
        // this code run if object volley created for first time
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context) ;
        }
        return requestQueue ;
    }

    //T -> generic type
    public<T> void addtoVolley (Request<T> q){
        requestQueue.add(q);
    }




}
