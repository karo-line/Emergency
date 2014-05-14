package com.example.emergency;

public class EinsatzInfo {
	String ort;
	String plz;
	String str;
	String stockwerk;
	String bei;
	String patient;
	String geschlecht;
	String anrufer;
	String telNummer;
	String weitereAlarmierung;
	String einsatzArt;
	String info;
	String aktualisiert;
	
	public String getAktualisiert() {
		return aktualisiert;
	}
	public void setAktualisiert(String aktualisiert) {
		this.aktualisiert = aktualisiert;
	}
	public void actualize(String ort, String plz, String str, String stockwerk, String bei, String patient, String geschlecht, String anrufer, String telNummer, String weitereAlarmierung, String einsatzArt, String info) {
		this.ort = ort;
		this.plz = plz;
		this.str = str;
		this.stockwerk = stockwerk;
		this.bei = bei;
		this.patient = patient;
		this.geschlecht = geschlecht;
		this.anrufer = anrufer;
		this.telNummer = telNummer;
		this.weitereAlarmierung = weitereAlarmierung;
		this.einsatzArt = einsatzArt;
		this.info = info;
	}
	public String getEinsatz() {
		String einsatz = "Einsatzort: "+ ort
		   +" "+plz
		   +"\n"+"Straße: "+str
		   +"\n"+"Stockwerk: "+stockwerk
		   +"\n"+"Bei: "+bei
		   +"\n"+"Patient: "+patient
		   +"\n"+"Geschlecht: "+geschlecht
		   +"\n"+"Anrufer: "+anrufer
		   +"\n"+"Zurückrufnummer: "+telNummer
		   +"\n"+"Weitere Alarmierung: "+weitereAlarmierung
		   +"\n"+"Einsatzart: "+einsatzArt
		   +"\n" +info;
		return einsatz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getStockwerk() {
		return stockwerk;
	}
	public void setStockwerk(String stockwerk) {
		this.stockwerk = stockwerk;
	}
	public String getBei() {
		return bei;
	}
	public void setBei(String bei) {
		this.bei = bei;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	public String getAnrufer() {
		return anrufer;
	}
	public void setAnrufer(String anrufer) {
		this.anrufer = anrufer;
	}
	public String getTelNummer() {
		return telNummer;
	}
	public void setTelNummer(String telNummer) {
		this.telNummer = telNummer;
	}
	public String getWeitereAlarmierung() {
		return weitereAlarmierung;
	}
	public void setWeitereAlarmierung(String weitereAlarmierung) {
		this.weitereAlarmierung = weitereAlarmierung;
	}
	public String getEinsatzArt() {
		return einsatzArt;
	}
	public void setEinsatzArt(String einsatzArt) {
		this.einsatzArt = einsatzArt;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
