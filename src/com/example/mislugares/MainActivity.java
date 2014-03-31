package com.example.mislugares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Este método lanzará la actividad VistaLugar
	// pasándole como id del lugar a visualizar siempre 0.
	public void lanzarVistaLugar(View view) {

		/*
		 * Intent i = new Intent(this, VistaLugar.class); i.putExtra("id",
		 * (long)0); startActivity(i);
		 */
		
		
		/*
		 * 
		 * 
		 * En Java es posible crea un objeto sin que este disponga 
		 * de un identificador de objeto. Este tipo de objeto 
		 * se conoce como objeto anónimo.
		 * 
		 * 
		 * 
		 * */
		
		final EditText entrada = new EditText(this);
		entrada.setText("0");
		new AlertDialog.Builder(this).setTitle("Selección de lugar")
				.setMessage("indica su id:").setView(entrada)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						long id = Long.parseLong(entrada.getText().toString());
						Intent i = new Intent(MainActivity.this,
								VistaLugar.class);
						i.putExtra("id", id);
						startActivity(i);
					}
				})

				.setNegativeButton("Cancelar", null).show();

	}
}
