package app.example.phanmembanhoa.Model;

public class Category {
    private  String  id, name, imgUrl;


    public Category(){

    }

    public Category(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.imgUrl = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String image) {
        this.imgUrl = image;
    }
}
