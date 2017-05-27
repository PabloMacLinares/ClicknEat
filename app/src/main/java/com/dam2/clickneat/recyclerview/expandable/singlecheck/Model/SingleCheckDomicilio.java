package com.dam2.clickneat.recyclerview.expandable.singlecheck.Model;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

/**
 * Created by ferna on 23/05/2017.
 */

public class SingleCheckDomicilio extends SingleCheckExpandableGroup {

    private int iconResId;

    public SingleCheckDomicilio(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    protected SingleCheckDomicilio(Parcel in) {
        super(in);
        iconResId = in.readInt();
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(iconResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleCheckDomicilio> CREATOR = new Creator<SingleCheckDomicilio>() {
        @Override
        public SingleCheckDomicilio createFromParcel(Parcel in) {
            return new SingleCheckDomicilio(in);
        }

        @Override
        public SingleCheckDomicilio[] newArray(int size) {
            return new SingleCheckDomicilio[size];
        }
    };
}
