package com.maxmind.minfraud.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains subscores for many of the individual components that are
 * used to calculate the overall risk score.
 */
public final class Subscores {
    private final Double avsResult;
    private final Double billingAddress;
    private final Double billingAddressDistanceToIpLocation;
    private final Double browser;
    private final Double chargeback;
    private final Double country;
    private final Double countryMismatch;
    private final Double cvvResult;
    private final Double emailAddress;
    private final Double emailDomain;
    private final Double emailTenure;
    private final Double ipTenure;
    private final Double issuerIdNumber;
    private final Double orderAmount;
    private final Double phoneNumber;
    private final Double shippingAddressDistanceToIpLocation;
    private final Double timeOfDay;

    public Subscores(
            @JsonProperty("avs_result") Double avsResult,
            @JsonProperty("billing_address") Double billingAddress,
            @JsonProperty("billing_address_distance_to_ip_location") Double billingAddressDistanceToIpLocation,
            @JsonProperty("browser") Double browser,
            @JsonProperty("chargeback") Double chargeback,
            @JsonProperty("country") Double country,
            @JsonProperty("country_mismatch") Double countryMismatch,
            @JsonProperty("cvv_result") Double cvvResult,
            @JsonProperty("email_address") Double emailAddress,
            @JsonProperty("email_domain") Double emailDomain,
            @JsonProperty("email_tenure") Double emailTenure,
            @JsonProperty("ip_tenure") Double ipTenure,
            @JsonProperty("issuer_id_number") Double issuerIdNumber,
            @JsonProperty("order_amount") Double orderAmount,
            @JsonProperty("phone_number") Double phoneNumber,
            @JsonProperty("shipping_address_distance_to_ip_location") Double shippingAddressDistanceToIpLocation,
            @JsonProperty("time_of_day") Double timeOfDay
    ) {
        this.avsResult = avsResult;
        this.billingAddress = billingAddress;
        this.billingAddressDistanceToIpLocation = billingAddressDistanceToIpLocation;
        this.browser = browser;
        this.chargeback = chargeback;
        this.country = country;
        this.countryMismatch = countryMismatch;
        this.cvvResult = cvvResult;
        this.emailAddress = emailAddress;
        this.emailDomain = emailDomain;
        this.emailTenure = emailTenure;
        this.ipTenure = ipTenure;
        this.issuerIdNumber = issuerIdNumber;
        this.orderAmount = orderAmount;
        this.phoneNumber = phoneNumber;
        this.shippingAddressDistanceToIpLocation = shippingAddressDistanceToIpLocation;
        this.timeOfDay = timeOfDay;
    }

    public Subscores() {
        this(null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null);
    }

    /**
     * @return The risk associated with the AVS result. If present, this is a
     * value in the range 0.01 to 99.
     */
    @JsonProperty("avs_result")
    public Double getAvsResult() {
        return avsResult;
    }

    /**
     * @return The risk associated with the billing address. If present, this is
     * a value in the range 0.01 to 99.
     */
    @JsonProperty("billing_address")
    public Double getBillingAddress() {
        return billingAddress;
    }

    /**
     * @return The risk associated with the distance between the billing address
     * and the location for the given IP address. If present, this is a value in
     * the range 0.01 to 99.
     */
    @JsonProperty("billing_address_distance_to_ip_location")
    public Double getBillingAddressDistanceToIpLocation() {
        return billingAddressDistanceToIpLocation;
    }

    /**
     * @return The risk associated with the browser attributes such as the
     * User-Agent and Accept-Language. If present, this is a value in the range
     * 0.01 to 99.
     */
    @JsonProperty("browser")
    public Double getBrowser() {
        return browser;
    }

    /**
     * @return Individualized risk of chargeback for the given IP address on
     * your account and shop ID. This is only available to users sending
     * chargeback data to MaxMind. If present, this is a value in the range 0.01
     * to 99.
     */
    @JsonProperty("chargeback")
    public Double getChargeback() {
        return chargeback;
    }

    /**
     * @return The risk associated with the country the transaction originated
     * from. If present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("country")
    public Double getCountry() {
        return country;
    }

    /**
     * @return The risk associated with the combination of IP country, card
     * issuer country, billing country, and shipping country. If present, this
     * is a value in the  range 0.01 to 99.
     */
    @JsonProperty("country_mismatch")
    public Double getCountryMismatch() {
        return countryMismatch;
    }

    /**
     * @return The risk associated with the CVV result. If present, this is a
     * value in the range 0.01 to 99.
     */
    @JsonProperty("cvv_result")
    public Double getCvvResult() {
        return cvvResult;
    }

    /**
     * @return The risk associated with the particular email address. If
     * present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("email_address")
    public Double getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return The general risk associated with the email domain. If present,
     * this is a value in the range 0.01 to 99.
     */
    @JsonProperty("email_domain")
    public Double getEmailDomain() {
        return emailDomain;
    }

    /**
     * @return The risk associated with the issuer ID number on the email
     * domain. If present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("email_tenure")
    public Double getEmailTenure() {
        return emailTenure;
    }

    /**
     * @return The risk associated with the issuer ID number on the IP address.
     * If present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("ip_tenure")
    public Double getIpTenure() {
        return ipTenure;
    }

    /**
     * @return The risk associated with the particular issuer ID number (IIN)
     * given the billing location and the history of usage of the IIN on your
     * account and shop ID. If present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("issuer_id_number")
    public Double getIssuerIdNumber() {
        return issuerIdNumber;
    }

    /**
     * @return The risk associated with the particular order amount for your
     * account and shop ID. If present, this is a value in the range 0.01 to 99.
     */
    @JsonProperty("order_amount")
    public Double getOrderAmount() {
        return orderAmount;
    }

    /**
     * @return The risk associated with the particular phone number. If present,
     * this is a value in the range 0.01 to 99.
     */
    @JsonProperty("phone_number")
    public Double getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return The risk associated with the distance between the shipping
     * address and the IP location for the given IP address. If present, this is
     * a value in the range 0.01 to 99.
     */
    @JsonProperty("shipping_address_distance_to_ip_location")
    public Double getShippingAddressDistanceToIpLocation() {
        return shippingAddressDistanceToIpLocation;
    }

    /**
     * @return The risk associated with the local time of day of the transaction
     * in the IP address location. If present, this is a value in the range 0.01
     * to 99.
     */
    @JsonProperty("time_of_day")
    public Double getTimeOfDay() {
        return timeOfDay;
    }
}
