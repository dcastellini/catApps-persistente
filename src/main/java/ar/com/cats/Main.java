package ar.com.cats;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public final static String KEY = "e57cba9c-c1b7-4d03-998a-e81e50dd968d";

    public static void main(String[] args) throws IOException {

        CatService catService = new CatService();

        int opcionElegida = -1;
        String[] botonesDeMenu = {
                "1) Ver gatos","2) Ver favoritos" ,"3) Salir"
        };

        do{
            String opcion = (String) JOptionPane.showInputDialog(null,
                    "Gatos Java",
                    "Menu Principal",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    botonesDeMenu,
                    botonesDeMenu[0]);

            for(int i = 0 ; i < botonesDeMenu.length ; i ++){
                if(opcion.equals(botonesDeMenu[i])){
                    opcionElegida = i;
                }
            }

            switch(opcionElegida){
                case 0:
                    catService.verGatos();
                    break;
                case 1:
                    catService.listFavorite(KEY);
                default:
                    break;
            }


        }while(opcionElegida != 1);

    }
}
