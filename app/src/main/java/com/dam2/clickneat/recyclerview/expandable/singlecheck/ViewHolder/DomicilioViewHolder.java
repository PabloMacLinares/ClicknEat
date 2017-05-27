package com.dam2.clickneat.recyclerview.expandable.singlecheck.ViewHolder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.Model.Genre;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.Model.SingleCheckDomicilio;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by ferna on 23/05/2017.
 */

public class DomicilioViewHolder extends GroupViewHolder {

    private TextView genreName;
    private ImageView arrow;
    private ImageView icon;

    public DomicilioViewHolder(View itemView) {
        super(itemView);
        genreName = (TextView) itemView.findViewById(R.id.list_item_domicilio_name);
        arrow = (ImageView) itemView.findViewById(R.id.list_item_domicilio_arrow);
        icon = (ImageView) itemView.findViewById(R.id.list_item_domicilio_image);
    }

    public void setGenreTitle(ExpandableGroup genre) {
        if (genre instanceof Genre) {
            genreName.setText(genre.getTitle());
            icon.setBackgroundResource(((Genre) genre).getIconResId());
        }

        if (genre instanceof SingleCheckDomicilio) {
            genreName.setText(genre.getTitle());
            icon.setBackgroundResource(((SingleCheckDomicilio) genre).getIconResId());
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
