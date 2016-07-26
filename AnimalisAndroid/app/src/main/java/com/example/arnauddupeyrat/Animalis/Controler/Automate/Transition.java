package com.example.arnauddupeyrat.Animalis.Controler.Automate;

import android.util.Log;

import java.util.HashMap;

public class   Transition {
	private IAction action;
	private HashMap<String,Object> event;
	private HashMap<String,Object> eventRecu;
	private Etat cible;
	
	public Transition(Etat cible,HashMap<String, Object> event2)
	{
		this.cible = cible;
		this.event = event2;
	}
	
	public void setAction(IAction a) { action = a;}
	public Etat getCible() { return cible; }
	public HashMap<String, Object> getEventRecu() { return eventRecu; }
	
	public void setEventRecu(HashMap<String, Object> eventRecu){this.eventRecu = eventRecu;}
	
	public boolean predicatRespecte(HashMap<String,Object> eventRecu)
	{
		Log.d(Transition.class.getName(),"predicatRespecte(), see if the predicat is repected");
		
		if(eventRecu == null && event == null)
			return true;
		if(eventRecu == null && event != null)
			return false;
		
		return eventRecu.get("Event").equals(event.get("Event"));
	}
	
	public void faireAction()
	{
		Log.d(Transition.class.getName(),"faireAction(), do action on "+cible.getId());
		action.doInBackground(null);
	}
	
	public String toString()
	{
		return "["+event.get("Event")+" - "+cible.getLabel()+"]";
	}

	public IAction getAction() {
		return action;
	}
}
