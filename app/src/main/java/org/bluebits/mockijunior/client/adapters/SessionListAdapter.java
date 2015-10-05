/**
 * 
 */
package org.bluebits.mockijunior.client.adapters;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.bluebits.mockijunior.client.model.Session;


/**
 * @author satyajit
 *
 */
public class SessionListAdapter implements SpinnerAdapter{
	Context context;
    List<Session> sessions;
   
    public SessionListAdapter(Context context ,List<Session> sessions){
        this.context =context;
        this.sessions = sessions;
    }
    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
   
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //TextView textview = (TextView) inflater.inflate(android.R.layout.simple_spinner_item, null);
        TextView textView = new TextView(context);
        textView.setText(sessions.get(position).getName());
      
        return textView;
    }

    @Override
    public int getViewTypeCount() {
        return android.R.layout.simple_spinner_item;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_spinner_item, null);
        textview.setText(sessions.get(position).getName());
      
        return textview;
    }
	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
	}
}
