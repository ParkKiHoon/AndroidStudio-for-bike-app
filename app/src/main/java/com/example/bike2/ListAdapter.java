package com.example.bike2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.player.SecondUnityPlayerActivity;
import com.unity3d.player.UnityPlayerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListData> mData = null ;
    String[] cur_name=new String[5];
    String[] cur_part=new String[5];
    String weight_price;
    Fragment fragment;
    private Context mContext;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_time;
        TextView tv_weight_price;

        ViewHolder(View itemView) {
            super(itemView) ;

            this.iv_image=itemView.findViewById(R.id.iv_image);
            this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_weight_price=itemView.findViewById(R.id.tv_weight_price);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    ListAdapter(ArrayList<ListData> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_list, parent, false) ;
        ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(view) ;

        return vh ;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        weight_price=mData.get(position).getTv_weight_price();
        holder.tv_name.setText(mData.get(position).getTv_name() + "님의 커스텀");
        holder.tv_time.setText(TimeConvert.timeconvert(Long.parseLong(mData.get(position).getTv_time())).toString());
        holder.tv_weight_price.setText(weight_price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), SecondUnityPlayerActivity.class);
                cur_name[0]=mData.get(position).getFrame_name();
                cur_name[1]=mData.get(position).getWheelset_name();
                cur_name[2]=mData.get(position).getHandlebar_name();
                cur_name[3]=mData.get(position).getSaddle_name();
                cur_name[4]=mData.get(position).getGroupset_name();
                cur_part[0]=mData.get(position).getFrame_value();
                cur_part[1]=mData.get(position).getWheelset_value();
                cur_part[2]=mData.get(position).getHandlebar_value();
                cur_part[3]=mData.get(position).getSaddle_value();
                cur_part[4]=mData.get(position).getGroupset_value();
                intent.putExtra("cur_name",cur_name);
                intent.putExtra("cur_part",cur_part);
                intent.putExtra("user_id",mData.get(position).getTv_name());
                intent.putExtra("time",TimeConvert.timeconvert(Long.parseLong(mData.get(position).getTv_time())));
                intent.putExtra("price",mData.get(position).getTv_weight_price());
                ((Activity)holder.itemView.getContext()).startActivityForResult(intent,8090);

            }

        });
    }


    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public void clear(){
        mData.clear();
    }

    public void addItem(ListData data){
        mData.add(data);
    }
}
