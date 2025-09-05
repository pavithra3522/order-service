package com.example.order_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="orders")
public class Order {
	@Id
	private String id;
	private String name;	
	private int price;
	private int quantity;
	
	public Order() {}
	/*public Order(String name, int price, int quantity) {
		this.name=name;
		this.price=price;
		this.quantity=quantity;
	}*/
	
	public String getId() { return id; }
	public void setId(String id) { this.id=id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name=name; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price=price; }
	
	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity=quantity; }	
		
}
