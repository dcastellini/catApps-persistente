package ar.com.cats;

import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatService {

    public void verGatos() throws IOException{

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();

        String responseJsonSinCorchetes = responseJson.substring(1, responseJson.length()-1);

        Gson gson = new Gson();

        Cat cat = gson.fromJson(responseJsonSinCorchetes,Cat.class);

        Image image = null;

        try {
            URL url = new URL(cat.getUrl());
            image = ImageIO.read(url);

            ImageIcon catWall = new ImageIcon(image);

            if(catWall.getIconWidth() > 800 ){
                Image wall = catWall.getImage();
                Image newWallResized = wall.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                catWall = new ImageIcon(newWallResized);
            }

            String menu = "Opciones: \n" +
                    "1) Ver otra imagen \n" +
                    "2) Favorito \n" +
                    "3) Volver \n";

            String[] botonesDeMenu = {"Ver otra imagen", "Favorito", "Volver"};

            String id_gato = String.valueOf(cat.getId());

            String opcion = (String) JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE,catWall,botonesDeMenu,botonesDeMenu[0]);

            int opcionElegida = -1;

            for(int i = 0 ; i < botonesDeMenu.length ; i ++){
                if(opcion.equals(botonesDeMenu[i])){
                    opcionElegida = i;
                }
            }

            switch(opcionElegida){
                case 0:
                    verGatos();
                    break;
                case 1:
                    setFavoriteCat(cat);
                default:
                    break;
            }


        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void setFavoriteCat(Cat cat){


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\""+ cat.getId() +"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApiKey())
                    .build();
        try{
            Response response = client.newCall(request).execute();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void listFavorite(String apiKey){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("x-api-key", apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();

            String responseJson = response.body().string();

            Gson gson = new Gson();

            CatFav[] catFavArray = gson.fromJson(responseJson,CatFav[].class);

            if(catFavArray.length > 0){
                int min = 1;
                int max = catFavArray.length;
                int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
                int indice = aleatorio - 1;

                CatFav catFav = catFavArray[indice];

                try {
                    Image image = null;

                    URL url = new URL(catFav.getImagex().getUrl());

                    image = ImageIO.read(url);

                    ImageIcon catWall = new ImageIcon(image);

                    if(catWall.getIconWidth() > 800 ){
                        Image wall = catWall.getImage();
                        Image newWallResized = wall.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                        catWall = new ImageIcon(newWallResized);
                    }

                    String menu = "Opciones: \n" +
                            "1) Ver otra imagen \n" +
                            "2) Eliminar favorito \n" +
                            "3) Volver \n";

                    String[] botonesDeMenu = {"Ver otra imagen", "Eliminar favorito", "Volver"};

                    String id_gato = String.valueOf(catFav.getId());

                    String opcion = (String) JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE,catWall,botonesDeMenu,botonesDeMenu[0]);

                    int opcionElegida = -1;

                    for(int i = 0 ; i < botonesDeMenu.length ; i ++){
                        if(opcion.equals(botonesDeMenu[i])){
                            opcionElegida = i;
                        }
                    }

                    switch(opcionElegida){
                        case 0:
                            listFavorite(apiKey);
                            break;
                        case 1:
                            deleteFavorite(catFav, apiKey);
                        default:
                            break;
                    }


                } catch (IOException e){
                    System.out.println(e.getMessage());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFavorite(CatFav catFav, String apiKey){

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites/"+ catFav.getId())
                .method("DELETE", body)
                .addHeader("x-api-key", apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}
