package com.example.to_do.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.to_do.Model.toDoData;
import com.example.to_do.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class toDoAdapter extends RecyclerView.Adapter<toDoAdapter.MyViewHolder> {

    private Context mContext;
    private List<toDoData> dataList;
    private OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public toDoAdapter(Context mContext, List<toDoData> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        v=layoutInflater.inflate(R.layout.to_do_data_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( toDoAdapter.MyViewHolder holder, int position) {



        holder.id.setText("Id:"+dataList.get(position).getId());
        holder.title.setText("Title:"+dataList.get(position).getTitle());
        holder.userId.setText("UserId:"+dataList.get(position).getUserId());

      Glide.with(mContext)
                .load("https://picsum.photos/200/300")
              .fitCenter()
              .centerInside()
              .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView id;
        TextView userId;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titletxt);
            id=itemView.findViewById(R.id.idtxt);
            userId=itemView.findViewById(R.id.useridtxt);
            imageView=itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
