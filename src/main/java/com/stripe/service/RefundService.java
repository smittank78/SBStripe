package com.stripe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;

@Service
public class RefundService {
	
	@Value("${stripe.key}")
	private String stripe_key;

	private boolean flag;
	private Map<String, Object> params;
	private Map<String, String> metadata;
	private Map<String, String> info;
	private Refund refund;
	
	public String createRefund(String paymentIntentId) throws StripeException 
	{
		Stripe.apiKey = stripe_key;

		metadata = new HashMap<>();
		metadata.put("refund-to",paymentIntentId);
		
		Map<String, Object> params = new HashMap<>();		
		params.put("payment_intent", paymentIntentId);
		params.put("metadata", metadata);
		params.put("reason", "requested_by_customer");
//		params.put("refund_application_fee", true);
		refund = null;
		refund = Refund.create(params);
		System.out.println("refund-id : " + refund.getId());
		return refund.getId();
	}
	
	public Refund getRefund(String refundId) throws StripeException 
	{
		Stripe.apiKey = stripe_key;

		refund = null;
		refund = Refund.retrieve(refundId);
		System.out.println("refund retrived : " + refund.getId());
		return refund;		
	}
	
	public void cancleRefund(String refundId) throws StripeException 
	{
		Stripe.apiKey = stripe_key;

		refund = null;
		refund = Refund.retrieve(refundId);
		refund = refund.cancel();
		System.out.println("cancled refund-id : " + refund.getId());
		
	}
	public List getAllRefund() throws StripeException 
	{
		List listofRefund = new ArrayList();
		
		Stripe.apiKey = stripe_key;
		
		RefundCollection refunds = Refund.list(params);
		if(refunds != null)
		{
		refunds.getData().forEach(r -> {
			info = new HashMap<>();
			info.put("id",r.getId());
			info.put("refunded-on",new Date(r.getCreated()).toString());
			info.put("amount",r.getAmount().toString());
			info.put("receipt-no",r.getReceiptNumber());
			listofRefund.add(info);
		});
		}
		System.out.println(listofRefund);
		return listofRefund;
	}

	private void info(String string, String id) {
		// TODO Auto-generated method stub
		
	}
}