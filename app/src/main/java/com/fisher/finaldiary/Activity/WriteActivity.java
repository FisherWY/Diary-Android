package com.fisher.finaldiary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.fisher.finaldiary.Adapter.MoodAdapter;
import com.fisher.finaldiary.DataBase.DiaryModel;
import com.fisher.finaldiary.R;
import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private EditText title;

    private EditText text;

    private Button cancel;

    private Button save;

    private Spinner spinner;

    private ImageView imageView;

    private MoodAdapter moodAdapter = new MoodAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        title = findViewById(R.id.writeTitle);
        text = findViewById(R.id.writeText);
//        cancel = findViewById(R.id.toHome);
        save = findViewById(R.id.Save);
        spinner = findViewById(R.id.moodSpinner);
        imageView = findViewById(R.id.moodImage);

//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHome();
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary();
            }
        });

        initMoodData();
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
        model.setMood(spinner.getSelectedItemPosition());
        model.setWeather(0);
        model.setDate(new Date());

        if (model.save()) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }

        toHome();
    }

    private void initMoodData() {
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(moodAdapter.getResources(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
