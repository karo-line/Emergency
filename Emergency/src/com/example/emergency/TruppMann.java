package com.example.emergency;

import java.util.Calendar;
import java.sql.Date;
import java.sql.Timestamp;

import android.text.format.Time;
import android.widget.EditText;

public class TruppMann {

	private String name ="Name";
	private String trupp;
	private int id;
	private String zeitIn;
	private String zeitOut;
	private EditText editText = null;
	private boolean back;
	private String timeBack;
	private String difftime;
	
	public String getDifftime() {
		return difftime;
	}

	public void setDifftime(String difftime) {
		this.difftime = difftime;
	}

	public String getTimeBack() {
		return timeBack;
	}

	public void setTimeBack(String timeBack) {
		this.timeBack = timeBack;
	}

	public TruppMann(String name) {
		this.name = name;
		
		
	}

	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public String getZeitIn() {
		return zeitIn;
	}

	public void setZeitIn(String zeitIn) {
		this.zeitIn = zeitIn;
	}
	
	public String getZeitOut() {
		return zeitOut;
	}

	public void setZeitOut(String zeitOut) {
		this.zeitOut = zeitOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTrupp() {
		return trupp;
	}

	public void setTrupp(String trupp) {
		this.trupp = trupp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}
}
