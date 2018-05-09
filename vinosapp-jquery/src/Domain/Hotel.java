package Domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Hotel {

    private int id;
    private String hotel_name;
    private int stars;
    private String city;
    private String description;
    private double price;

    public Hotel(int id, String hotel_name, int stars, String city, String description, double price) {
        this.id = id;
        this.hotel_name = hotel_name;
        this.stars = stars;
        this.city = city;
        this.description = description;
        this.price = price;
    }

    public Hotel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
