package com.fisher.finaldiary.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fisher.finaldiary.Activity.MainActivity;
import com.fisher.finaldiary.Activity.ViewActivity;
import com.fisher.finaldiary.DataBase.DiaryModel;
import com.fisher.finaldiary.R;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private List<DiaryModel> list;

    private MoodAdapter moodAdapter = new MoodAdapter();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View diaryView;
        ImageView diaryImage;
        TextView diaryTitle;

        public ViewHolder(View view) {
            super(view);
            diaryView = view;
            diaryImage = view.findViewById(R.id.diaryImage);
            diaryTitle = view.findViewById(R.id.diaryTitle);
        }
    }

    public DiaryAdapter(List<DiaryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.diary_card, viewGroup , false);
        final ViewHolder holder = new ViewHolder(view);
        // 设置图片点击事件
        holder.diaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DiaryModel diaryModel = list.get(position);
                // 跳转
                Toast.makeText(v.getContext(), diaryModel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        // 设置标题点击事件
        holder.diaryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DiaryModel diaryModel = list.get(position);
                // 跳转
                Intent intent = new Intent(viewGroup.getContext(), ViewActivity.class);
                intent.putExtra("diaryData", diaryModel);
                viewGroup.getContext().startActivity(intent);
//                Toast.makeText(v.getContext(), diaryModel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DiaryModel diaryModel = list.get(i);
//        viewHolder.diaryImage.setImageURI();
        viewHolder.diaryImage.setImageResource(moodAdapter.getResources(diaryModel.getMood()));
        viewHolder.diaryTitle.setText(diaryModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
