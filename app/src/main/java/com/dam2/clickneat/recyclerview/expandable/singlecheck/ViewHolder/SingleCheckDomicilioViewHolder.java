package com.dam2.clickneat.recyclerview.expandable.singlecheck.ViewHolder;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.dam2.clickneat.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

/**
 * Created by ferna on 23/05/2017.
 */

public class SingleCheckDomicilioViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public SingleCheckDomicilioViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView = (CheckedTextView) itemView.findViewById(R.id.list_item_singlecheck_domicilio_name);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setDomicilioName(String domicilioName) {
        childCheckedTextView.setText(domicilioName);
    }
}