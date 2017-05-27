package com.dam2.clickneat.recyclerview.adapters.perfil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Domicilio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ferna on 20/05/2017.
 */

public class DomicilioAdapter extends RecyclerView.Adapter<DomicilioAdapter.ViewHolder> {

    private ArrayList<Domicilio> domicilios;
    private Context cxt;

    public DomicilioAdapter(Context cxt) {

        this(cxt, new ArrayList<Domicilio>());
    }

    public DomicilioAdapter(Context cxt, ArrayList<Domicilio> domicilios) {

        this.cxt        = cxt;
        this.domicilios = domicilios;
    }

    @Override
    public DomicilioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view               = inflater.inflate(R.layout.list_single_card, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DomicilioAdapter.ViewHolder holder, int position) {

        holder.loadDomicilio(domicilios.get(position));
    }

    @Override
    public int getItemCount() {
        return domicilios == null ? 0 : domicilios.size();
    }

    public void setDomicilios(ArrayList<Domicilio> domicilios) {

        this.domicilios = domicilios;
        this.notifyDataSetChanged();
    }

    public void addDomicilio(Domicilio domicilio) {

        this.domicilios.add(domicilio);
        this.notifyItemInserted(this.domicilios.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivDomicilio;
        private TextView tvDomicilio;

        public ViewHolder(View v) {

            super(v);

            this.ivDomicilio = (ImageView) v.findViewById(R.id.itemImage);
            this.tvDomicilio = (TextView) v.findViewById(R.id.tvTitle);
        }

        public void loadDomicilio(Domicilio domicilio) {

            this.tvDomicilio.setText(domicilio.getDireccion());

            Picasso.with(DomicilioAdapter.this.cxt)
                    .load(domicilio.getImagenes().get(0))
                    .fit()
                    .into(this.ivDomicilio);
        }
    }
}
