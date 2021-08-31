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
import com.hms.atbotizmozel.activity.SetRelativeEmotions;
import com.hms.atbotizmozel.persistence.Emotion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionListViewHolder>{

    private final LayoutInflater mInflater;
    private List<Emotion> emotions;

    public List<Emotion> getEmotion() {
        return emotions;
    }

    public void setEmotion(List<Emotion> emotion) {
        this.emotions = emotion;
        notifyDataSetChanged();
    }

    private Context context;

    public EmotionAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public EmotionAdapter.EmotionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.emotions_list_item, parent, false);
        return new EmotionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionAdapter.EmotionListViewHolder holder, int position) {
        Emotion current = emotions.get(position);
        String emotionType = current.getmEmotionName();
        holder.textView.setText(String.format("%s",  emotionType));

        File f = new File(current.getmEmotionPhoto());
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.imageView.setImageBitmap(bitmap);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        if (emotions != null)
            return emotions.size();
        else return 0;
    }



    class EmotionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final ImageView imageView;


        private EmotionListViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.emotionName);
            imageView = itemView.findViewById(R.id.image_profile);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Emotion emotion = emotions.get(position);
            Intent intent = new Intent(context, SetRelativeEmotions.class);
            intent.putExtra("name", emotion.getmEmotionName());
            intent.putExtra("photo", emotion.getmEmotionPhoto());
            intent.putExtra("uId", emotion.getUid());
            context.startActivity(intent);
        }
    }

    public Emotion getEmotionAtPosition (int position) {
        return emotions.get(position);
    }
}
