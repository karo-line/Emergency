package com.example.emergency;

import android.widget.EditText;

public class Todo {

	private String todoText;
	private int id;
	private int done;
	private EditText editText;
	
	public Todo(String todoText) {
		this.todoText = todoText;
		
		
	}

	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public String getTodoText() {
		return todoText;
	}

	public void setTodoText(String todoText) {
		this.todoText = todoText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
