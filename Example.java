package com.vocabtrain.maxim.linguee;

import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by maxim on 28/07/2017.
 */

public class Example {

    /*  CONSOLE OUTPUT:

        http://www.linguee.de/deutsch-englisch/search?source=auto&query=Haus
        ----------------------------
        house
        building
        home
        domicile
        establishment
        dwelling
        household
        shell

        http://www.linguee.de/deutsch-englisch/search?source=auto&query=Haus
        ----------------------------
        house (Substantiv)
        building (Substantiv)
        home (Substantiv)
        domicile (Substantiv)
        establishment (Substantiv)
        dwelling (Substantiv)
        household (Substantiv)
        shell (Substantiv)
     */


    public static void main (String[] arg) {

        //catch the Exceptions
        try {

            //print the url
            System.out.println(Translator.getTranslationURL("Haus",Translator.German,Translator.English)+"\n----------------------------");

            //get the translations of the german word "Haus" to english
            String[] results = Translator.getTranslations("Haus",Translator.German,Translator.English);

            //print to the console
            for (String result:results) {
                System.out.println(result);
            }

            System.out.println("\n"+Translator.getTranslationURL("Haus",Translator.German,Translator.English)+"\n----------------------------");

            //get the translations with the wordtype of the german word "Haus" to english
            results = Translator.getTranslationsWithExtras("Haus",Translator.German,Translator.English);

            //print to the console
            for (String result:results) {
                System.out.println(result);
            }



            //no translations means the array has the length 0, it is empty
            results = Translator.getTranslationsWithExtras("csdcsfd",Translator.German,Translator.English);
            for (String result:results) {
                System.out.println(result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
