package com.example.emergency.activities.fire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.example.emergency.entities.Todo;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TodoFunction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
import android.widget.TextView;

public class ToDoFire extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	LinearLayout ll;
	
	private HashMap<String, ArrayList<Todo>> map;
	private ArrayList<String> kommArray;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.todo_nexus);
			
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
				viewKomm.setTextSize(25);
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
					cb.setTextSize(20);
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
