package cn.madeai.blog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private List<Post> postList;
    private OnItemClickListener listener;

    //在adapter中自定义一个接口 实现想要实现的方法
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    //回调方法 将接口传递进来
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView banner,bookmark,favorite,share;
        TextView title,date;

        public ViewHolder(final View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_card_main_2_title);
            date=itemView.findViewById(R.id.img_main_card2_date);
            banner=itemView.findViewById(R.id.img_main_card_2);
            bookmark=itemView.findViewById(R.id.img_main_card2_bookmark);
            favorite=itemView.findViewById(R.id.img_main_card2_favorite);
            share=itemView.findViewById(R.id.img_main_card2_share);

            share.setOnClickListener(ViewHolder.this);
            favorite.setOnClickListener(ViewHolder.this);
            bookmark.setOnClickListener(ViewHolder.this);
            itemView.setOnClickListener(ViewHolder.this);
        }


        @Override
        public void onClick(View view) {
            int position= getAdapterPosition();
            if (listener!=null){
                listener.onItemClick(view,position);
            }
        }
    }

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_2,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Post post= postList.get(position);
        List<String> imageUrl=Tool.getMarkdownImagePath(post.getRaw());
       if (!imageUrl.isEmpty()){
            Glide.with(holder.itemView.getContext()).load(imageUrl.get(0)).into(holder.banner);
        }else {
           Glide.with(holder.itemView.getContext()).load(Tool.getRandomImage()).into(holder.banner);
       }
        holder.title.setText(post.getTitle());
        holder.date.setText(post.getDate());
    }
    @Override
    public int getItemCount() {
        return postList.size();
    }
}
