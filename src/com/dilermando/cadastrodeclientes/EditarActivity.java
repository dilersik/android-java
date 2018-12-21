package com.dilermando.cadastrodeclientes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditarActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar);
		
		Intent it = getIntent();
		int id = it.getIntExtra("id", 0);
		
		SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
		
		Cursor cursor = db.rawQuery("SELECT * FROM clientes WHERE _id = ?", new String[]{String.valueOf(id)});
		if (cursor.moveToFirst()) {
			EditText txtNome = (EditText) findViewById(R.id.txtNome);
			EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
			
			txtNome.setText(cursor.getString(1));
			txtEmail.setText(cursor.getString(2));
		}
		
		db.close();
	}
	
	public void editar(View v) {
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
				
				Intent it = getIntent();
				int id = it.getIntExtra("id", 0);
				
				if (db.update("clientes", cv, "_id = ?", new String[]{String.valueOf(id)}) > 0) {
					Toast.makeText(getBaseContext(), "Cliente alterado com sucesso", Toast.LENGTH_SHORT).show();
					finish(); // fecha activity e volta pra anterior
				} else {
					Toast.makeText(getBaseContext(), "Erro altera cliente", Toast.LENGTH_SHORT).show();
				}
				
				db.close();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void apagar(View v) {
		try {
			SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
			
			Intent it = getIntent();
			int id = it.getIntExtra("id", 0);			
			
			if (db.delete("clientes", "_id = ?", new String[]{String.valueOf(id)}) > 0) {
				Toast.makeText(getBaseContext(), "Cliente deletado com sucesso", Toast.LENGTH_SHORT).show();
				finish(); // fecha activity e volta pra anterior
			} else {
				Toast.makeText(getBaseContext(), "Erro deletar cliente", Toast.LENGTH_SHORT).show();
			}
			
			db.close();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		
		}
	}

}
