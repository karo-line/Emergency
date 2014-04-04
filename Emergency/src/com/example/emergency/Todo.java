package com.example.emergency;

public class Todo {

	private String todoText;
	private int id;
	private int done;
	
	public Todo(String todoText, int id, int done) {
		this.todoText = todoText;
		this.id = id;
		this.done = done;
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
