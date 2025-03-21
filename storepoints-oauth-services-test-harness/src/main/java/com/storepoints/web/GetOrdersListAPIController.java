package com.storepoints.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOrdersListAPIController {
	
    @RequestMapping("/order/rest/orderlist/{accountId}")
    public String ordersList(@PathVariable String accountId) {
    	
    	String ordersListResponse=		"<GetOrderListResponse>"
    									+" <orderListResponse>" 
    									+"      <count>2</count>"
								    	+"      <marker>1270023312|296</marker>"
								    	+"      <orderDetails>"
								    	+"        <OrderDetails>"
								    	+"          <order>"
								    	+"            <orderId>320</orderId>"
								    	+"            <orderPlacedTime>1270036641807</orderPlacedTime>"
								    	+"           <orderValue>1068.77</orderValue>"
								    	+"            <orderStatus>OPEN</orderStatus>"
								    	+"            <orderType>BUY_WRITES</orderType>"
								    	+"            <orderTerm>GOOD_FOR_DAY</orderTerm>"
								    	+"            <priceType>MARKET</priceType>"
								    	+"            <limitPrice>0</limitPrice>"
								    	+"            <stopPrice>0</stopPrice>"
								    	+"            <legDetails>"
								    	+"              <LegDetails>"
								    	+"                <legNumber>1</legNumber>"
								    	+"                <symbolInfo>"
								    	+"                  <symbol>DELL</symbol>"
								    	+"                </symbolInfo>"
								    	+"                <symbolDescription>DELL INC COM</symbolDescription>"
								    	+"                <orderAction>BUY</orderAction>"
								    	+"                <orderedQuantity>100</orderedQuantity>"
								    	+"                <filledQuantity>0</filledQuantity>"
								    	+"                <executedPrice>0</executedPrice>"
								    	+"                <estimatedCommission>7.99</estimatedCommission>"
								    	+"                <estimatedFees>0</estimatedFees>"
								    	+"              </LegDetails>"
								    	+"              <LegDetails>"
								    	+"                <legNumber>2</legNumber>"
								    	+"                <symbolInfo>"
								    	+"                  <symbol>DLY</symbol>"
								    	+"                  <callPut>CALL</callPut>"
								    	+"                  <expYear>2010</expYear>"
								    	+"                  <expMonth>4</expMonth>"
								    	+"                  <expDay>17</expDay>"
								    	+"                  <strikePrice>10</strikePrice>"
								    	+"                </symbolInfo>"
								    	+"                <symbolDescription>DELL APR 10 Call</symbolDescription>"
								    	+"                <orderAction>SELL_OPEN</orderAction>"
								    	+"                <orderedQuantity>1</orderedQuantity>"
								    	+"                <filledQuantity>0</filledQuantity>"
								    	+"                <executedPrice>0</executedPrice>"
								    	+"                <estimatedCommission>8.74</estimatedCommission>"
								    	+"                <estimatedFees>0</estimatedFees>"
								    	+"              </LegDetails>"
								    	+"            </legDetails>"
								    	+"            <allOrNone>false</allOrNone>"
								    	+"          </order>"
								    	+"        </OrderDetails>"
								    	+"        <OrderDetails>"
								    	+"          <order>"
								    	+"            <orderId>317</orderId>"
								    	+"            <orderPlacedTime>1270031791517</orderPlacedTime>"
								    	+"            <orderExecutedTime>1270031819000</orderExecutedTime>"
								    	+"            <orderValue>242.61</orderValue>"
								    	+"            <orderStatus>EXECUTED</orderStatus>"
								    	+"            <orderType>EQ</orderType>"
								    	+"            <orderTerm>GOOD_FOR_DAY</orderTerm>"
								    	+"            <priceType>MARKET</priceType>"
								    	+"            <limitPrice>0</limitPrice>"
								    	+"            <stopPrice>0</stopPrice>"
								    	+"            <legDetails>"
								    	+"              <LegDetails>"
								    	+"                <legNumber>1</legNumber>"
								    	+"                <symbolInfo>"
								    	+"                  <symbol>MSFT</symbol>"
								    	+"                </symbolInfo>"
								    	+"                <symbolDescription>MICROSOFT CORP COM</symbolDescription>"
								    	+"                <orderAction>SELL_SHORT</orderAction>"
								    	+"                <orderedQuantity>10</orderedQuantity>"
								    	+"                <filledQuantity>10</filledQuantity>"
								    	+"                <executedPrice>1</executedPrice>"
								    	+"                <estimatedCommission>7.99</estimatedCommission>"
								    	+"                <estimatedFees>0</estimatedFees>"
								    	+"              </LegDetails>"
								    	+"            </legDetails>"
								    	+"           <allOrNone>false</allOrNone>"
								    	+"          </order>"
								    	+"        </OrderDetails>"
								    	+"      </orderDetails>"
								    	+"    </orderListResponse>"
								    	+"  </GetOrderListResponse>    	";
    	
    	
    	return ordersListResponse;

    }
    


}
