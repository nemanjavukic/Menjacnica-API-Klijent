package menjacnica.domain.operacije;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import menjacnica.domain.Valuta;
import menjacnica.jsonrates.JsonRateApiKomunikacija;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AzuriranjeKursneListe {
	
	private final static String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute(){
		
		LinkedList<Valuta> valute= new LinkedList<Valuta>();
		
     try {
		FileReader reader = new FileReader(putanjaDoFajlaKursnaLista);
			
			Gson gson = new GsonBuilder().create();
			
			JsonObject valuta = gson.fromJson(reader, JsonObject.class);
			JsonArray valuteJson = valuta.get("valute").getAsJsonArray();
			
			for (int i = 0; i < valuteJson.size(); i++) {
				
				JsonObject valutaJson = (JsonObject) valuteJson.get(i);
				Valuta novi= new Valuta();
				novi.setNaziv(valutaJson.get("naziv").getAsString());
				novi.setKurs(valutaJson.get("kurs").getAsDouble());
				valute.add(novi);
				
			}
	} catch (JsonSyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonIOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return valute;
	}
	public void upisiValute (LinkedList<Valuta> valute, GregorianCalendar datum){
		
		JsonObject valuta= new JsonObject();
		valuta.addProperty("datum", datum.getTime().toString());
		
		JsonArray valuteJson= new JsonArray();
		
		for (int i = 0; i < valute.size(); i++) {
			JsonObject va=new JsonObject();
			va.addProperty("naziv", valute.get(i).getNaziv());
			va.addProperty("kurs", valute.get(i).getKurs());
			valuteJson.add(va);
		}
		valuta.add("valute", valuteJson);
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(putanjaDoFajlaKursnaLista)));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			String valuteST = gson.toJson(valuta);
			
			out.println(valuteST);
			out.close();
		} catch (IOException e) {
			System.out.println("Greska: " + e.getMessage());
		}
		
	}
	public void azurirajValute(){
		
      LinkedList<Valuta> valute =ucitajValute();
      String[] nazivi= new String[valute.size()];
      for (int i = 0; i < valute.size(); i++) {
		nazivi[i]=valute.get(i).getNaziv();
	}
      valute.clear();
      JsonRateApiKomunikacija comm=new JsonRateApiKomunikacija();
      valute=comm.vratiKurseve(nazivi);
      GregorianCalendar dat= new GregorianCalendar();
      upisiValute(valute, dat);
	}
}
