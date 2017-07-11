package com.example.pranay.sitbazar;

/**
 * Created by Pranay on 4/1/2017.
 */

public class purchase {

    private String name;
    private String author;
    private String image;

    public purchase(){

    }

    public purchase(String name,String author,String image)
    {
        this.name=name;
        this.author=author;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

       public void setAuthor(String author) {
        this.author = author;
    }
}
