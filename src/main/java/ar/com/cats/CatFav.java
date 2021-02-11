package ar.com.cats;

public class CatFav {

    private String id;
    private String image_id;
    private String apiKey;
    private Imagex image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Imagex getImagex() {
        return image;
    }

    public void setImage(Imagex imagex) {
        this.image = imagex;
    }
}
