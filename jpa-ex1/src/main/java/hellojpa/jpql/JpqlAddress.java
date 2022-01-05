package hellojpa.jpql;

import javax.persistence.Embeddable;

@Embeddable
public class JpqlAddress {
    private String city;
    private String stret;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStret() {
        return stret;
    }

    public void setStret(String stret) {
        this.stret = stret;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
