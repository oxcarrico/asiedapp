package com.example.ansiedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> itemList;

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.text_item_name);
        TextView itemConcernLevel = convertView.findViewById(R.id.text_item_concern_level);

        Item currentItem = itemList.get(position);

        itemName.setText(currentItem.getName());
        itemConcernLevel.setText(context.getString(R.string.total_concern_level, currentItem.getConcernLevel()));

        return convertView;
    }
}