package com.developer.ditmar.gallery;

import android.app.Service;
import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadInformation();
    }

    private void LoadInformation() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Services.URLSERVICE, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<GalleryData> LIST = new ArrayList<GalleryData>();
                for (int i = 0; i < response.length(); i ++) {
                    GalleryData item = new GalleryData();
                    try {
                        JSONObject objitem = response.getJSONObject(i);
                        if (objitem.has("urls")) {
                            JSONObject urlsObj = objitem.getJSONObject("urls");
                            if (urlsObj.has("full")) {
                                item.setUrl(urlsObj.getString("full"));
                            }
                        }
                        if (objitem.has("user")) {
                            JSONObject userobj = objitem.getJSONObject("user");
                            if (userobj.has("name")) {
                                item.setUsername(userobj.getString("name"));
                            }
                            if (userobj.has("bio")) {
                                if (!userobj.isNull("bio")) {
                                    item.setBio(userobj.getString("bio"));
                                } else {
                                    item.setBio("informacion no disponible");
                                }
                            }
                        }
                        LIST.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                LoadRecyClerView(LIST);
                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
            }

        });
    }

    private void LoadRecyClerView(ArrayList<GalleryData> list) {
        CustomRecycler adapter = new CustomRecycler(list);
        RecyclerView myrecycler = this.findViewById(R.id.myrecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecycler.setLayoutManager(manager);
        myrecycler.setAdapter(adapter);

    }
}
