package com.hms.atbotizmozel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.activity.SetMember;
import com.hms.atbotizmozel.persistence.Member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsListViewHolder>{

    private final LayoutInflater mInflater;
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    private Context context;

    public SettingsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public SettingsAdapter.SettingsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.settings_list_item, parent, false);
        return new SettingsListViewHolder(view);
    }
 
    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.SettingsListViewHolder holder, int position) {
        Member current = members.get(position);
        String memberType = current.getmName();
        holder.textView.setText(String.format("%s",  memberType));

            File f = new File(current.getmPhoto());
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageView.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
    }



    @Override
    public int getItemCount() {
        if (members != null)
            return members.size();
        else return 0;
    }



    class SettingsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final ImageView imageView;


        private SettingsListViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.memberName);
            imageView = itemView.findViewById(R.id.image_profile1);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Member member = members.get(position);
            Intent intent = new Intent(context, SetMember.class);
            intent.putExtra("name", member.getmName());
            intent.putExtra("photo", member.getmPhoto());
            intent.putExtra("uId", member.getUid());
            context.startActivity(intent);
        }
    }

    public Member getMemberAtPosition (int position) {
        return members.get(position);
    }
}
