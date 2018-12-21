package com.dilermando.cadastrodeclientes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar);
	}
	
	public void onResume() {
		super.onResume();
		
		// 	criar DB
		SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
		
		// tabela
		StringBuilder sqlClientes = new StringBuilder();
//				sqlClientes.append("DROP TABLE clientes");
		sqlClientes.append("CREATE TABLE IF NOT EXISTS clientes (");
		sqlClientes.append("_id INTEGER PRIMARY KEY, ");
		sqlClientes.append("nome VARCHAR(70),");
		sqlClientes.append("email VARCHAR(100));");
		db.execSQL(sqlClientes.toString());
		
		Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);
		
		String[] from = {"_id", "nome"};
		int[] to = {R.id.txtvID, R.id.txtvNome};
		
		SimpleCursorAdapter ad = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_listar_model, cursor, from, to);
		
		ListView listView1 = (ListView) findViewById(R.id.listView1);		
		listView1.setAdapter(ad);
		
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SQLiteCursor c = (SQLiteCursor) parent.getAdapter().getItem(position);
				
				Intent it = new Intent(getBaseContext(), EditarActivity.class);
				it.putExtra("id", c.getInt(0));
				startActivity(it);
			}
		});
		
		db.close();
	}
	
}
