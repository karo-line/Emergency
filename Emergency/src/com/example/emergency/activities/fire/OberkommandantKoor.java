package com.example.emergency.activities.fire;

import java.util.ArrayList;
import java.util.Collection;
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
import com.example.emergency.R.drawable;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.entities.Todo;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TodoFunction;

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
public class OberkommandantKoor extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	Button addKommandant;
	Button btnTruppen;
	LinearLayout ll;
	LinearLayout llTodo;
	EditText newKommandant;
	HashMap<EditText, ArrayList<Todo>> todosMap;
	//ArrayList<EditText> todos;
	ArrayList<EditText> kommandanten;
	HashMap<String, ArrayList<Todo>> map;
	ArrayList<String> kommArray;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.oberkommandant_nexus);
			
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
			
			todosMap = new HashMap<EditText, ArrayList<Todo>>();
			//todos = new ArrayList<EditText>();
			kommandanten =  new ArrayList<EditText>();
			
			addKommandant = (Button) findViewById(R.id.addKommandant);
			ll =(LinearLayout) findViewById(R.id.llOberkommandant);
			llTodo =(LinearLayout) findViewById(R.id.todos);
			
			
			
			
			
			map = new HashMap<String, ArrayList<Todo>>();
			kommArray = new ArrayList<String>();
			//ll = (LinearLayout) findViewById(R.id.todoll2);
			
			TodoFunction todoFunction = new TodoFunction();
			JSONObject json = todoFunction.loginUser("");
			try {
				JSONArray json_user=json.getJSONArray("user");
				int arrayLength = json_user.length();

				for(int i=0; i<arrayLength; i++) {
					JSONObject jsonNext = json_user.getJSONObject(i);
					String kommandant = jsonNext.getString("kommandant");
					String todo = jsonNext.getString("todo");
					String done = jsonNext.getString("done");
					String id = jsonNext.getString("id");

					if(map.containsKey(kommandant)) {
						Todo t = new Todo(todo);
						t.setId(Integer.parseInt(id));
						t.setDone(Integer.parseInt(done));
						ArrayList<Todo> todos = map.get(kommandant);
						todos.add(t);
						map.put(kommandant, todos);
					} else {
						Todo t = new Todo(todo);
						t.setId(Integer.parseInt(id));
						t.setDone(Integer.parseInt(done));
						kommArray.add(kommandant);
						ArrayList<Todo> todos = new ArrayList<Todo>();
						todos.add(t);
						map.put(kommandant,todos);
					}
				}
	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i =0;i<kommArray.size();i++) {
				ArrayList<Todo> todos = map.get(kommArray.get(i));
				final EditText viewKomm = new EditText(getApplicationContext());
				viewKomm.setText(kommArray.get(i));
				viewKomm.setTextColor(getResources().getColor(R.color.black_overlay));
				viewKomm.setTextSize(20);
				viewKomm.setPadding(5, 10, 5, 5);
				kommandanten.add(viewKomm);
				ll.addView(viewKomm);
				ArrayList<EditText> todosEdit = new ArrayList<EditText>();
				for(int j =0;j<todos.size();j++) {
					
					final LinearLayout llh = new LinearLayout(getApplicationContext());
					int viewIndex = ll.indexOfChild(viewKomm);
					ll.addView(llh,viewIndex+j+1);
				
					EditText viewTodo = new EditText(getApplicationContext());
					
					
					viewTodo.setText(todos.get(j).getTodoText());
					viewTodo.setTextColor(getResources().getColor(R.color.black_overlay));
					todos.get(j).setEditText(viewTodo);
					final String id = String.valueOf(todos.get(j).getId());
					
					Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
					todosEdit.add(viewTodo);
					todosMap.put(viewKomm, todos);
					
					ImageButton delete = new ImageButton(getApplicationContext());
					//Uri imgUri=Uri.parse("drawable/ic_action_discard");
					delete.setImageResource(R.drawable.ic_action_discard);
					
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lp.leftMargin = 30;
	                lp.topMargin = 10;
	                lp.width = 700;
	                viewTodo.setLayoutParams(lp);
					llh.addView(viewTodo);
					
					LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
	                lp2.topMargin = 5;
	                lp2.rightMargin = 30;
	                delete.setLayoutParams(lp2);
					llh.addView(delete);
					
					/**
					 * delete ausgewähltes todo
					 * wird nicht mehr angezeigt und vom server entfernt
					 */
					delete.setOnClickListener(new View.OnClickListener() {
						@SuppressLint("NewApi")
						public void onClick(View view) {
							TodoFunction todoFunction = new TodoFunction();
							JSONObject json = todoFunction.deleteTodo(id);
							recreate();
						}
					});
					
				}
				Button addTodo = new Button(getApplicationContext());
				addTodo.setText("+ ToDo");
				addTodo.setLayoutParams(new LayoutParams(200,60));
				ll.addView(addTodo);
				
				addTodo.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						int index = ll.indexOfChild(view);
						EditText todo = new EditText(getApplicationContext());
						String text = todo.getText().toString();
						ArrayList<Todo> todosArray = new ArrayList<Todo>();
						Todo t = new Todo(text);
						t.setEditText(todo);
						todosArray.add(t);
						todosMap.put(viewKomm, todosArray);
						
						todo.setTextColor(R.color.black_overlay);
						ll.addView(todo, index);
					}
				});
			}
				
				
				
				
			
			addKommandant.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Log.i("addTodo","hfhf");
					newKommandant = new EditText(getApplicationContext());
					newKommandant.setText("Kommandant");
					EditText todo = new EditText(getApplicationContext());
					String text = todo.getText().toString();
					final ArrayList<Todo> todosArray = new ArrayList<Todo>();
					Todo t = new Todo(text);
					t.setEditText(todo);
					todosArray.add(t);
					todosMap.put(newKommandant, todosArray);
					Log.i("addTodo","gfhj");
					//Log.i("todosMap",todosMap.get("Kommandant").get(0));
					kommandanten.add(newKommandant);
					
					Button addTodo = new Button(getApplicationContext());
					addTodo.setText("+ ToDo");
					addTodo.setLayoutParams(new LayoutParams(200,60));

					/**MarginLayoutParams params = new LinearLayout.LayoutParams();
					params.width = 200; params.leftMargin = 100; params.bottomMargin = 20;
					addTodo.setLayoutParams(params);*/
					
					newKommandant.setTextColor(R.color.black_overlay);
					todo.setTextColor(R.color.black_overlay);
					int index = ll.indexOfChild(view);
					ll.addView(newKommandant,index+1);
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
							Todo t = new Todo(text);
							t.setEditText(todo);
							todosArray.add(t);
							todosMap.put(newKommandant, todosArray);
							//kommandanten.add(newKommandant);
							todo.setTextColor(R.color.black_overlay);
							ll.addView(todo, index);
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
						ArrayList<Todo> todoList = todosMap.get(kommandanten.get(i));
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
							JSONObject json = todoFunction.storeUser(kommandanten.get(i).getText().toString(), todoList.get(j).getEditText().getText().toString(),"", id);
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
}
