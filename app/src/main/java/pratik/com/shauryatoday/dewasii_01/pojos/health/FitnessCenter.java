package pratik.com.shauryatoday.dewasii_01.pojos.health;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by prati on 16-Feb-17.
 */

public class FitnessCenter implements Parcelable{
    private String name, contactNo, address, email, otherInformation, facilities, website, image, dateAndTime;
    private String latitude, longitude;
    private long fitnessCenterId;

    public FitnessCenter(String name, String contactNo, String address, String email, String otherInformation,
                         String facilities, String website, String image, String dateAndTime,
                         String latitude, String longitude, long fitnessCenterId) {
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
        this.email = email;
        this.otherInformation = otherInformation;
        this.facilities = facilities;
        this.website = website;
        this.image = image;
        this.dateAndTime = dateAndTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fitnessCenterId = fitnessCenterId;
    }

    public FitnessCenter() {
    }

    protected FitnessCenter(Parcel in) {
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
        fitnessCenterId = in.readLong();
    }

    public static final Creator<FitnessCenter> CREATOR = new Creator<FitnessCenter>() {
        @Override
        public FitnessCenter createFromParcel(Parcel in) {
            return new FitnessCenter(in);
        }

        @Override
        public FitnessCenter[] newArray(int size) {
            return new FitnessCenter[size];
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public long getFitnessCenterId() {
        return fitnessCenterId;
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
        parcel.writeLong(fitnessCenterId);
    }
}
