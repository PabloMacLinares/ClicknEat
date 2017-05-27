package com.dam2.clickneat.recyclerview.adapters.perfil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.utils.BitmapHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ferna on 20/05/2017.
 */

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Publicacion> publicaciones;
    private Context cxt;
    private View.OnClickListener clickListener;
    private Picasso picasso;

    public PublicacionAdapter(Context cxt) {

        this(cxt, new ArrayList<Publicacion>());
    }

    public PublicacionAdapter(Context cxt, ArrayList<Publicacion> publicaciones) {

        this.cxt            = cxt;
        this.publicaciones  = publicaciones;
        this.picasso         = Picasso.with(cxt);
    }

    @Override
    public PublicacionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view               = inflater.inflate(R.layout.list_single_card, null);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PublicacionAdapter.ViewHolder holder, int position) {

        holder.loadPublicacion(publicaciones.get(position));
    }

    @Override
    public int getItemCount() {
        return publicaciones == null ? 0 : publicaciones.size();
    }


    @Override
    public void onClick(View v) {
        if ( this.clickListener != null ) clickListener.onClick(v);
    }

    public void setPublicaciones(ArrayList<Publicacion> publicaciones) {

        this.publicaciones = publicaciones;
        this.notifyDataSetChanged();
    }

    public void setOnClickListener ( View.OnClickListener clickListener ) {

        this.clickListener = clickListener;
    }

    public Publicacion getPublicacion(int position) {

        return this.publicaciones.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivDomicilio;
        private TextView tvDomicilio;

        public ViewHolder(View v) {

            super(v);

            this.ivDomicilio = (ImageView) v.findViewById(R.id.itemImage);
            this.tvDomicilio = (TextView) v.findViewById(R.id.tvTitle);
        }

        public void loadPublicacion(Publicacion publicacion) {

            this.tvDomicilio.setText(publicacion.getTitulo());


            BitmapHelper.loadBitmapAsynchronously(picasso, this.ivDomicilio, R.drawable.chimichangas, publicacion.getFoto());
            /*Picasso.with(PublicacionAdapter.this.cxt)
                    .load(publicacion.getFoto().isEmpty() ? "-" : publicacion.getFoto())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.chimichangas)
                    .error(R.drawable.chimichangas)
                    .into(this.ivDomicilio);*/
        }
    }
}
