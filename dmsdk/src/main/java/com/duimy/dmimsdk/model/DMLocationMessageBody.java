package com.duimy.dmimsdk.model;

import org.jivesoftware.smack.util.XmlStringBuilder;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by haley on 2017/7/17.
 */


@Table(name = "T_LocationBody")
public class DMLocationMessageBody extends DMMessageBody {

    @Column(name = "_latitude")
    private String latitude;
    @Column(name = "_longitude")
    private String longitude;
    @Column(name = "_address")
    private String address;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DMLocationMessageBody() {
        this.setBodyType(DMMessageBody.Body_Type_Location);
    }

    public DMLocationMessageBody(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.setBodyType(DMMessageBody.Body_Type_Location);
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder buf = new XmlStringBuilder();
        buf.openElement(getElementName());

        buf.element("latitude", String.valueOf(latitude));
        buf.element("longitude", String.valueOf(longitude));
        buf.element("address", address);

        buf.closeElement(getElementName());
        return buf;
    }

    @Override
    public String toString() {
        return "DMLocationMessageBody{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
