package com.jw.lightwallet.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;

public class DaemonRPC {
	public String 	address;
	public Map		params;
	String			status;
	
	public DaemonRPC () {
		address = "http://moneroclub.com:8880";
	}
	
	public void getinfo () {
		Map params = new HashMap();
		params.put("jsonrpc", "2.0");
		params.put("id", "test");
		params.put("method", "getinfo");
		
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl("http://somewhere.net");
		httpGet.setContent(HttpParametersUtils.convertHttpParameters(params));

		 Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                status = httpResponse.getResultAsString();
		                System.out.print(status);
		        }
		 
		        public void failed(Throwable t) {
		                status = "failed";
		                //do stuff here based on the failed attempt
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}

}
