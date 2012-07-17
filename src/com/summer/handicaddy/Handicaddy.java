package com.summer.handicaddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class Handicaddy extends Activity {



	@Override
	public void onCreate(Bundle savedInstanceState) {	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GridView mainMenu = (GridView) findViewById(R.id.main_menu);
		
		RoundCatalog fromAdding = (RoundCatalog) getIntent().getSerializableExtra("round_catalog_after_adding");
		if (fromAdding == null)
			readCatalogFromFile();
		else 
			catalog = fromAdding;
		
		if (catalog.numRounds() == 0) { //temporarily hard-code rounds to test passing
			Course bocaMuni = new Course("Boca Municipal", 72, 135, 69.5, "Champ", 6600.0);
			Course miznerCC = new Course("Mizner CC", 72, 151, 75.9, "Champ", 7206.0);
			
			Round a = new Round(bocaMuni.getTeeBoxes()[0], 76, new Date(5, 10, 2011));
			Round b = new Round(miznerCC.getTeeBoxes()[0], 79, new Date(6, 10, 2011));
			Round c = new Round(bocaMuni.getTeeBoxes()[0], 81, new Date(7, 10, 2011));
			Round d = new Round(miznerCC.getTeeBoxes()[0], 83, new Date(8, 10, 2011));
			Round e = new Round(bocaMuni.getTeeBoxes()[0], 87, new Date(9, 10, 2011));
			Round f = new Round(miznerCC.getTeeBoxes()[0], 81, new Date(10, 10, 2011));
			Round g = new Round(bocaMuni.getTeeBoxes()[0], 79, new Date(11, 10, 2011));
			Round h = new Round(miznerCC.getTeeBoxes()[0], 76, new Date(12, 10, 2011));

			
			catalog.addRound(a); //add a round
			catalog.addRound(b); //add a round
			catalog.addRound(c); //add a round
			catalog.addRound(d); //add a round
			catalog.addRound(e); //add a round
			catalog.addRound(f); //add a round
			catalog.addRound(g); //add a round
			catalog.addRound(h); //add a round
			writeCatalogToFile();
			System.out.println("Cock " + catalog.numRounds());
		}
		
		
		
		mainMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(Handicaddy.this, "You just clicked numba " + arg2, Toast.LENGTH_SHORT).show();
				switch (arg2) {
					case 0:
						Intent i = new Intent(getApplicationContext(), RoundHistory.class);
						i.putExtra("round_catalog", catalog);
						startActivity(i);
						break;
					case 1:
						Intent r = new Intent(getApplicationContext(), Stats.class);
						r.putExtra("round_catalog", catalog);
						startActivity(r);
						break;
					default: 
						Toast.makeText(getApplicationContext(), "You clicked wrong shit", Toast.LENGTH_SHORT).show();
				}
			}	
		} );
		
		mainMenu.setAdapter(new ImageAdapter(this));
		
		System.out.println("Started Handicaddy " + catalog.numRounds());
		
		Button newRound = (Button) findViewById(R.id.start_round);
		newRound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TYPE_OF_ROUND); //when button is clicked, let user choose how to enter round
			}
		});
		
	}
	
	public void onPause() 
	{
		System.out.println("onPause() " + catalog.numRounds());
		super.onPause();
		writeCatalogToFile();
	}
	
	public void onResume() 
	{
		super.onResume();
		//readCatalogFromFile();
	}
	

	public Dialog onCreateDialog (int id, Bundle args) { //called by system with id from showDialog(int)
		Dialog dialog;
		switch(id) {
		case TYPE_OF_ROUND:
			AlertDialog.Builder builder = new AlertDialog.Builder(Handicaddy.this);
			builder.setMessage("How would you like to enter your round?").setCancelable(true)
			.setPositiveButton("Completed Round", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent i = new Intent(getApplicationContext(), EnterRound.class);
					i.putExtra("round_catalog", catalog);
					startActivity(i);
				}
			})
			.setNegativeButton("Hole-by-Hole", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			dialog = alert;
			break;
		default:
			dialog = null;

		}
		return dialog;
	}
	
	private void readCatalogFromFile() {
		ObjectInputStream in;
		try {
			File file = new File(getFilesDir(), "RoundCatalog");
			if (!file.exists()) file.createNewFile();
			in = new ObjectInputStream(new FileInputStream(file));
			catalog = (RoundCatalog) in.readObject();
			
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			Calendar today = Calendar.getInstance();
			
			System.out.println(new Date(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR)).getDate() - 200 + ":)");
			if (catalog == null) catalog = new RoundCatalog(new Date(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR)).getDate(), 1000);
		}

	}
	
	private void writeCatalogToFile() {
		ObjectOutputStream out;
		File file = new File(getFilesDir(), "RoundCatalog");
		
		if (file.exists()) file.delete(); //unsure if necessary
		
		try {
			file.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(catalog);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	static final int TYPE_OF_ROUND = 0;
	RoundCatalog catalog;

}