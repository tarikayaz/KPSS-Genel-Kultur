package com.zargidigames.kpssgenelkultur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zargidigames.kpssgenelkultur.R;
import com.zargidigames.kpssgenelkultur.model.Unit;

import java.util.List;

public class UnitAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<Unit> unitList;

    public UnitAdapter(Activity activity_, List<Unit> units) {
        activity = activity_;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        unitList = units;
    }

    @Override
    public int getCount() {
        return unitList.size();
    }

    @Override
    public Unit getItem(int position) {
        return unitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowUnitView;

        rowUnitView = mInflater.inflate(R.layout.unit_row, null);
        TextView textView = (TextView) rowUnitView.findViewById(R.id.unit_name);

        Unit unit = unitList.get(position);

        textView.setText(unit.name);

        return rowUnitView;
    }


}