package me.gurpreetsk.gopetting.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.gopetting.R;
import me.gurpreetsk.gopetting.adapter.DataAdapter;
import me.gurpreetsk.gopetting.model.Data;
import me.gurpreetsk.gopetting.model.DataTable;

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

}