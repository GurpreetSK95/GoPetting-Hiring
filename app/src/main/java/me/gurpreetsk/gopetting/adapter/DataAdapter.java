package me.gurpreetsk.gopetting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textviewData.setText(dataArrayList.get(position).getName());
        Picasso.with(context)
                .load(dataArrayList.get(position).getIconUrl())
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.imageviewData);
        holder.gridElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "Start Date:\r\t" + dataArrayList.get(holder.getAdapterPosition()).getStartDate() +
                        "\n\nEnd Date:\r\t" + dataArrayList.get(holder.getAdapterPosition()).getEndDate() +
                        "\n\nURL:\r\t" + dataArrayList.get(holder.getAdapterPosition()).getUrl() +
                        "\n\nLogin Required:\r\t" + dataArrayList.get(holder.getAdapterPosition()).getLoginRequired() +
                        "\n\nObjType:\r\t" + dataArrayList.get(holder.getAdapterPosition()).getObjType();
                new MaterialDialog.Builder(context)
                        .title(dataArrayList.get(holder.getAdapterPosition()).getName())
                        .content(content)
                        .positiveText(R.string.cool)
                        .show();
            }
        });
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
        @BindView(R.id.grid_element)
        LinearLayout gridElement;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
