package info.androidhive.cardview;


public class Album {
    private String Username,ItemID,Description,Mobile,Location;
    private String Price,Category;
    private int thumbnail=R.drawable.album1;

    public Album() {
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }


    public String getName() {
        return Username;
    }

    public void setName(String name) {
        this.Username = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Album(String ItemID,String Username,String Description,String Mobile,String Location,String Price,String Category){
        this.ItemID=ItemID;
        this.Username=Username;
        this.Description=Description;
        this.Mobile=Mobile;
        this.Location=Location;
        this.Price=Price;
        this.Category=Category;
    }
}
