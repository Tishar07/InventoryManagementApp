package model;

public class Subcategory {
    private int id;
    private int CatId;
    private String name;

    public Subcategory(int id,int CatId ,String name) {
        this.id = id;
        this.CatId= CatId;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCatId() { return id; }


}
