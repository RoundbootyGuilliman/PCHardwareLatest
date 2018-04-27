package epam.javalab22.pchardware.dao;

import epam.javalab22.pchardware.entity.Product;

import java.util.List;
import java.util.Map;

public interface IProductDAO {

    List<Product> getAllProducts();
    List<Product> findByType(String type, String locale);
    Product findById(int id, String locale);
    void addProduct(Product product, Map<String, String> mapOfCharsEng,  Map<String, String> mapOfCharsRus);
    void deleteProduct(int productId);
}
