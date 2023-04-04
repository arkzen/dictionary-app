
package studios.darkzen.dictionaryapp.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phonetic {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("license")
    @Expose
    private PhoneticLicense license;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public PhoneticLicense getLicense() {
        return license;
    }

    public void setLicense(PhoneticLicense license) {
        this.license = license;
    }

}