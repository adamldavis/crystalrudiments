
package net.crystalrudiments.util;
/**
 * Object to store a person's address.
 * @version 1.0, 2005
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class Address {

    /*
     * address As String city As String state As String * 2 zip As String county
     * As String
     */
    /**
     * Address line.
     */    
    String address;

    /**
     * City name
     */    
    String city;

    /**
     * State name.
     */    
    String state;

    /**
     * Postal Zip code.
     */    
    String zip;

    /**
     * County or Country of residence.
     */    
    String county;

    /**
     * Initiales the address with only the address line filled.
     * @param s The first address line.
     */    
    public Address(String s) {
        this(s, "", "", "", "");
    }

    /**
     * Creates an Address with given values.
     * @param address Address line.
     * @param city City.
     * @param state State.
     * @param zip Zip code.
     * @param county Country or county, which ever you prefer.
     */
    public Address( String address, String city, String state, String zip, String county )
    {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.county = county;
    }
    /**
     * Returns a string representing this address.
     * @return Address|city|state|zip|country
     */
    public String toString() {
        String ret = address + "|" + city + "|" + state + "|" + zip + "|"
                + county;
        return ret;
    }
}//Address
