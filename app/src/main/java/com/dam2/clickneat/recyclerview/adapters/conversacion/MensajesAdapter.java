package com.dam2.clickneat.recyclerview.adapters.conversacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.utils.StringHelper;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.util.ArrayList;

/**
 * Created by Fernando on 18/12/2016.
 */

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ViewHolder> {

    private ArrayList<Mensaje> mensajes = new ArrayList();
    private int yo;

    public MensajesAdapter(int yo){

        this(yo, new ArrayList<Mensaje>());
    }

    public MensajesAdapter( int yo, ArrayList<Mensaje> messages)  {

        this.mensajes       = messages;

        //Cargamos el identificador del usuario
        this.yo             = yo;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes){

        this.mensajes = mensajes;
        this.notifyDataSetChanged();
    }

    @Override
    public MensajesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li   = LayoutInflater.from(parent.getContext());
        View view;

        if ( viewType == 0 ) {

            view   = li.inflate(R.layout.mensaje_recibido, parent, false);
        }
        else {

            view   = li.inflate(R.layout.mensaje_enviado, parent, false);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MensajesAdapter.ViewHolder holder, int position) {

        Mensaje mensaje = mensajes.get(position);
        holder.loadMensaje(mensaje);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public int getItemViewType(int position) {

        Mensaje mensaje  = mensajes.get(position);

        int usuarioEnvia = mensaje.getUsuarioEnvia();

        return Integer.compare(usuarioEnvia, yo) == 0 ? 1 : 0;
    }

    public void addMensaje(Mensaje mensaje ) {

        //Debido a que el servicio llama en dos ocasiones comprobamos que el obejto no exista para insertarlo
        for (Mensaje mss : mensajes) {

            if (mss.getId() != 0 && mss.getId() == mensaje.getId()) {

                return;
            }
        }

        this.mensajes.add(mensaje);
        notifyItemInserted(this.mensajes.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EmojiconTextView tvMensaje;
        public TextView tvHora;

        public ViewHolder(View itemView) {

            super(itemView);

            this.tvMensaje = (EmojiconTextView) itemView.findViewById(R.id.emojiconTextView);
            this.tvHora    = (TextView) itemView.findViewById(R.id.textview_time);

        }

        public void loadMensaje(Mensaje mensaje) {

            this.tvMensaje.setText(mensaje.getTextoMensaje());
            this.tvHora.setText(StringHelper.hourToString(mensaje.getFechaEnvio()));

        }

    }



}

