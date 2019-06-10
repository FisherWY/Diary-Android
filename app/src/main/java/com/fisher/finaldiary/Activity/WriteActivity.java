package com.fisher.finaldiary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.fisher.finaldiary.DataBase.DiaryModel;
import com.fisher.finaldiary.R;
import org.litepal.LitePal;

import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private EditText title;

    private EditText text;

    private Button cancel;

    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        title = findViewById(R.id.writeTitle);
        text = findViewById(R.id.writeText);
        cancel = findViewById(R.id.toHome);
        save = findViewById(R.id.Save);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHome();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary();
            }
        });
    }

    private void toHome() {
        onBackPressed();
    }

    // 保存日记
    private void saveDiary() {
        if (title.getText().toString().length() == 0 || text.getText().length() == 0) {
            Toast.makeText(this, "标题和内容不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        DiaryModel model = new DiaryModel();
        // 统计行数
        int row = LitePal.count(DiaryModel.class);
        model.setId(row + 1);
        model.setTitle(title.getText().toString());
        model.setMainText(text.getText().toString());
        model.setMood(0);
        model.setWeather(0);
        model.setDate(new Date());

        if (model.save()) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }

        toHome();
    }
}
