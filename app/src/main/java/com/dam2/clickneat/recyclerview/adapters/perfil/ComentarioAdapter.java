package com.dam2.clickneat.recyclerview.adapters.perfil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.utils.BitmapHelper;
import com.dam2.clickneat.utils.StringHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ferna on 20/05/2017.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private ArrayList<Comentario> comentarios;
    private Context cxt;
    private Picasso picasso;

    public ComentarioAdapter(Context cxt) {

        this(cxt, new ArrayList<Comentario>());
    }

    public ComentarioAdapter(Context cxt, ArrayList<Comentario> publicaciones) {

        this.cxt         = cxt;
        this.comentarios = publicaciones;
        this.picasso     = Picasso.with(cxt);
    }

    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view               = inflater.inflate(R.layout.list_single_card_comment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComentarioAdapter.ViewHolder holder, int position) {

        holder.loadPublicacion(comentarios.get(position));
    }

    @Override
    public int getItemCount() {
        return comentarios == null ? 0 : comentarios.size();
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {

        this.comentarios = comentarios;
        this.notifyDataSetChanged();
    }

    public void addComentario(Comentario comentario) {

        this.comentarios.add(comentario);
        this.notifyItemInserted(this.comentarios.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView cvImage;
        private TextView tvTitle, tvFecha, tvDescripcion;
        private RatingBar rbPuntuacion;

        public ViewHolder(View view) {

            super(view);

            this.cvImage        = (CircleImageView) view.findViewById(R.id.comentario_image);
            this.tvTitle        = (TextView) view.findViewById(R.id.comentario_title);
            this.tvFecha        = (TextView) view.findViewById(R.id.comentario_fecha);
            this.tvDescripcion  = (TextView) view.findViewById(R.id.comentario_valoracion);
            this.rbPuntuacion   = (RatingBar) view.findViewById(R.id.comentario_stars);
        }

        public void loadPublicacion(Comentario comentario) {

            BitmapHelper.loadBitmapAsynchronously(picasso, this.cvImage, R.drawable.placeholder, comentario.getMetadata().getImagen());
            /*picasso.load(comentario.getMetadata().getImagen().isEmpty() ? "-" : comentario.getMetadata().getImagen())
                   .fit()
                   .centerCrop()
                   .placeholder(R.drawable.placeholder)
                   .error(R.drawable.placeholder)
                   .into(this.cvImage);*/

            this.tvTitle.setText(comentario.getMetadata().getTitulo());
            this.tvFecha.setText(StringHelper.dateToString(comentario.getFechaComentario()));
            this.tvDescripcion.setText(comentario.getValoracion());
            this.rbPuntuacion.setRating(comentario.getPuntuacion());
        }
    }
}
