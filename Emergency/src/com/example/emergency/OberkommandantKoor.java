package com.example.emergency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("ResourceAsColor")
public class OberkommandantKoor extends Activity {

	private Intent i;
	Button btnWeitere;
	Button addKommandant;
	Button btnTruppen;
	LinearLayout ll;
	EditText newKommandant;
	HashMap<EditText, ArrayList<Todo>> todosMap;
	//ArrayList<EditText> todos;
	ArrayList<EditText> kommandanten;
	HashMap<String, ArrayList<Todo>> map;
	ArrayList<String> kommArray;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.oberkommandant_nexus);
			
			todosMap = new HashMap<EditText, ArrayList<Todo>>();
			//todos = new ArrayList<EditText>();
			kommandanten =  new ArrayList<EditText>();
			
			addKommandant = (Button) findViewById(R.id.addKommandant);
			ll =(LinearLayout) findViewById(R.id.llOberkommandant);
			
			
			
			
			
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
				
					int viewIndex = ll.indexOfChild(viewKomm);
					EditText viewTodo = new EditText(getApplicationContext());
					
					
					viewTodo.setText(todos.get(j).getTodoText());
					viewTodo.setTextColor(getResources().getColor(R.color.black_overlay));
					todos.get(j).setEditText(viewTodo);
					
					int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
					todosEdit.add(viewTodo);
					todosMap.put(viewKomm, todos);
					
					ll.addView(viewTodo,viewIndex+j+1);
				}
				Button addTodo = new Button(getApplicationContext());
				addTodo.setText("+ Todo");
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
					addTodo.setText("+ Todo");
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
							JSONObject json = todoFunction.storeUser(kommandanten.get(i).getText().toString(), todoList.get(j).getTodoText(),"", id);
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
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosKoordination));
	}
	
	public void back(View v) {
		 finish();
				
	}
}
