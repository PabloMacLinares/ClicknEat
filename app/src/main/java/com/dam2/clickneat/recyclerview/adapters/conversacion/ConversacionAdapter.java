package com.dam2.clickneat.recyclerview.adapters.conversacion;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.utils.StringHelper;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fernando on 31/12/2016.
 */

public class ConversacionAdapter extends RecyclerView.Adapter<ConversacionAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<ConversacionMetadata> conversacionesMetadata;
    private View.OnClickListener clickListener;
    private Context context;

    public ConversacionAdapter(Context context) {

        this(context, new ArrayList<ConversacionMetadata>());
    }

    public ConversacionAdapter( Context context, ArrayList<ConversacionMetadata> conversacionesMetadata ) {

        this.context                = context;
        this.conversacionesMetadata = conversacionesMetadata;
    }

    @Override
    public ConversacionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li   = LayoutInflater.from(parent.getContext());
        View view           = li.inflate(R.layout.conversacion_metadata_item, parent, false);
        view.setOnClickListener(this.clickListener);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversacionAdapter.ViewHolder holder, int position) {

        ConversacionMetadata metadata = conversacionesMetadata.get(position);
        holder.drawConversacion(metadata);
    }

    @Override
    public int getItemCount() {
        return conversacionesMetadata != null ? conversacionesMetadata.size() : 0;
    }

    @Override
    public void onClick(View v) {

        if(clickListener != null) clickListener.onClick(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image;
        public EmojiconTextView tvMensaje;
        public TextView tvUsername, tvChatTime, tvMensajesNoLeidos;

        public ViewHolder(View itemView) {

            super(itemView);

            this.image              = (CircleImageView) itemView.findViewById(R.id.profile_image);
            this.tvUsername         = (TextView) itemView.findViewById(R.id.tvUsername);
            this.tvChatTime         = (TextView) itemView.findViewById(R.id.tvChatTime);
            this.tvMensaje          = (EmojiconTextView) itemView.findViewById(R.id.emojiconTextView);
            this.tvMensajesNoLeidos = (TextView) itemView.findViewById(R.id.tvUnseen);
        }

        public void drawConversacion(ConversacionMetadata metadata) {

            //Cargamos la imagen
            String foto = (metadata.getFoto() == null || metadata.getFoto().isEmpty())  ? "-" : metadata.getFoto();

            Picasso.with(context)
                    .load(foto)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(image);
            tvUsername.setText(metadata.getTitulo());

            tvChatTime.setText(StringHelper.dateToString(metadata.getFechaEnvio()));
            tvMensaje.setText(metadata.getTextoUltimoMensaje());

            if (metadata.getMensajesNoLeidos() > 0) {

                //Cambiamos el color si el numero de mensajes sin leer es mayor a 0
                int color = ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null); //without theme
                tvChatTime.setTextColor(color);

                //Cargamos el numero de mensajes no leidos
                tvMensajesNoLeidos.setText(String.valueOf(metadata.getMensajesNoLeidos()));
                tvMensajesNoLeidos.setVisibility(View.VISIBLE);
            }
        }

    }


    public void setOnClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public ConversacionMetadata getConversacionMetadata(int position){

        return conversacionesMetadata.get(position);
    }

    public void setConversaciones(ArrayList<ConversacionMetadata> conversacionesMetadata) {

        this.conversacionesMetadata = conversacionesMetadata;
        this.notifyItemInserted(this.getItemCount());
    }

    public void updateConversacionMetadata(ConversacionMetadata metadata) {

        int indice;
        boolean existe = false;

        for ( indice = 0;  indice < this.conversacionesMetadata.size(); indice++ ) {

            ConversacionMetadata conversacionMetadata = this.conversacionesMetadata.get(indice);

            if ( Integer.compare(metadata.getId(), conversacionMetadata.getId()) == 0 ) {

                conversacionesMetadata.set(indice, metadata);
                existe = true;
                break;
            }
        }

        if ( !existe ) {

            this.conversacionesMetadata.add(metadata);
            this.notifyItemInserted(this.conversacionesMetadata.size());
        }
        else {

            this.notifyItemChanged(indice);
        }
    }
}
