package epam.javalab22.pchardware.entity;

import epam.javalab22.pchardware.util.TimeAndDateSupport;

import java.util.Map;

public class Order implements TimeAndDateSupport {

    private int orderId;
    private String username;
    private String total;
    private String status;
    private long time;
    private String date;
    private Map<Product, Integer> mapOfProducts;

    public Order() {}

    public Order(int orderId, String username, String total, String status, long time, Map<Product, Integer> mapOfProducts) {
        this.orderId = orderId;
        this.username = username;
        this.total = total;
        this.status = status;
        this.time = time;
        this.mapOfProducts = mapOfProducts;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    public Map<Product, Integer> getMapOfProducts() {
        return mapOfProducts;
    }

    public void setMapOfProducts(Map<Product, Integer> mapOfProducts) {
        this.mapOfProducts = mapOfProducts;
    }
}
