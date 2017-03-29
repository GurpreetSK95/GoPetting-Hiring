package me.gurpreetsk.gopetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.gopetting.R;
import me.gurpreetsk.gopetting.model.Data;

/**
 * Created by gurpreet on 29/03/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    private List<Data> dataArrayList;

    private static final String TAG = DataAdapter.class.getSimpleName();


    public DataAdapter(Context context, List<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textviewData.setText(dataArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_grid_element)
        ImageView imageviewData;
        @BindView(R.id.textview_grid_element)
        TextView textviewData;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
