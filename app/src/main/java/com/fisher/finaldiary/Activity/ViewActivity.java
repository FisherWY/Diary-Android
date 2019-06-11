package com.fisher.finaldiary.Activity;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fisher.finaldiary.Adapter.MoodAdapter;
import com.fisher.finaldiary.DataBase.DiaryModel;
import com.fisher.finaldiary.R;
import org.litepal.LitePal;

public class ViewActivity extends AppCompatActivity {

    private DiaryModel diaryModel;

    private TextView title;

    private TextView text;

    private ImageView mood;

    private Button deleteButton;

    private MoodAdapter moodAdapter = new MoodAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // 获取数据
        diaryModel = (DiaryModel) getIntent().getSerializableExtra("diaryData");

        // 加载数据
        title = findViewById(R.id.viewTitle);
        text = findViewById(R.id.viewText);
        mood = findViewById(R.id.viewMoodImage);
        deleteButton = findViewById(R.id.viewDelete);
        initData();
    }

    private void initData() {
        title.setText(diaryModel.getTitle());
        text.setText(diaryModel.getMainText());
        mood.setImageResource(moodAdapter.getResources(diaryModel.getMood()));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDialog();
            }
        });
    }

    private void showCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认");
        builder.setMessage("确认删除这篇日记吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDiary();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDiary() {
        LitePal.delete(DiaryModel.class, diaryModel.getId());
        finish();
    }
}
