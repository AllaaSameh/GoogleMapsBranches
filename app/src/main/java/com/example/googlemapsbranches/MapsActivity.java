package com.example.googlemapsbranches;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        GetBranches();

    }

    public void GetBranches ()
    {
        String url = "https://api.myjson.com/bins/130t4t";

        final JsonArrayRequest task = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ArrayList<Brand> brandlist = new ArrayList<>();
                try {
                for (int i =0; i<response.length();i++) {

                    JSONObject object = response.getJSONObject(i);
                    Brand brand = new Brand();
                    brand.setId(object.getInt("id"));
                    brand.setName(object.getString("name"));

                    JSONArray jsonArray = object.getJSONArray("branches");
                    ArrayList<Branch> brancheslist = new ArrayList<>();
                    for(int j = 0 ; j<jsonArray.length();j++)
                    {
                        JSONObject object1 = jsonArray.getJSONObject(j);
                        Branch branch = new Branch();

                        branch.setAddress(object1.getString("address"));
                        branch.setLang(object1.getDouble("lat"));
                        branch.setLang(object1.getDouble("lng"));

                        brancheslist.add(branch);

                        LatLng branches = new LatLng(branch.getLat(),branch.getLang());
                        mMap.addMarker(new MarkerOptions().position(branches).title(brand.getName()+j));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(branches));

                    }

                    brand.setBranches(brandlist);
                    brandlist.add(brand);
                }

                    } catch (JSONException e) {

                    Toast.makeText(MapsActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        SingletoneVolley.getObject(MapsActivity.this).addtoVolley(task);
    }

}