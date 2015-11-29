package com.zargidigames.kpssgenelkultur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zargidigames.kpssgenelkultur.R;
import com.zargidigames.kpssgenelkultur.model.Lesson;

import java.util.List;

public class LessonAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<Lesson> lessonList;

    public LessonAdapter(Activity activity_, List<Lesson> lessons) {
        activity = activity_;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lessonList = lessons;
    }

    @Override
    public int getCount() {
        return lessonList.size();
    }

    @Override
    public Lesson getItem(int position) {
        return lessonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowLessonView;

        rowLessonView = mInflater.inflate(R.layout.lesson_row, null);
        TextView textView = (TextView) rowLessonView.findViewById(R.id.lesson_name);

        Lesson lesson = lessonList.get(position);

        textView.setText(lesson.name);

        return rowLessonView;
    }


}