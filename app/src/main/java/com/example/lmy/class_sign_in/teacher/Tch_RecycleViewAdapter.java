package com.example.lmy.class_sign_in.teacher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmy.class_sign_in.R;

import java.util.List;




/**
 * Created by 何盛昌 on 2018/12/15.
 */

public class Tch_RecycleViewAdapter extends RecyclerView.Adapter<Tch_RecycleViewAdapter.ContactViewHolder> {


    // 设置点击事件的接口，利用接口回调，来完成点击事件
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    @NonNull
    //Tch_Adapter的成员变量contactInfoList, 这里被我们用作数据的来源
    private List<ContactInfo> contactInfoList;

    //Tch_Adapter的构造器
    public Tch_RecycleViewAdapter(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }
//重写3个抽象方法
//onCreateViewHolder()方法 返回我们自定义的 ContactViewHolder对象
    @Override
    public Tch_RecycleViewAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view,parent,false);

        return new ContactViewHolder(itemView);

    }

    @Override
    //contactInfoList中包含的都是ContactInfo类的对象
    public void onBindViewHolder(@NonNull final Tch_RecycleViewAdapter.ContactViewHolder holder, final int position) {

        //通过其get()方法可以获得ContactInfo类的对象
        ContactInfo contactInfo = contactInfoList.get(position);

        //将viewholder中hold住的各个view与数据源进行绑定(bind)
        holder.Cname.setText(contactInfo.Cname);
        holder.Cno.setText(contactInfo.CNO_+contactInfo.Cno);
        holder.Tchname.setText(contactInfo.TCHNAME_+contactInfo.Tchname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                return false;
            }
        });

    }


    //此方法返回列表项的数目
    @Override
    public int getItemCount() {
        return contactInfoList.size();
    }

     class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView Cname ;
        protected TextView Cno ;
        protected TextView Tchname ;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            Cname=itemView.findViewById(R.id.Cname);
            Cno=itemView.findViewById(R.id.text_cno);
            Tchname=itemView.findViewById(R.id.text_Tchname);

        }
    }

}
