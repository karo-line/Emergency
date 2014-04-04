package com.example.emergency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoFire extends Activity {

	private Intent i;
	Button btnWeitere;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	LinearLayout ll;
	
	private HashMap<String, ArrayList<Todo>> map;
	private ArrayList<String> kommArray;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.todo_nexus);
			map = new HashMap<String, ArrayList<Todo>>();
			kommArray = new ArrayList<String>();
			ll = (LinearLayout) findViewById(R.id.todoll2);
			
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
						Todo t = new Todo(todo,Integer.parseInt(id),Integer.parseInt(done));
						ArrayList<Todo> todos = map.get(kommandant);
						todos.add(t);
						map.put(kommandant, todos);
					} else {
						Todo t = new Todo(todo,Integer.parseInt(id),Integer.parseInt(done));
						kommArray.add(kommandant);
						ArrayList<Todo> todos = new ArrayList<Todo>();
						todos.add(t);
						map.put(kommandant,todos);
					}
					
					
					Log.i("jsontodo",todo);
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i =0;i<kommArray.size();i++) {
				ArrayList<Todo> todos = map.get(kommArray.get(i));
				TextView viewKomm = new TextView(getApplicationContext());
				viewKomm.setText(kommArray.get(i));
				viewKomm.setTextColor(getResources().getColor(R.color.black_overlay));
				viewKomm.setTextSize(20);
				viewKomm.setPadding(5, 10, 5, 5);
				ll.addView(viewKomm);
				
				for(int j =0;j<todos.size();j++) {
					
					/**TextView viewTodo = new TextView(getApplicationContext());
					viewTodo.setText(todos.get(j));
					
					ll.addView(viewTodo);*/
					
					//int todoIndex = ll.indexOfChild(viewTodo);
					int viewIndex = ll.indexOfChild(viewKomm);
					CheckBox cb = new CheckBox(getApplicationContext());
					int done = todos.get(j).getDone();
					if(done == 0) {
						cb.setChecked(false);
					} else if (done==1) {
						cb.setChecked(true);
					}
					cb.setText(todos.get(j).getTodoText());
					cb.setTextColor(getResources().getColor(R.color.black_overlay));
					int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
					cb.setButtonDrawable(id);
					final int idtodo = todos.get(j).getId();
					final String strtodo = todos.get(j).getTodoText();
					
					cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
	                            boolean isChecked) {
							if (buttonView.isChecked()) {
								int i = idtodo;
								String s = strtodo;
								Log.i("todo2323",String.valueOf(i)+" "+s);
								
								TodoFunction todoFunction = new TodoFunction();
								JSONObject json = todoFunction.doneCheckbox(String.valueOf(i), String.valueOf(1));
							} else if (!buttonView.isChecked()) {
								int i = idtodo;
								TodoFunction todoFunction = new TodoFunction();
								JSONObject json = todoFunction.doneCheckbox(String.valueOf(i), String.valueOf(0));
							}
							
						}
					});
					ll.addView(cb,viewIndex+j+1);
				}
			}
			/**try {
				JSONArray json_user = json.getJSONArray("user");
				int arrayLength = json_user.length();
              
			} catch (JSONException e) {
                e.printStackTrace();
            }*/

			
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuFire.class);
		startActivity(i);		
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		startActivity(i);		
				
	}
	
	public void startTodo(View v) {
		i= new Intent(this, ToDoFire.class);
		startActivity(i);		
				
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosToDo));
	}
	
	public void back(View v) {
		 finish();
				
	}
}
