package com.example.emergency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class OberkommandantKoor extends Activity {

	private Intent i;
	Button btnWeitere;
	Button addKommandant;
	Button btnTruppen;
	LinearLayout ll;
	TextView newKommandant;
	HashMap<String, ArrayList<EditText>> todosMap;
	ArrayList<EditText> todos;
	ArrayList<TextView> kommandanten;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.oberkommandant_nexus);
			
			todosMap = new HashMap<String, ArrayList<EditText>>();
			todos = new ArrayList<EditText>();
			kommandanten =  new ArrayList<TextView>();
			
			addKommandant = (Button) findViewById(R.id.addKommandant);
			ll =(LinearLayout) findViewById(R.id.llOberkommandant);
			
			
			addKommandant.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Log.i("addTodo","hfhf");
					newKommandant = new TextView(getApplicationContext());
					newKommandant.setText("Kommandant");
					EditText todo = new EditText(getApplicationContext());
					String text = todo.getText().toString();
					todos.add(todo);
					todosMap.put(newKommandant.getText().toString(), todos);
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
							todos.add(todo);
							todosMap.put(newKommandant.getText().toString(), todos);
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
						ArrayList<EditText> todoList = todosMap.get(kommandanten.get(i).getText().toString());
						Log.i("komm2",todoList.get(0).getText().toString());
						Log.i("neue",String.valueOf(todoList.size()));
						for(int j =0; j<todoList.size();j++) {
							TodoFunction todoFunction = new TodoFunction();
							Log.i("todolisttext",todoList.get(j).getText().toString());
							Log.i("kommandanttext",kommandanten.get(i).getText().toString());
							JSONObject json = todoFunction.storeUser(kommandanten.get(i).getText().toString(), todoList.get(j).getText().toString(),"");
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
