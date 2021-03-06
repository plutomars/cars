package Model;

import java.io.Serializable;

public class MyImage implements Serializable {

    private String carid;
    private int img_no;
    private byte[] image;

    public MyImage(){}

    public MyImage(String carid,int img_no,byte[] image){
        this.carid=carid;
        this.img_no=img_no;
        this.image = image;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public int getImg_no() {
        return img_no;
    }

    public void setImg_no(int img_no) {
        this.img_no = img_no;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
