package com.zargidigames.kpssgenelkultur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zargidigames.kpssgenelkultur.R;
import com.zargidigames.kpssgenelkultur.model.Subunit;

import java.util.List;

public class SubunitAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<Subunit> subunitList;

    public SubunitAdapter(Activity activity_, List<Subunit> subunits) {
        activity = activity_;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        subunitList = subunits;
    }

    @Override
    public int getCount() {
        return subunitList.size();
    }

    @Override
    public Subunit getItem(int position) {
        return subunitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowSubunitView;

        rowSubunitView = mInflater.inflate(R.layout.subunit_row, null);
        TextView textView = (TextView) rowSubunitView.findViewById(R.id.subunit_name);

        Subunit subunit = subunitList.get(position);

        textView.setText(subunit.name);

        return rowSubunitView;
    }


}