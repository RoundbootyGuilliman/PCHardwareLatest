package epam.javalab22.pchardware.entity;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {

    private int id;
    private String name;
    private int price;
    private String type;
    private String img;
    private String vendor;
    private Map<String, String> mapOfCharacteristics;

    public Product() {}

    public Product(int id, String name, int price, String type, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.img = img;
    }

    public Map<String, String> getMapOfCharacteristics() {
        return mapOfCharacteristics;
    }

    public void setMapOfCharacteristics(Map<String, String> mapOfCharacteristics) {
        this.mapOfCharacteristics = mapOfCharacteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
