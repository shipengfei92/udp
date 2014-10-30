package app.udp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import java.io.FileWriter;
import java.io.IOException;

public class udpmainatt extends Activity implements OnClickListener,SeekBar.OnSeekBarChangeListener, OnItemClickListener, OnItemSelectedListener 
{
	private static final String[] m = {"1ºÅµÆ","2ºÅµÆ","3ºÅµÆ","4ºÅµÆ"};
	private EditText etremoteip;
	private EditText etremoteport;
	private EditText etlocalport;
	private EditText etrdata;
	private EditText etsdata;
	private EditText etlogfilepath;
//	private CheckBox cbshex;
//	private CheckBox cbrhex;
	//private Spinner spscharcodes;
	//private Spinner sprcharcodes;
	private Button btnconnect;
	private Button btnwindowsopen;
	private Button btnwindowsuspend;
	private Button btnwindowstop;
	private Button btnreadstatus;
	private Button btndisconnect;
	private SeekBar bar1,bar2,bar3,bar4;
	private ProgressBar btnstatusBar;
	private Spinner spinner1;
	private ArrayAdapter<String> adapter;
//	private Button btnsend;
	private int numOfLight=1;
	int []light=new int[5];
	
	private udpthread ut = null;
	
	private void initUI()
	{
		etremoteip = (EditText)findViewById(R.id.etremoteip);
		etremoteport = (EditText)findViewById(R.id.etremoteport);
		etlocalport = (EditText)findViewById(R.id.etlocalport);
	//	etrdata = (EditText)findViewById(R.id.etrdata);
	//	etrdata.setMovementMethod(ScrollingMovementMethod.getInstance());
	//	etsdata = (EditText)findViewById(R.id.etsdata);
	//	cbshex = (CheckBox)findViewById(R.id.cbshex);
	//	cbrhex = (CheckBox)findViewById(R.id.cbrhex);
	//	cbshex.setChecked(true);
	//	cbrhex.setChecked(true);
	//	cbshex.setOnCheckedChangeListener(this);
	//	cbrhex.setOnCheckedChangeListener(this);
		btnconnect = (Button)findViewById(R.id.btnconnect);
		btnconnect.setOnClickListener(this);
		btnwindowsopen=(Button)findViewById(R.id.windowsopen);
		btnwindowsopen.setOnClickListener(this);
		btnwindowstop=(Button)findViewById(R.id.windowstop);
		btnwindowstop.setOnClickListener(this);
		btnwindowsuspend=(Button)findViewById(R.id.windowsuspend);
		btnwindowsuspend.setOnClickListener(this);
		
		btnreadstatus=(Button)findViewById(R.id.readstatus);
		btnreadstatus.setOnClickListener(this);
		
		bar1=(SeekBar)findViewById(R.id.seekBar1);
		bar1.setOnSeekBarChangeListener(this);
		bar2=(SeekBar)findViewById(R.id.seekBar2);
		bar2.setOnSeekBarChangeListener(this);
		bar3=(SeekBar)findViewById(R.id.seekBar3);
		bar3.setOnSeekBarChangeListener(this);
		bar4=(SeekBar)findViewById(R.id.seekBar4);
		bar4.setOnSeekBarChangeListener(this);
		btnstatusBar=(ProgressBar)findViewById(R.id.statusBar);
		btnstatusBar.setClickable(false);
		btnstatusBar.setMax(100);
	//	btnstatusBar.setProgress(50);
		btndisconnect = (Button)findViewById(R.id.btndisconnect);
		btndisconnect.setEnabled(false);
		btndisconnect.setOnClickListener(this);
		
		spinner1 =(Spinner)findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(this);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		spinner1.setVisibility(View.VISIBLE);
		
		
	//	btnsend = (Button)findViewById(R.id.btnsend);
	//	btnsend.setEnabled(false);
	//	btnsend.setOnClickListener(this);
		initCharCodes();
		
		ut = new udpthread(etrdata);
	}
	
	private void initCharCodes()
	{
		/*
		spscharcodes = (Spinner)findViewById(R.id.spscharcodes);
		sprcharcodes = (Spinner)findViewById(R.id.sprcharcodes);
		spscharcodes.setEnabled(false);
		sprcharcodes.setEnabled(false);
		spscharcodes.setOnItemSelectedListener(this);
		sprcharcodes.setOnItemSelectedListener(this);
		ArrayAdapter<String> itemvalues = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,codes);
		itemvalues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spscharcodes.setAdapter(itemvalues);
		sprcharcodes.setAdapter(itemvalues);*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, 0, 0, R.string.menuexit);
	//	menu.add(0, 1, 1, R.string.menusave);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId()==0)
			finish();
//		if (item.getItemId()==1)
//		{
//			SaveToFileDlg().show();
//		}
		//if (item.getItemId()==2)
			//etrdata.setText("");
		return super.onOptionsItemSelected(item);
	}

	private void uiState(boolean state)
	{
		btnconnect.setEnabled(state);
		btndisconnect.setEnabled(!state);
		bar1.setEnabled(!state);
		bar2.setEnabled(!state);
		bar3.setEnabled(!state);
		bar4.setEnabled(!state);
		spinner1.setEnabled(!state);
		btnstatusBar.setEnabled(!state);
		btnreadstatus.setEnabled(!state);
//		btnsend.setEnabled(!state);
		etremoteip.setEnabled(state);
		etremoteport.setEnabled(state);
		etlocalport.setEnabled(state);
	//	cbshex.setEnabled(state);
	//	cbrhex.setEnabled(state);
		btnwindowsopen.setEnabled(!state);
	
		btnwindowstop.setEnabled(!state);

		btnwindowsuspend.setEnabled(!state);
	}
	/*
	private void SaveToFile(String FilePath)
	{
		/*File f = new File(FilePath);
		if (!f.exists())
		{
			showMessage(getResources().getString(R.string.alertsavefiletilte),FilePath + getResources().getString(R.string.alertsavefilepatherror));
			return;
		}
		String filevalue = etrdata.getText().toString().trim();
		if (filevalue.trim().equals("")) return;
		try
		{
			FileWriter fw = new FileWriter(FilePath,true);
			fw.write(filevalue);
			fw.close();
			fw = null;

			showMessage(getResources().getString(R.string.alertsavefiletilte),getResources().getString(R.string.alertsavefileok));
		}catch(IOException ie)
		{
			showMessage(getResources().getString(R.string.alertsavefiletilte),getResources().getString(R.string.alertsavefilefail) + ie.getMessage());
		}
	}*/
	
/*
	private AlertDialog SaveToFileDlg()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		final View dlgView = inflater.inflate(R.layout.logfilesavedlg, null);
		Builder dlg = new AlertDialog.Builder(this);
		dlg.setTitle(R.string.alertsavefiletilte);
		dlg.setView(dlgView);
		dlg.setPositiveButton(R.string.alertok, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
	//			etlogfilepath = (EditText)dlgView.findViewById(R.id.etsavefilepath);
				String s = etlogfilepath.getText().toString().trim();
				if (s.trim().equals("")) return;
				SaveToFile(s);
			}
		});
		dlg.setNegativeButton(R.string.alertcancel, null);
		
		return dlg.create();
	}*/
	 
	
	
	private void showMessage(String Title,String Info)
	{
		Builder alertdlg = new AlertDialog.Builder(this);
		alertdlg.setTitle(Title);
		alertdlg.setMessage(Info);
		alertdlg.setPositiveButton(R.string.alertok, null);
		alertdlg.show();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initUI();
    }
	public String checkout1(int id)
	{
		int s=0x7e^0x01^0x04^0x00^0x12^0x34^0x01^0x01^0x11^id;
		
		return Integer.toHexString(s);
	}


	public void onClick(View v)
	{
		if(v.equals(btnreadstatus)){
			ut.SendData("7e01040012340101110"+numOfLight+checkout1(numOfLight));
	//	int rec=Integer.parseInt(ut.recvData());
	//	int rec=ut.recvData();
	//	btnstatusBar.setProgress(rec);
		btnstatusBar.setProgress(light[numOfLight]);
		
			
			
		}
		
		if(v.equals(btnwindowsopen)){
			ut.SendData("7e0003001234030e0254");	
		}
		if(v.equals(btnwindowsuspend)){
			ut.SendData("7e00030012340307025d");	
		}
		if(v.equals(btnwindowstop)){
			ut.SendData("7e0003001234030f0255");	
		}
		
		if (v.equals(btnconnect))
		{
			String RemoteIP = etremoteip.getText().toString().trim();
			int RemotePort = Integer.parseInt(etremoteport.getText().toString().trim());
			int LocalPort = Integer.parseInt(etlocalport.getText().toString().trim());
			ut.setRemoteIP(RemoteIP);
			ut.setRemotePort(RemotePort);
			ut.setLocalPort(LocalPort);
		//	ut.setRHex(cbrhex.isChecked());
		//	ut.setSHex(cbshex.isChecked());
			if (ut.ConnectSocket())
				uiState(false);
		}
		if (v.equals(btndisconnect))
		{
			ut.DisConnectSocket();
			uiState(true);
		}
	//	if (v.equals(btnsend))
	//	{
	//		String SData = etsdata.getText().toString().trim();
	//		if (!SData.trim().equals(""))
	//			ut.SendData(SData);
	//	}
	}

	
/*	protected void onDestroy()
	{
		super.onDestroy();
		Process.killProcess(Process.myPid());
	}*/
	
	public String checkout(int id,int arg1)
	{
		int s=0x7e^0x02^0x05^0x00^0x12^0x34^0x01^0x02^0x11^id^((arg1/10)*16+arg1%10);
		
		return Integer.toHexString(s);
	}


	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
		if(arg0==bar1){
			light[1]=arg1;
			ut.SendData("7e020500123401021101"+arg1+checkout(01,arg1));
		}
		if(arg0==bar2){
			light[2]=arg1;
			ut.SendData("7e020500123401021102"+arg1+checkout(02,arg1));
		}
		if(arg0==bar3){
			light[3]=arg1;
			ut.SendData("7e020500123401021103"+arg1+checkout(03,arg1));
		}
		if(arg0==bar4){
			light[4]=arg1;
			ut.SendData("7e020500123401021104"+arg1+checkout(04,arg1));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		numOfLight=arg2+1;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}