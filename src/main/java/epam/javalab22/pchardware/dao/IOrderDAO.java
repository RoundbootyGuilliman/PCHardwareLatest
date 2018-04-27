package epam.javalab22.pchardware.dao;

import epam.javalab22.pchardware.entity.Order;
import epam.javalab22.pchardware.entity.Product;

import java.util.List;
import java.util.Map;

public interface IOrderDAO {

    List<Order> getAllOrders();
    void placeAnOrder(Map<Product, Integer> shoppingCart, String username, String totalSum, long time);
    List<Order> findOrders(String username);
    void changeOrderStatus(String status, int orderId);
}
