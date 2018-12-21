package com.dilermando.cadastrodeclientes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class InserirActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserir);
	}
	
	public void cadastrar(View v) {
		EditText txtNome = (EditText) findViewById(R.id.txtNome);
		EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
		
		if (txtNome.getText().toString().trim().length() <= 0) {
			txtNome.setError("Campo obrigatório");
			txtNome.requestFocus();
		} else if (txtEmail.getText().toString().trim().length() <= 0) {
			txtEmail.setError("Campo obrigatório");
			txtEmail.requestFocus();
		} else {
			try {
				SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
				
				ContentValues cv = new ContentValues();
				cv.put("nome", txtNome.getText().toString());
				cv.put("email", txtEmail.getText().toString());
				
				if (db.insert("clientes", "_id", cv) > 0) {
					Toast.makeText(getBaseContext(), "Cliente cadastrado com sucesso", Toast.LENGTH_SHORT).show();
					finish(); // fecha activity e volta pra anterior
				} else {
					Toast.makeText(getBaseContext(), "Erro cliente", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

}
