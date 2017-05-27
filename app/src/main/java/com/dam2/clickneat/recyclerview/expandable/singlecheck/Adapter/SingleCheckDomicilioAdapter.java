package com.dam2.clickneat.recyclerview.expandable.singlecheck.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.Model.SingleCheckDomicilio;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.ViewHolder.DomicilioViewHolder;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.ViewHolder.SingleCheckDomicilioViewHolder;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

/**
 * Created by ferna on 23/05/2017.
 */

public class SingleCheckDomicilioAdapter extends
        CheckableChildRecyclerViewAdapter<DomicilioViewHolder, SingleCheckDomicilioViewHolder> {

    private List<SingleCheckDomicilio> groups;

    public SingleCheckDomicilioAdapter(List<SingleCheckDomicilio> groups) {

        super(groups);
        this.groups = groups;
    }


    @Override
    public SingleCheckDomicilioViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_check_domicilio, parent, false);
        return new SingleCheckDomicilioViewHolder(view);
    }

    @Override
    public void onBindCheckChildViewHolder(SingleCheckDomicilioViewHolder holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final Domicilio domicilio = (Domicilio) group.getItems().get(childIndex);
        holder.setDomicilioName(domicilio.getDireccion());
    }

    @Override
    public DomicilioViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_domicilio, parent, false);
        return new DomicilioViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(DomicilioViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }

    public void checkDomicilio(Domicilio domicilio) {

        for ( SingleCheckDomicilio group : groups ) {

            List<Domicilio> domicilios = (List<Domicilio>)group.getItems();

            for ( int i = 0; i < domicilios.size(); i++ ) {

                if ( domicilios.get(i).getId() == domicilio.getId() ) {

                    group.checkChild(i);
                    break;
                }
            }
        }
    }

    public Domicilio getCheckedDomicilio() {

        for ( SingleCheckDomicilio group : groups ) {

            List<Domicilio> domicilios = (List<Domicilio>)group.getItems();

            for ( int i = 0; i < domicilios.size(); i++ ) {

                if ( group.isChildChecked(i) ) {

                    return domicilios.get(i);

                }
            }
        }

        return null;
    }
}