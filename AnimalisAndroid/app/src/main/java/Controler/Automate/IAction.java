package Controler.Automate;

import android.os.AsyncTask;

public abstract class IAction extends AsyncTask <Void, Integer, Void> {

	@Override
	abstract protected Void doInBackground(Void... params);

}
