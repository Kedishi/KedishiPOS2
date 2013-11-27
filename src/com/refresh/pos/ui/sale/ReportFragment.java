package com.refresh.pos.ui.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.achartengine.GraphicalView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.Chart;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.UpdatableFragment;
import com.refresh.pos.ui.inventory.ProductDetailActivity;


public class ReportFragment extends UpdatableFragment {
	
	private SaleLedger saleLedger;
	List<Map<String, String>> saleList;
	private ListView saleLedgerListView;
	private EditText searchBox;
//	private GraphicalView mChartView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			saleLedger = SaleLedger.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		View view = inflater.inflate(R.layout.layout_report2, container, false);
		
		saleLedgerListView = (ListView) view.findViewById(R.id.saleListView);
		searchBox = (EditText) view.findViewById(R.id.searchBox);
		
		saleLedgerListView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
		    	  String id = saleList.get(position).get("id").toString();
		    	  Intent newActivity = new Intent(ReportFragment.this.getActivity().getBaseContext(), SaleDetailActivity.class);
		          newActivity.putExtra("id", id);
		          startActivity(newActivity);  
		      }     
		});
//		if (mChartView == null) {
//			LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart);
//			mChartView = (new Chart()).execute(getActivity().getBaseContext());
//			layout.addView(mChartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//		} else {
//			mChartView.repaint();
//		}
//		
		initUI();
		return view;
	}

	private void initUI() {
		searchBox.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
//				if (s.length() >= SEARCH_LIMIT) {
//					search();
//				}
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before,int count) {}
		});
		
	}
	
	private void showList(List<Sale> list) {

		saleList = new ArrayList<Map<String, String>>();
		for (Sale sale : list) {
			saleList.add(sale.toMap());
			Log.d("ledger", sale.getStartTime());
//			Log.d("ledger", sale.getEndTime());
		}
		
		SimpleAdapter sAdap = new SimpleAdapter(getActivity().getBaseContext() , saleList,
				R.layout.listview_saleledger2, new String[] { "id", "startTime", "total"},
				new int[] { R.id.sid, R.id.startTime , R.id.total});
		saleLedgerListView.setAdapter(sAdap);
	}

	@Override
	public void onResume() {
		Log.d("ledger",saleLedger.getAllSale().size()+"");
		super.onResume();
		update();
	}

	@Override
	public void update() {
		showList(saleLedger.getAllSale());
	}

}