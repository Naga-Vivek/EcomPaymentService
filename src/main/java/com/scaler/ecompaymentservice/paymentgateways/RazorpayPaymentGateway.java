package com.scaler.ecompaymentservice.paymentgateways;

import com.razorpay.PaymentLink;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {
    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String generatePaymentLink(Long orderId, Long amount, String phoneNumber) throws RazorpayException {
        //RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
        LocalDateTime localDateTime = LocalDateTime.now();
        long expiryAt = localDateTime.plusMinutes(20).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        //paymentLinkRequest.put("accept_partial",true);
        //paymentLinkRequest.put("first_min_partial_amount",100);()
        paymentLinkRequest.put("expire_by",expiryAt);
        paymentLinkRequest.put("reference_id","RP"+orderId);
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name", "Anand Kumar");
        customer.put("contact",phoneNumber);
        customer.put("email","anand.kumar@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink paymentlink = razorpayClient.paymentLink.create(paymentLinkRequest);

        return paymentlink.toString();
    }
}