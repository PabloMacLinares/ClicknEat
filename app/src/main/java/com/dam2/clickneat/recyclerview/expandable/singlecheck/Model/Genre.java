package com.dam2.clickneat.recyclerview.expandable.singlecheck.Model;

import com.dam2.clickneat.pojos.Domicilio;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by ferna on 23/05/2017.
 */

public class Genre extends ExpandableGroup<Domicilio> {

    private int iconResId;

    public Genre(String title, List<Domicilio> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        return getIconResId() == genre.getIconResId();

    }

    @Override
    public int hashCode() {
        return getIconResId();
    }
}
