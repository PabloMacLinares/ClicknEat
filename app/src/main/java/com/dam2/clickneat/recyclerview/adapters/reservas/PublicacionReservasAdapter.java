package com.dam2.clickneat.recyclerview.adapters.reservas;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Publicacion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ferna on 25/05/2017.
 */

public class PublicacionReservasAdapter extends RecyclerView.Adapter<PublicacionReservasAdapter.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener clickListenerFab1;
    private View.OnClickListener clickListenerFab2;
    private View.OnClickListener clickListenerItem;

    private ArrayList<Publicacion> publicaciones;
    private Context context;
    private Picasso picasso;

    public PublicacionReservasAdapter(Context cxt) {

        this(cxt, new ArrayList<Publicacion>());
    }

    public PublicacionReservasAdapter(Context cxt, ArrayList<Publicacion> publicaciones) {

        this.context        = cxt;
        this.publicaciones  = publicaciones;
        this.picasso        = Picasso.with(cxt);
    }

    public void setReservasPublicaciones(ArrayList<Publicacion> publicaciones) {

        this.publicaciones = publicaciones;
        this.notifyDataSetChanged();
    }

    public void setClickListenerFab1(View.OnClickListener clickListenerFab1) {

        this.clickListenerFab1 = clickListenerFab1;
    }

    public void setClickListenerFab2(View.OnClickListener clickListenerFab2) {

        this.clickListenerFab2 = clickListenerFab2;
    }


    public void setOnItemClickListener(View.OnClickListener clickListenerItem ) {

        this.clickListenerItem = clickListenerItem;
    }

    public Publicacion getPublicacion(int position) {

        return this.publicaciones.get(position);
    }

    @Override
    public PublicacionReservasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view               = inflater.inflate(R.layout.activity_reservas_publicacion_item, parent, false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PublicacionReservasAdapter.ViewHolder holder, int position) {

        holder.viewPublicacion(this.publicaciones.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.publicaciones == null ? 0 : this.publicaciones.size();
    }

    @Override
    public void onClick(View v) {
        if ( this.clickListenerItem != null ) this.clickListenerItem.onClick(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPublicacion;
        private TextView tvTitulo;
        private FloatingActionButton fab1, fab2;

        public ViewHolder(View view) {

            super(view);

            this.ivPublicacion  = (ImageView) view.findViewById(R.id.reserva_imagen);
            this.tvTitulo       = (TextView) view.findViewById(R.id.reserva_titulo);
            this.fab1           = (FloatingActionButton) view.findViewById(R.id.reserva_guiar_publicacion);
            this.fab2           = (FloatingActionButton) view.findViewById(R.id.reserva_ver_perfil);


            if (clickListenerFab1 != null ) fab1.setOnClickListener(clickListenerFab1);
            if (clickListenerFab2 != null ) fab2.setOnClickListener(clickListenerFab2);
        }

        public void viewPublicacion(Publicacion publicacion, int position) {

            //Cargamos la informacion
            picasso.load((publicacion.getFoto() == null || publicacion.getFoto().isEmpty()) ? "-" : publicacion.getFoto())
                   .placeholder(R.drawable.chimichangas)
                   .error(R.drawable.chimichangas)
                   .into(this.ivPublicacion);

            this.tvTitulo.setText(publicacion.getTitulo());
            this.fab1.setTag(position);
            this.fab2.setTag(position);

        }
    }
}
