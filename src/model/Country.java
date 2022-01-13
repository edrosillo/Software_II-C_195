package model;

import java.time.LocalDateTime;

public class Country {

    public int countryID;

    public String country;

    public LocalDateTime createDate;

    public LocalDateTime createTime;

    public Country(int countryID, String country, LocalDateTime createDate, LocalDateTime createTime) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createTime = createTime;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
