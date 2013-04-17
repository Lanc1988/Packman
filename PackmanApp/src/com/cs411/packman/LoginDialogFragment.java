package com.cs411.packman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.packman.R;

public class LoginDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.login_dialog, null))
	    // Add action buttons
	           .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	            	   // User touched the dialog's positive button
	                   EditText username = (EditText) LoginDialogFragment.this.getDialog().findViewById(R.id.username);
	                   EditText password = (EditText) LoginDialogFragment.this.getDialog().findViewById(R.id.password);
	                   
	                   MainActivity.setUserName(username.getText().toString());
	                   MainActivity.setPassword(password.getText().toString());
	                   
	                   LoginDialogFragment.this.getDialog().cancel();
	                   
	                   ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
	                   viewPager.forceLayout();
	                   viewPager.invalidate();
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   //LoginDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
}