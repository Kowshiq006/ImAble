package com.example.imable1;



//public class Upload1 {
//}
import com.google.firebase.database.Exclude;
public class Upload1{
    private String eemail;

    private String mName;
    private String mImageUrl;
    private String mKey;
    public Upload1() {
        //empty constructor needed
    }
    public Upload1(String mail, String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        eemail=mail;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getEemail() {
        return eemail;
    }

    public void setEemail(String eemail) {
        this.eemail = eemail;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}