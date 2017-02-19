package pratik.com.shauryatoday.dewasii_01.pojos.health;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by prati on 16-Feb-17.
 */

public class Hospital implements Parcelable{
    private String name, contactNo, address, email, otherInformation, facilities, website, image, dateAndTime;
    private String latitude, longitude;
    private long hospitalId;

    public Hospital(String name, String contactNo, String address, String email, String otherInformation, String facilities,
                    String website, String image, long hospitalId, String dateAndTime, String latitude, String longitude) {
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
        this.email = email;
        this.website = website;
        this.otherInformation = otherInformation;
        this.facilities = facilities;
        this.image = image;
        this.hospitalId = hospitalId;
        this.dateAndTime = dateAndTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Hospital() {
    }

    protected Hospital(Parcel in) {
        name = in.readString();
        contactNo = in.readString();
        address = in.readString();
        email = in.readString();
        otherInformation = in.readString();
        facilities = in.readString();
        website = in.readString();
        image = in.readString();
        dateAndTime = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        hospitalId = in.readLong();
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getWebsite() {
        return website;
    }

    public String getImage() {
        return image;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(contactNo);
        parcel.writeString(address);
        parcel.writeString(email);
        parcel.writeString(otherInformation);
        parcel.writeString(facilities);
        parcel.writeString(website);
        parcel.writeString(image);
        parcel.writeString(dateAndTime);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeLong(hospitalId);
    }
}
