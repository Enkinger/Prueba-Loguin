package com.estin.loginjava;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {


    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id = 0;

    public Adaptador(Activity a, ArrayList<Contacto> lista, daoContacto dao){
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contacto getItem(int position) {
        c = lista.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        c = lista.get(position);
        return c.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item,null);
        }
        c = lista.get(position);
        TextView nombre = (TextView)v.findViewById(R.id.t_nombre);
        TextView tel = (TextView)v.findViewById(R.id.t_telefono);
        TextView email = (TextView)v.findViewById(R.id.t_email);
        TextView edad = (TextView)v.findViewById(R.id.t_edad);
        Button editar = (Button)v.findViewById(R.id.editar);
        Button eliminar = (Button)v.findViewById(R.id.eliminar);

        nombre.setText(c.getNombre());
        tel.setText(c.getTelefono());
        email.setText(c.getEmail());
        edad.setText(""+c.getEdad());
        editar.setTag(position);
        eliminar.setTag(position);
        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //introducir dialogo
                int pos=Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre =(EditText)dialogo.findViewById(R.id.nombre);
                final EditText tel =(EditText)dialogo.findViewById(R.id.telefono);
                final EditText email =(EditText)dialogo.findViewById(R.id.email);
                final EditText edad =(EditText)dialogo.findViewById(R.id.edad);
                Button guardar =(Button)dialogo.findViewById(R.id.d_agregar);
                Button cancelar =(Button)dialogo.findViewById(R.id.d_cancelar);
                c = lista.get(pos);
                setId(c.getId());
                nombre.setText(c.getNombre());
                tel.setText(c.getTelefono());
                email.setText(c.getEmail());
                edad.setText(c.getEdad());
                guardar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        try {
                            c = new Contacto(getId(),nombre.getText().toString(),
                                    tel.getText().toString(),
                                    email.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()));
                            dao.editar(c);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        } catch (Exception e){
                            Toast.makeText(a,"ERROR",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // introducir dialogo eliminar
            }
        });


        return v;
    }
}
