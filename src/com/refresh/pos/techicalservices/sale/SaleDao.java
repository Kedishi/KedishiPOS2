package com.refresh.pos.techicalservices.sale;

import java.util.List;

import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.sale.Sale;

public interface SaleDao {

	Sale initiateSale(String startTime);

	void endSale(Sale sale, String endTime);

	int addLineItem(int saleId, LineItem lineItem);
	
	List<Sale> getAllSale();

	Sale getSaleById(int id);

	void clearSaleLedger();

	List<LineItem> getLineItem(int saleId);

	List<Sale> getAllSaleDuring(String start, String end);

}