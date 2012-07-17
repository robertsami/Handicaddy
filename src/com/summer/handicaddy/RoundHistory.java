package com.summer.handicaddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;



public class RoundHistory extends ListActivity{
	
	RoundCatalog catalog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.round_history);
		catalog = (RoundCatalog) getIntent().getSerializableExtra("round_catalog");
		//this.readCatalogFromFile();
		System.out.println((catalog == null) ? "Catalog is null" : "Catalog has " + catalog.numRounds());
		
		setListAdapter(new RoundArrayAdapter(this, R.layout.round_header, catalog.getAllRounds()));
		
		ListView lv = getListView();

	    EditText searchBar = (EditText)findViewById(R.id.search_bar);
	    searchBar.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	getListView().setFilterText(s.toString());
	        	if (s.toString().equals("") || s.toString() == null) {
	        		getListView().clearTextFilter();
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	
	        }
	    }); 
	    
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), RoundView.class);
				i.putExtra("round", (Round) getListView().getAdapter().getItem(arg2));
				startActivity(i);
			}
	    	
	    });
		
	}


	public void readCatalogFromFile() {
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
			if (catalog == null) catalog = new RoundCatalog(new Date(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR)).getDate() - 200, 1000);
		}

	}
	
}
