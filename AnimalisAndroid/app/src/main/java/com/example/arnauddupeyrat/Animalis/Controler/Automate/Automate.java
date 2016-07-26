package com.example.arnauddupeyrat.Animalis.Controler.Automate;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Automate {



	private Etat etatCourant;
	private Etat etatInit;
	
	public Automate()
	{
		Etat.NumeroEtat = 0;
		etats = new  ArrayList<Etat>();
	}
	
	public Etat ajouterEtat(String nom)
	{
		Etat e = new Etat(nom);
		etats.add(e.getId(),e);
		return e;
	}
	
	public void ajouterTransition(int idEtat, Transition t)
	{
		ajouterTransition(etats.get(idEtat),  t);
	}
	
	public void ajouterTransition(Etat e, Transition t)
	{
		e.ajouterTransition(t);
	}
	
	public void setEtatInitial(int idEtat)
	{
		setEtatInitial(etats.get(idEtat));
	}
	
	public void setEtatInitial(Etat e)
	{
		etatInit = e;
	}
	
	public void demarrer() {
		etatCourant = etatInit;
	}

	public ArrayList<Etat> getEtats() {
		return etats;
	}

	private ArrayList<Etat> etats;

	public Etat getEtatCourant() {
		return etatCourant;
	}

	public Etat getEtatInit() {
		return etatInit;
	}

	// iterate on event of the curent statee of the automate.
	public void consider(HashMap<String,Object> event)
	{
		Log.i(Automate.class.getName(),"consider(), consider event "+ event.get("Event")+" cur State : "+etatCourant.getLabel());
		
		Transition t = etatCourant.rechercherTransition(event);
		
		if(t == null) return;
		
		t.setEventRecu(event);
		t.faireAction();
		etatCourant = t.getCible();
		Log.d(Automate.class.getName(),"consider(), get target"+"{Etat"+etatCourant.getId()+" "+etatCourant.getLabel()+"}");
	}
}
