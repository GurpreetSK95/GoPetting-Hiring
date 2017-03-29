package me.gurpreetsk.gopetting.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.gopetting.PettingApplication;
import me.gurpreetsk.gopetting.R;
import me.gurpreetsk.gopetting.adapter.DataAdapter;
import me.gurpreetsk.gopetting.model.Data;
import me.gurpreetsk.gopetting.model.DataTable;
import me.gurpreetsk.gopetting.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    DataAdapter adapter;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        fetchDataFromServer();
        adapter = new DataAdapter(MainActivity.this, fetchDataFromDB());
        RecyclerView.LayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private List<Data> fetchDataFromDB() {
        final List<Data> dataFromServer;

        Cursor dataCursor = getContentResolver().query(DataTable.CONTENT_URI, null, null, null, null);
        dataFromServer = DataTable.getRows(dataCursor, true);

        return dataFromServer;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_settings:
                new MaterialDialog.Builder(MainActivity.this)
                        .title("Created by Gurpreet Singh")
                        .content("GoPetting - 2017")
                        .positiveText(R.string.app_name)
                        .show();
        }
        return true;
    }

    public void fetchDataFromServer() {
        JsonObjectRequest dataArrayRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            Log.i(TAG, "onResponse: " + dataArray.toString());
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject object = (JSONObject) dataArray.get(i);
                                Data data = new Data(object.get("name").toString(),
                                        object.get("startDate").toString(),
                                        object.get("endDate").toString(),
                                        object.get("url").toString(),
                                        object.get("icon").toString(),
                                        object.get("loginRequired").toString(),
                                        object.get("objType").toString()
                                );
                                try {
                                    getContentResolver().insert(DataTable.CONTENT_URI,
                                            DataTable.getContentValues(data, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: ", e);
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                    }
                }
        );
        PettingApplication.getInstance().addToQueue(dataArrayRequest);
    }

}