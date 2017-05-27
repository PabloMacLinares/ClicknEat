package com.dam2.clickneat.recyclerview.adapters.publicacion;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.clickneat.R;

import java.util.ArrayList;

/**
 * Created by ferna on 22/05/2017.
 */

public class PlatosAdapter extends RecyclerView.Adapter<PlatosAdapter.ViewHolder> {

    private ArrayList<String> platos;
    private boolean isEditable;

    public PlatosAdapter() {

        this(new ArrayList<String>());
    }

    public PlatosAdapter(ArrayList<String> platos) {

        this.platos = platos;
    }

    public void setPlatos(ArrayList<String> platos) {

        this.platos = platos;
        this.notifyDataSetChanged();
    }

    public void addPlato(String plato) {

        this.platos.add(plato);
        this.notifyItemInserted(this.platos.size());
    }

    public void addNewPlato() {

        this.addPlato("");
    }

    public void removePlato(int position) {

        if ( position >= 0 ) {

            this.platos.remove(position);
            this.notifyItemRemoved(position);
        }
    }

    public void updatePlato( String plato, int position ) {

        if ( position >= 0 ) {

            this.platos.remove(position);
            this.platos.add(position, plato);
            //this.notifyItemInserted(position);
        }
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public ArrayList<String> getPlatos() {

        for ( int i = 0; i < this.platos.size(); i++ ) {

            if (this.platos.get(i).trim().isEmpty()) this.removePlato(i);
        }

        return this.platos;
    }

    @Override
    public PlatosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view               = inflater.inflate(R.layout.activity_publicacion_item_platos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatosAdapter.ViewHolder holder, int position) {

        holder.viewPlato(this.platos.get(position));
    }

    @Override
    public int getItemCount() {
        return platos == null ? 0 : platos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

        private EditText edPlato;
        private ImageView btPlato;

        public ViewHolder(View view) {

            super(view);

            this.edPlato = (EditText) view.findViewById(R.id.edPlato);
            this.btPlato = (ImageView) view.findViewById(R.id.btPlato);


            //Agregamos el evento para que se muestre nuestro boton de eliminar elemento
            this.edPlato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    ViewHolder.this.btPlato.setVisibility(hasFocus && PlatosAdapter.this.isEditable ? View.VISIBLE : View.GONE);
                }
            });

            this.btPlato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if ( position >= 0 ) {

                        PlatosAdapter.this.removePlato(position);

                    }
                }
            });

            this.edPlato.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                //Se registran las opciones IME para controlar la introduccion del texto
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if ( (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT) ) {

                        int position = ViewHolder.super.getAdapterPosition();
                        if ( actionId == EditorInfo.IME_ACTION_NEXT && position == platos.size() - 1 ) {

                            addNewPlato();

                        }

                    }

                    //Devolvemos siempre falso para ocultar el teclado
                    return false;
                }
            });

            this.edPlato.addTextChangedListener(this);
            //Actualizamos los datos del plato en tiempo real
            /*this.edPlato.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if ( PlatosAdapter.this.getItemCount() > 0) {

                        int position = getAdapterPosition();

                        if ( position >= 0 ) {

                            PlatosAdapter.this.updatePlato(s.toString(), position);
                        }
                    }
                }
            });*/
        }

        public void viewPlato(String plato) {

            this.edPlato.setText(plato);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            int position = getAdapterPosition();
            PlatosAdapter.this.updatePlato(s.toString(), position);
        }
    }
}
