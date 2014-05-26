package com.example.emergency.activities.fire;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.BaseActivity;
import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.color;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.entities.TruppMann;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TodoFunction;
import com.example.emergency.functions.TruppFunction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("ResourceAsColor")
public class TruppKoordination extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	Button addTrupp;
	Button btnTruppen;
	LinearLayout ll;
	LinearLayout llTodo;
	EditText newKommandant;
	HashMap<TextView, ArrayList<TruppMann>> todosMap;
	//ArrayList<EditText> todos;
	ArrayList<TextView> kommandanten;
	HashMap<String, ArrayList<TruppMann>> map;
	ArrayList<String> kommArray;
	String einsatzID;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	ArrayList<Boolean> allBack;
	HashMap<String, EditText> dauerList;
	HashMap<TruppMann, LinearLayout> layoutMap;
	final HashMap<String, Integer> backMap = new HashMap<String, Integer>();
	HashMap<String, LinearLayout> layoutKomm;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.truppkoordination_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			if(RefreshInfo.einsatz.isTerminate()) {
				einsatzinfos.setText("Kein Einsatz");
			} else {
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			}
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			todosMap = new HashMap<TextView, ArrayList<TruppMann>>();
			//todos = new ArrayList<EditText>();
			kommandanten =  new ArrayList<TextView>();
			
			addTrupp = (Button) findViewById(R.id.addTrupp);
			ll =(LinearLayout) findViewById(R.id.llTruppen);
			llTodo =(LinearLayout) findViewById(R.id.men);
			
			
			
			dauerList = new HashMap<String, EditText>();
			layoutMap = new HashMap<TruppMann, LinearLayout>(); 
			//backMap = new HashMap<String, Integer>();
			layoutKomm = new HashMap<String, LinearLayout>();
			
			map = new HashMap<String, ArrayList<TruppMann>>();
			kommArray = new ArrayList<String>();
			//ll = (LinearLayout) findViewById(R.id.todoll2);
			SharedPreferences settings = getSharedPreferences("shares",0);
			einsatzID = settings.getString("einsatzID", "nosuchvalue");
			if(einsatzID.equals("nosuchvalue")) {
				Log.i("Fehler", "keine Einsatz ID");
				finish();
			}
			
			TruppFunction truppFunction = new TruppFunction();
			JSONObject json = truppFunction.getTruppen(einsatzID);
			try {
				JSONArray json_user=json.getJSONArray("user");
				int arrayLength = json_user.length();

				for(int i=0; i<arrayLength; i++) {
					JSONObject jsonNext = json_user.getJSONObject(i);
					String trupp = jsonNext.getString("trupp");
					String name = jsonNext.getString("name");
					String zeitIn = jsonNext.getString("zeitIn");
					String zeitOut = jsonNext.getString("zeitOut");
					String id = jsonNext.getString("id");
					String back = jsonNext.getString("back");
					String timeBack = jsonNext.getString("timeBack");
					String difftime = jsonNext.getString("difftime");

					if(map.containsKey(trupp)) {
						TruppMann t = new TruppMann(name);
						t.setId(Integer.parseInt(id));
						t.setZeitIn(zeitIn);
						t.setZeitOut(zeitOut);
						t.setTrupp(trupp);
						t.setName(name);
						t.setTimeBack(timeBack);
						t.setDifftime(difftime);
						if(back.equals("1")) {
							t.setBack(true);
						} else {
							t.setBack(false);
						}
						ArrayList<TruppMann> truppMannList = map.get(trupp);
						truppMannList.add(t);
						map.put(trupp, truppMannList);
					} else {
						TruppMann t = new TruppMann(name);
						t.setId(Integer.parseInt(id));
						t.setZeitIn(zeitIn);
						t.setZeitOut(zeitOut);
						t.setTrupp(trupp);
						t.setName(name);
						t.setTimeBack(timeBack);
						t.setDifftime(difftime);
						if(back.equals("1")) {
							t.setBack(true);
						} else {
							t.setBack(false);
						}
						kommArray.add(trupp);
						ArrayList<TruppMann> truppMannList = new ArrayList<TruppMann>();
						truppMannList.add(t);
						map.put(trupp, truppMannList);
					}
				}
	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i =0;i<kommArray.size();i++) {
				
				allBack = new ArrayList<Boolean>();
				
				ArrayList<TruppMann> todos = map.get(kommArray.get(i));
				final TextView viewKomm = new TextView(getApplicationContext());
				viewKomm.setText(kommArray.get(i));
				viewKomm.setTextColor(getResources().getColor(R.color.black_overlay));
				viewKomm.setTextSize(20);
				viewKomm.setPadding(5, 20, 5, 5);
				kommandanten.add(viewKomm);
				final LinearLayout llh1 = new LinearLayout(getApplicationContext());
				llh1.setId(2);
				layoutKomm.put(kommArray.get(i), llh1);
				Log.i("llh1",llh1.toString());
				ll.addView(llh1);
				
				LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
                lpT.leftMargin = 30;
                lpT.width = 300;
                viewKomm.setLayoutParams(lpT);
				llh1.addView(viewKomm);
				
				TextView dauer = new TextView(getApplicationContext());
				dauer.setText("Dauer: ");
				dauer.setTextColor(getResources().getColor(R.color.black_overlay));
				dauer.setTextSize(20);
				LinearLayout.LayoutParams lpD = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
                lpD.leftMargin = 30;
                lpD.width = 60;
                dauer.setLayoutParams(lpD);
				llh1.addView(dauer);
				
				final EditText dauerTxt = new EditText(getApplicationContext());
				//wenn difftime - irgendwas ist dann soll abgelaufen dort stehen oder abgelaufen seit
				if(todos.get(0).getDifftime().charAt(0) != '-') {
				dauerTxt.setText(todos.get(0).getDifftime());
				} else {
					dauerTxt.setText("abgelaufen");
				}
				dauerTxt.setTextColor(getResources().getColor(R.color.black_overlay));
				dauerTxt.setTextSize(20);
				LinearLayout.LayoutParams lpDt = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
                lpDt.leftMargin = 10;
                lpDt.width = 150;
                dauerTxt.setLayoutParams(lpDt);
				llh1.addView(dauerTxt);
				dauerList.put(viewKomm.getText().toString(), dauerTxt);
				
				final Button start = new Button(getApplicationContext());
				start.setText("Losschicken");
				LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
                lpb.leftMargin = 20;
                lpb.width = 150;
                start.setLayoutParams(lpb);
                start.setId(1);
				//llh1.addView(start);
				
				ArrayList<TextView> todosEdit = new ArrayList<TextView>();
				for(int j =0;j<todos.size();j++) {
				
					final LinearLayout llh = new LinearLayout(getApplicationContext());
					int viewIndex = ll.indexOfChild(llh1);
					ll.addView(llh,viewIndex+j+1);
					layoutMap.put(todos.get(j),llh);
					
					TextView viewTodo = new TextView(getApplicationContext());
					viewTodo.setText(todos.get(j).getName());
					viewTodo.setTextColor(getResources().getColor(R.color.black_overlay));
					viewTodo.setTextSize(18);
					//todos.get(j).setEditText(viewTodo);
					final String id = String.valueOf(todos.get(j).getId());
					
					
					Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
					todosEdit.add(viewTodo);
					todosMap.put(viewKomm, todos);
					
					TextView timeIn = new TextView(getApplicationContext());
					timeIn.setText(todos.get(j).getZeitIn());
					timeIn.setTextColor(getResources().getColor(R.color.black_overlay));
					timeIn.setTextSize(20);
					
					final Button back = new Button(getApplicationContext());
					back.setText("Zurück");
					back.setId(5);
					
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lp.leftMargin = 30;
	                lp.topMargin = 10;
	                lp.width = 300;
	                viewTodo.setLayoutParams(lp);
					llh.addView(viewTodo);
					
					LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lp1.leftMargin = 30;
	                lp1.topMargin = 10;
	                lp1.width = 300;
	                timeIn.setLayoutParams(lp1);
					llh.addView(timeIn);
					
					if(todos.get(j).getTimeBack().equals("0000-00-00 00:00:00")) {
						LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		                lp2.topMargin = 5;
		                lp2.rightMargin = 30;
		                back.setLayoutParams(lp2);
						llh.addView(back);
						
						if(backMap.containsKey(kommArray.get(i))) {
							int anzahlBtn = backMap.get(kommArray.get(i));
							backMap.put(kommArray.get(i), anzahlBtn+1);
						} else {
							backMap.put(kommArray.get(i), 1);
						}
						allBack.add(false);
					} else {
						TextView timeOut = new TextView(getApplicationContext());
						timeOut.setText(todos.get(j).getTimeBack());
						timeOut.setTextColor(getResources().getColor(R.color.black_overlay));
						timeOut.setTextSize(20);
						LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		                lp2.leftMargin = 30;
		                lp2.topMargin = 10;
		                lp2.width = 150;
		                timeOut.setLayoutParams(lp2);
						llh.addView(timeOut);
						//um festzustellen ob alle Männer von dieser Truppe schon zurück sind
						allBack.add(true);
					}
					
					
					final TruppMann man = todos.get(j);
					
					/**
					 * delete ausgewähltes todo
					 * wird nicht mehr angezeigt und vom server entfernt
					 */
					back.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							TruppFunction truppFunction = new TruppFunction();
							JSONObject json = truppFunction.setBack(id);
							
							String success = null;
							try {
								success = json.getString("success").toString();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (success.equals("1")) {
								TextView timeOut = new TextView(getApplicationContext());
								java.util.Date date= new java.util.Date();
								Timestamp now = new Timestamp(date.getTime());
								timeOut.setText(now.toString());
								timeOut.setTextColor(getResources().getColor(R.color.black_overlay));
								timeOut.setTextSize(20);
								
								LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
				                lp1.leftMargin = 30;
				                lp1.topMargin = 10;
				                lp1.width = 150;
				                timeOut.setLayoutParams(lp1);
								llh.addView(timeOut);
								
								
								
								man.setBack(true);
								ArrayList<TruppMann> listTrupp = todosMap.get(viewKomm);
								boolean all = true;
								for(int i =0; i<listTrupp.size(); i++) {
									TruppMann t = listTrupp.get(i);
									if(!t.getBack()) {
										all = false;
									}
								}
								
								if(all) {
									addDeleteButton(llh1, start);
								}
								
								llh.removeView(back);
								/**allBack.add(true);
								allBack.remove(allBack.indexOf(false));
								addDeleteButton(allBack, llh1, start);*/
						
							}
						}
					});
					
				}
				Button delete = new Button(getApplicationContext());
				
				//wenn alle Männer aus diesem Trupp zurück sind loschcicknen btn wieder anzeigen
				if(!allBack.contains(false)) {
					llh1.addView(start);
					delete.setText("Löschen");
					LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                //lpd.rightMargin = 30;
	                lpd.width = 150;
	                delete.setLayoutParams(lpd);
	                llh1.addView(delete);
				}
				
				/**
				 * startet neuen Trupp einsatz
				 */
			
				start.setOnClickListener(new View.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(View view) {
						TruppFunction truppFunction = new TruppFunction();
						//SharedPreferences settings = getSharedPreferences("shares",0);
						//String einsatzID = settings.getString("einsatzID", "nosuchvalue");
						ArrayList<TruppMann> men = todosMap.get(viewKomm);
						JSONObject json = truppFunction.startEinsatzTextView(dauerTxt.getText().toString(), men, viewKomm.getText().toString(), einsatzID);
						recreate();
					}
				});
				
				/**
				 * Trupp löschen
				 */
				delete.setOnClickListener(new View.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(View view) {
						TruppFunction truppFunction = new TruppFunction();
						JSONObject json = truppFunction.deleteTrupp(viewKomm.getText().toString());
						recreate();
					}
				});
				/**Button addMann = new Button(getApplicationContext());
				addMann.setText("+ Mann");
				addMann.setLayoutParams(new LayoutParams(200,60));
				ll.addView(addMann);
				
				addMann.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						int index = ll.indexOfChild(view);
						EditText todo = new EditText(getApplicationContext());
						String text = todo.getText().toString();
						ArrayList<TruppMann> todosArray = new ArrayList<TruppMann>();
						TruppMann t = new TruppMann(text);
						t.setEditText(todo);
						t.setName(text);
						todosArray.add(t);
						todosMap.put(viewKomm, todosArray);
						
						todo.setTextColor(R.color.black_overlay);
						ll.addView(todo, index);
					}
				});*/
			}
				
				
				
				
			
			addTrupp.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Log.i("addTodo","hfhf");
					newKommandant = new EditText(getApplicationContext());
					newKommandant.setText("Trupp");
					newKommandant.setTextColor(R.color.black_overlay);
					
					final LinearLayout llh1 = new LinearLayout(getApplicationContext());
					int index = ll.indexOfChild(view);
					ll.addView(llh1,index+1);
					
					LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lpT.leftMargin = 30;
	                lpT.width = 400;
	                newKommandant.setLayoutParams(lpT);
					llh1.addView(newKommandant);
					
					final TextView dauer = new TextView(getApplicationContext());
					dauer.setText("Dauer: ");
					dauer.setTextColor(getResources().getColor(R.color.black_overlay));
					dauer.setTextSize(20);
					LinearLayout.LayoutParams lpD = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lpD.leftMargin = 30;
	                lpD.width = 150;
	                dauer.setLayoutParams(lpD);
					llh1.addView(dauer);
					
					final EditText dauerTxt = new EditText(getApplicationContext());
					dauerTxt.setText("in min");
					dauerTxt.setTextColor(getResources().getColor(R.color.black_overlay));
					dauerTxt.setTextSize(20);
					LinearLayout.LayoutParams lpDt = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lpDt.leftMargin = 10;
	                lpDt.width = 120;
	                dauerTxt.setLayoutParams(lpDt);
					llh1.addView(dauerTxt);
					dauerList.put(newKommandant.getText().toString(), dauerTxt);
					
					Button start = new Button(getApplicationContext());
					start.setText("Losschicken");
					LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lpb.leftMargin = 20;
	                lpb.width = 200;
	                start.setLayoutParams(lpb);
					llh1.addView(start);
					
					
					EditText todo = new EditText(getApplicationContext());
					String text = todo.getText().toString();
					final ArrayList<TruppMann> todosArray = new ArrayList<TruppMann>();
					TruppMann t = new TruppMann(text);
					t.setEditText(todo);
					t.setName(text);
					todosArray.add(t);
					todosMap.put(newKommandant, todosArray);
					
					kommandanten.add(newKommandant);
					
					Button addTodo = new Button(getApplicationContext());
					addTodo.setText("+ Mann");
					addTodo.setLayoutParams(new LayoutParams(200,60));

					
					
					todo.setTextColor(R.color.black_overlay);
					
					ll.addView(todo,index+2);
					ll.addView(addTodo,index+3);
					
					/**
					 * fügt neues Todo hinzu und speichert Todo in Hashmap mit allen Todos
					 */
					addTodo.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							int index = ll.indexOfChild(view);
							EditText todo = new EditText(getApplicationContext());
							String text = todo.getText().toString();
							TruppMann t = new TruppMann(text);
							t.setEditText(todo);
							t.setName(text);
							todosArray.add(t);
							todosMap.put(newKommandant, todosArray);
							//kommandanten.add(newKommandant);
							todo.setTextColor(R.color.black_overlay);
							ll.addView(todo, index);
						}
					});
					
					/**
					 * startet neuen Trupp einsatz
					 */
					start.setOnClickListener(new View.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(View view) {
							TruppFunction truppFunction = new TruppFunction();
							//SharedPreferences settings = getSharedPreferences("shares",0);
							//String einsatzID = settings.getString("einsatzID", "nosuchvalue");
							ArrayList<TruppMann> men = todosMap.get(newKommandant);
							Log.i("test",men.get(0).getEditText().getText().toString());
							Log.i("einsatzID",einsatzID);
							JSONObject json = truppFunction.startEinsatz(dauerTxt.getText().toString(), men, newKommandant.getText().toString(), einsatzID);
							recreate();
						}
					});
					
					
					
				}
			});
			Button speichern = new Button(getApplicationContext());
			speichern.setText("Speichern");
			ll.addView(speichern);
			
			/**
			 * speichern aller vorhandenen todos, 
			 * interiert über hashmap mit allen todos
			 * verbindet sich mit server
			 */
			speichern.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Log.i("komm",kommandanten.get(0).getText().toString());
					for(int i =0; i<kommandanten.size();i++) {
						Log.i("for1",String.valueOf(i));
						ArrayList<TruppMann> todoList = todosMap.get(kommandanten.get(i));
						//Log.i("komm2",todoList.get(0).getText().toString());
						//Log.i("neue",String.valueOf(todoList.size()));
						for(int j =0; j<todoList.size();j++) {
							TodoFunction todoFunction = new TodoFunction();
							//Log.i("todolisttext",todoList.get(j).getText().toString());
							Log.i("kommandanttext",kommandanten.get(i).getText().toString());
							String id;
							if (String.valueOf(todoList.get(j).getId()) != null) {
									id = String.valueOf(todoList.get(j).getId());
							} else {
								id =String.valueOf(-1);
							}
							Log.i("TodoListe", todoList.get(j).getEditText().getText().toString());
							Log.i("id", id);
							String textMann;
							if(todoList.get(j).getEditText()!=null) {
								textMann = todoList.get(j).getEditText().getText().toString();
							} else {
								textMann=todoList.get(j).getName();
							}
								
							JSONObject json = todoFunction.storeUser(kommandanten.get(i).getText().toString(), textMann,"", id);
							/**
							 * aus json die id auslesen und in Todo abspeichern
							 */
							try {
								JSONObject json_user=json.getJSONObject("user");
								String newID = json_user.getString("id");
								Log.i("newID",newID);
								todoList.get(j).setId(Integer.valueOf(newID));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					
					
				}
			});
			
			//start scheduler für timeDiff
			LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
			s.scheduleTimeDiff(dauerList, einsatzID, map, this, layoutMap, layoutInflater, findViewById(R.id.einsatzinfos));
			
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startTodo(View v) {
		i= new Intent(this, ToDoFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startTicker(View v) {
		i= new Intent(this, TickerFire.class);
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");
		 String username = settings.getString("username", "nosuchvalue");
		 Log.i("einsatzrefresh",einsatzID);
		 
		 
		 
		 LoginFunctions func = new LoginFunctions();
		 JSONObject json = func.getEinsatz(username);
		 try {
				if (json.getString("success") != null) {
				     String res = json.getString("success");
				     if(Integer.parseInt(res) == 1){
				    	 JSONObject jObj = json.getJSONObject("user");
				    	 einsatzID = jObj.getString("einsatzID");
				    	
				    	 
				    	 SharedPreferences.Editor editor = settings.edit();
		            	   editor.remove("einsatzID");
		            	   editor.putString("einsatzID", einsatzID);
		            	   editor.commit();
				     }
				 }
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfos),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
	
	@SuppressLint("NewApi")
	public void startDropdown(View v) {
		PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.popupmenu, popup.getMenu());
	    final View menu = this.findViewById(R.id.einsatzinfos);
	    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	    	   
	    	   @SuppressLint("CommitPrefEdits")
			@Override
	    	   public boolean onMenuItemClick(MenuItem item) {
	    		   switch(item.getItemId()){  
	               case R.id.menu1: 
	            	   SharedPreferences settings = getSharedPreferences("shares",0);
	          		 	String username = settings.getString("username", "nosuchvalue");
	            	   LoginFunctions func = new LoginFunctions();
	            	   JSONObject json = func.terminate(username);
	            	   
	            	   
	            	   SharedPreferences.Editor editor = settings.edit();
	            	   editor.remove("einsatzID");
	            	   editor.putString("einsatzID", "0");
	            	   editor.commit();
	            	   
	            	   RefreshInfo refreshInfo = new RefreshInfo();
	   				refreshInfo.refresh(menu,"0");
	            	   
	            	   return true;
	               case R.id.menu2:
	            	   i= new Intent(getApplicationContext(), StartChoice.class);
	            	   s.stopHandlerText();
	            	   startActivity(i);	
	            	   overridePendingTransition(R.layout.fadeout, R.layout.fadein);
	            	   SharedPreferences settings2 = getSharedPreferences("shares",0);
	            	   SharedPreferences.Editor editor2 = settings2.edit();
	            	   editor2.clear();
	            	   editor2.commit();
	            	   finish();
	            	   return true;
	    		   }
				return false;
	    	   }

	    	  });
	    popup.show();

	}
	
	public void changeTimeBack(LinearLayout llNeu, String timeBack, String trupp) {
		TextView timeOut = new TextView(getApplicationContext());
		
		//nicht now sondern zeit aus der db
		//nur auslesen nicht in db speichern (ist nur dafür da falls wer anderer das ändert)
		timeOut.setText(timeBack);
		timeOut.setTextColor(getResources().getColor(R.color.black_overlay));
		timeOut.setTextSize(20);
		
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
        lp1.leftMargin = 30;
        lp1.topMargin = 10;
        lp1.width = 150;
        timeOut.setLayoutParams(lp1);
        llNeu.addView(timeOut);
		
		llNeu.removeView(llNeu.findViewById(5));
		
		Log.i("trupp", trupp);
		Log.i("backMap", backMap.toString());
		int backBtns= backMap.get(trupp);
		backMap.put(trupp, backBtns-1);
		if(backMap.get(trupp).equals(0)) {
			Log.i("backMapp","0");
			LinearLayout llp = layoutKomm.get(trupp);
			showDeleteStartBtn(llp, (Button)llp.findViewById(1), trupp);
			
		}
	}
	
	public void addDeleteButton(LinearLayout llh1, Button start) {
		Button delete = new Button(getApplicationContext());
		
		//wenn alle Männer aus diesem Trupp zurück sind loschcicknen btn wieder anzeigen
		
			llh1.addView(start);
			delete.setText("Löschen");
			LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
            //lpd.rightMargin = 30;
            lpd.width = 150;
            delete.setLayoutParams(lpd);
            llh1.addView(delete);
		
	}
	
	public void showDeleteStartBtn(LinearLayout llTrupp, Button start, String trupp) {
		
		Button delete = new Button(getApplicationContext());
		
		delete.setText("Löschen");
		LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
            //lpd.rightMargin = 30;
        lpd.width = 150;
        delete.setLayoutParams(lpd);
        
        Button start2 = new Button(getApplicationContext());
		start2.setText("Losschicken");
		LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
        lpb.leftMargin = 20;
        lpb.width = 150;
        start2.setLayoutParams(lpb);
        
            
        llTrupp.addView(start2);
        llTrupp.addView(delete);
        
        clickListener(delete, start2, trupp);
	}
	
	public void clickListener(Button delete, Button start, final String trupp) {
		start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TruppFunction truppFunction = new TruppFunction();
				//SharedPreferences settings = getSharedPreferences("shares",0);
				//String einsatzID = settings.getString("einsatzID", "nosuchvalue");
				ArrayList<TruppMann> men = map.get(trupp);
				//dauerList odern dauer ist manchmal null
				Log.i("men",men.get(0).getName());
				Log.i("trupp",trupp);
				EditText dauer = dauerList.get(trupp);
				Log.i("dauer",dauer.getText().toString());
				JSONObject json = truppFunction.startEinsatz(dauer.getText().toString(), men, trupp, einsatzID);
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View view) {
				TruppFunction truppFunction = new TruppFunction();
				JSONObject json = truppFunction.deleteTrupp(trupp);
				recreate();
			}
		});
	}
}
