package io.tguduru.spring.jdbc.entity;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/5/18
 */
public class User {
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String state;
    private final String country;
    private final String zip;

    public User(String firstName, String lastName, String city, String state, String country, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }
}
