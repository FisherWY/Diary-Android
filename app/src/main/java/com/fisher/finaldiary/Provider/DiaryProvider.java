package com.fisher.finaldiary.Provider;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;
import com.fisher.finaldiary.DataBase.DiaryModel;
import org.litepal.LitePal;

import java.util.Date;

public class DiaryProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.fisher.finaldiary";
    static final String URL = "content://" + PROVIDER_NAME + "/diary";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final int DIARY = 1;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "diary", DIARY);
    }

    public DiaryProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        if (selection == null) {
            Toast.makeText(getContext(), "要删除的日记id为空", Toast.LENGTH_SHORT).show();
        } else {
            switch (uriMatcher.match(uri)) {
                case DIARY:
                    count = LitePal.delete(DiaryModel.class, Long.parseLong(selection));
                    break;
                default:
                    break;
            }
        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DIARY:
                return "com.fisher.finaldiary";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case DIARY:
                DiaryModel diaryModel = new DiaryModel();
                int row = LitePal.count(DiaryModel.class);
                diaryModel.setId(row + 1);
                diaryModel.setTitle(values.getAsString("title"));
                diaryModel.setMainText(values.getAsString("text"));
                diaryModel.setDate(new Date());
                diaryModel.setWeather(0);
                diaryModel.setMood(values.getAsInteger("mood"));
                diaryModel.save();

                Uri resUri = ContentUris.withAppendedId(CONTENT_URI, row + 1);
                getContext().getContentResolver().notifyChange(resUri, null);

                return resUri;
            default:
                return null;
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case DIARY:
                Cursor res = LitePal.findBySQL("SELECT * FROM DiaryStore WHERE id=" + Long.parseLong(selection));
                return res;
            default:
                throw new IllegalArgumentException("未知URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("暂无该操作提供");
    }
}
