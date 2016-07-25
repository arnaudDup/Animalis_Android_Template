package com.example.arnauddupeyrat.Animalis.Controler.Automate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Etat {
	public static int NumeroEtat;
	
	private ArrayList<Transition> transitionsPossibles;
	private String label;
	private int id;
	
	public Etat(String nom)
	{
		this.label = nom;
		transitionsPossibles = new ArrayList<Transition>();
		id = NumeroEtat;
		NumeroEtat++;
	}
	
	public void ajouterTransition(Transition t)
	{
		transitionsPossibles.add(t);
	}
	
	public int getId(){return id; }
	public String getLabel(){return label; }
	
	public Transition rechercherTransition(HashMap<String,Object> event)
	{
		Transition res = null;
		
		Iterator<Transition> i = transitionsPossibles.iterator();
		
		while(i.hasNext())
		{
			Transition tmp = i.next();
			if(tmp.predicatRespecte(event))
				res = tmp;
		}
		return res;
	}
}
