package com.hms.atbotizmozel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.persistence.Score;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreListViewHolder>{

    private final LayoutInflater mInflater;
    private List<Score> scores;

    public List<Score> getScore() {
        return scores;
    }

    public void setScore(List<Score> score) {
        this.scores = score;
        notifyDataSetChanged();
    }

    private Context context;

    public ScoreAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreAdapter.ScoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_report, parent, false);
        return new ScoreAdapter.ScoreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ScoreListViewHolder holder, int position) {
        Score current = scores.get(position);
        String scoreType = current.getmTestName();
        holder.textView.setText(String.format("%s",  scoreType));

    }

    @Override
    public int getItemCount() {
        if (scores != null)
            return scores.size();
        else return 0;
    }



    class ScoreListViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;


        private ScoreListViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.memberName);
            imageView = itemView.findViewById(R.id.image_profile1);

        }
    }

    public Score getScoreAtPosition (int position) {
        return scores.get(position);
    }
}