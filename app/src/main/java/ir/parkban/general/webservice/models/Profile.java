package ir.parkban.general.webservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class Profile {

    public Profile(LinkedTreeMap<String,String> input_object){

        this.setEmailAdress(input_object.get("EmailAdress"));
        this.setFullName(input_object.get("FullName"));
        this.setMelliCode(input_object.get("MelliCode"));
        this.setPrimaryAddress(input_object.get("PrimaryAddress"));
  //      this.setMobile(input_object.get("Mobile"));
        this.setUsername(input_object.get("username"));

    }
    public String getEmailAdress() {
        return EmailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        EmailAdress = emailAdress;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMelliCode() {
        return MelliCode;
    }

    public void setMelliCode(String melliCode) {
        MelliCode = melliCode;
    }

    public String getPrimaryAddress() {
        return PrimaryAddress;
    }

    public void setPrimaryAddress(String primaryAddress) {
        PrimaryAddress = primaryAddress;
    }

    @SerializedName("EmailAdress")
    @Expose
    private String EmailAdress;

    @SerializedName("FullName")
    @Expose
    private String FullName;

    @SerializedName("MelliCode")
    @Expose
    private String MelliCode;

    @SerializedName("PrimaryAddress")
    @Expose
    private String PrimaryAddress;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("@type")
    @Expose
    private String type;
}
