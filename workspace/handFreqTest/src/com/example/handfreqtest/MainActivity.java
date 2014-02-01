package com.example.handfreqtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import hanzhen.lin.handFreqTest.R;

public class MainActivity extends Activity {
	private Button clickButton;
	private Button sysButton;
	private TextView infoText;
	private TextView statText;
	private boolean started=false;
	private Counter clickCounter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickButton=(Button)this.findViewById(R.id.clickButton);
		sysButton=(Button)this.findViewById(R.id.sysButton);
		infoText=(TextView)this.findViewById(R.id.infoText);
		statText=(TextView)this.findViewById(R.id.statText);
		
		
		
		sysButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(started){
					stopCount();
					
				}else{
					startCount();
				}
			}
			
		});
		clickButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickCounter.newClick();
			}
		});
		clickButton.setClickable(false);
	}
	void stopCount() {
		started=false;
		sysButton.setText(this.getString(R.string.start_message));
		clickButton.setClickable(false);
		statText.setText(clickCounter.statResult());
	}
	void startCount(){
		clickCounter=new Counter();
		started=true;
		sysButton.setText(this.getString(R.string.stop_message));
		clickButton.setClickable(true);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
/*
 * 

public class MainActivity extends Activity {
	private Button clickButton;
	private TextView text1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickButton=(Button)this.findViewById(R.id.button1);
		text1=(TextView)this.findViewById(R.id.textView1);
		
		clickButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				text1.setText(text1.getText().toString()+"c");
				
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}

}

 */