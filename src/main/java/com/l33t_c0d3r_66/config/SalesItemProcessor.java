package com.l33t_c0d3r_66.config;

import com.l33t_c0d3r_66.model.Sales;
import org.springframework.batch.item.ItemProcessor;


public class SalesItemProcessor implements ItemProcessor<Sales, Sales> {
    @Override
    public Sales process(Sales sales) throws Exception {
        return sales;
    }
}
