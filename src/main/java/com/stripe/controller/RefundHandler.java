package com.stripe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.service.RefundService;

@RestController
@RequestMapping("/stripe/refund")
public class RefundHandler
{
	@Autowired
	private RefundService refundService;
	
	@GetMapping("/create/{payment_intent_id}")
	public String createRefund(@PathVariable("payment_intent_id") String paymentIntentId) 
	{
		try {	
			return refundService.createRefund(paymentIntentId);
		}
		catch (StripeException e) {
			System.out.println(e.getMessage());
			return null;			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;			
		}
	}
	@GetMapping("/retrive/{refund_id}")
	public String retriveRefund(@PathVariable("refund_id") String refundId) 
	{
		try {
			return refundService.getRefund(refundId).getId();
		}
		catch (StripeException e) {
			System.out.println(e.getMessage());
			return null;			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;			
		}
	}
	@GetMapping("/retriveAllRefunds")
	public List retriveAllRefund()
	{
		try {
			return refundService.getAllRefund();
		}
		catch (StripeException e) {
			System.out.println(e.getMessage());
			return null;			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;			
		}
	}
	@GetMapping("/cancle/{refund_id}")
	public boolean cancleRefund(@PathVariable("refund_id") String refundId) 
	{
		try {
			refundService.cancleRefund(refundId);
			return true;
		}
		catch (StripeException e) {
			System.out.println(e.getMessage());
			return false;			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;			
		}
	}
}