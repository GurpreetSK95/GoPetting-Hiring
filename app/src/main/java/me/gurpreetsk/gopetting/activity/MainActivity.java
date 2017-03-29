package me.gurpreetsk.gopetting.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        DataAdapter adapter = new DataAdapter(MainActivity.this, fetchDataFromDB());
        RecyclerView.LayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }


    private List<Data> fetchDataFromDB() {
        final List<Data> dataFromServer;

        Cursor dataCursor = getContentResolver().query(DataTable.CONTENT_URI, null, null, null, null);
        dataFromServer = DataTable.getRows(dataCursor, true);

        return dataFromServer;
    }

}