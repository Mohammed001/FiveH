package com.example.asus.fiveh.tests;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface FlowersAPI {

	@GET("/feeds/flowers.json")
	public void getFeed(Callback<List<Flower>> response);
	
}
