package com.vocabtrain.maxim.linguee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Maximilian Kias on 28/07/2017.
 */

public class Translator {

    public static final int Bulgarian = 0;
    public static final int Czech = 1;
    public static final int Danish = 2;
    public static final int German = 3;
    public static final int Greek = 4;
    public static final int English = 5;
    public static final int Spanish = 6;
    public static final int Estonian = 7;
    public static final int Finnish = 8;
    public static final int French = 9;
    public static final int Hungarian = 10;
    public static final int Italian = 11;
    public static final int Lithuanian = 12;
    public static final int Latvian = 13;
    public static final int Maltese = 14;
    public static final int Dutch = 15;
    public static final int Polish = 16;
    public static final int Portuguese = 17;
    public static final int Romanian = 18;
    public static final int Slovak = 19;
    public static final int Slovene = 20;
    public static final int Swedish = 21;

    private static final String[] languages = {"bulgarisch","tschechisch", "dänisch", "deutsch", "griechisch", "englisch", "spanisch", "estnisch", "finnisch", "franzoesisch", "ungarisch",
            "italienisch", "litauisch", "lettisch", "maltesisch", "niederländisch", "polnisch", "portugiesisch", "rumänisch", "slowakisch", "slowenisch", "schwedisch"};

    private static final String[] languageCodes = {"BG", "CS", "DA", "DE", "EL", "EN", "ES", "ET", "FI", "FR", "HU", "IT", "LT", "LV", "MT", "NL", "PL", "PT", "RO", "SK", "SL", "SV"};

    /**
     Returns an Array containing all translations.
     @param sourceString    the word to translate
     @param sourceLanguage  the code of the language of the word (use Translate.MYLANGUAGE)
     @param resultLanguage  the code of the language to translate to (use Translate.MYLANGUAGE)
     @return                an String Array containing all traslations, length is 0 when there is no translation
     @throws                IOException
     */
    public static String[] getTranslations(String sourceString, int sourceLanguage, int resultLanguage) throws IOException {

        //url generation
        String url = "http://www.linguee.de/"+languages[sourceLanguage]+"-"+languages[resultLanguage]+"/search?source=auto&query="+sourceString;
        //get the html code of the url
        String htmlCode =getUrlSource(url);

        ArrayList<String> results = new ArrayList<>();

        //if there are examples fom extern sources cut them off
        if(htmlCode.contains("class='example_lines inexact'"))
            htmlCode  = htmlCode.substring(0,htmlCode.indexOf("class='example_lines inexact'"));

        //search all translations, modify them and cut off the parts of te html code dealt with
        while(htmlCode.contains("lid='"+languageCodes[resultLanguage]+":")){
            results.add(htmlCode.substring(htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":")+8,htmlCode.indexOf("'>",htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":"))));
            results.set(results.size()-1, results.get(results.size()-1).replaceAll("#"," "));
            results.set(results.size()-1,results.get(results.size()-1).replaceAll("[0-9]",""));
            htmlCode = htmlCode.substring(htmlCode.indexOf("'>",htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":"))+2,htmlCode.length());
        }

        //cast to an array and return it
        String[] resutltArray = new String[results.size()];
        results.toArray(resutltArray);
        return resutltArray;
    }

    /**
     Returns an Array containing all translations in this Form: TRANSLATION (WORD-TYPE)
     @param sourceString    the word to translate
     @param sourceLanguage  the code of the language of the word (use Translate.MYLANGUAGE)
     @param resultLanguage  the code of the language to translate to (use Translate.MYLANGUAGE)
     @return                an String Array containing all traslations, length is 0 when there is no translation
     @throws                IOException
     */
    public static String[] getTranslationsWithExtras(String sourceString, int sourceLanguage, int resultLanguage) throws IOException {

        //url generation
        String url = "http://www.linguee.de/"+languages[sourceLanguage]+"-"+languages[resultLanguage]+"/search?source=auto&query="+sourceString;
        //get the html code of the url
        String htmlCode =getUrlSource(url);

        ArrayList<String> results = new ArrayList<>();

        //if there are examples fom extern sources cut them off
        if(htmlCode.contains("class='example_lines inexact'"))
            htmlCode  = htmlCode.substring(0,htmlCode.indexOf("class='example_lines inexact'"));

        //copy for search after extras
        String searchResults = htmlCode;

        //search all translations, modify them and cut off the parts of te html code dealt with
        while(htmlCode.contains("lid='"+languageCodes[resultLanguage]+":")){
            results.add(htmlCode.substring(htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":")+8,htmlCode.indexOf("'>",htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":"))));
            results.set(results.size()-1, results.get(results.size()-1).replaceAll("#"," "));
            results.set(results.size()-1,results.get(results.size()-1).replaceAll("[0-9]",""));
            htmlCode = htmlCode.substring(htmlCode.indexOf("'>",htmlCode.indexOf("lid='"+languageCodes[resultLanguage]+":"))+2,htmlCode.length());
        }

        int resultCount = 0;
        while(searchResults.contains("class='tag_type' title='")) {

            String extra = searchResults.substring(searchResults.indexOf("class='tag_type' title='") + 24, searchResults.indexOf("'>", searchResults.indexOf("class='tag_type' title='")));
            extra = extra.replaceAll("&nbsp;", " ");

            results.set(resultCount,
                    results.get(resultCount)
                            +" ("
                            +  extra
                            +")");

            searchResults = searchResults.substring(searchResults.indexOf("'>", searchResults.indexOf("class='tag_type' title='")) + 2, searchResults.length());
            resultCount++;
        }

        //cast to an array and return it
        String[] resutltArray = new String[results.size()];
        results.toArray(resutltArray);
        return resutltArray;
    }

    /**
     Returns the url of the translation
     @param sourceString    the word to translate
     @param sourceLanguage  the code of the language of the word (use Translate.MYLANGUAGE)
     @param resultLanguage  the code of the language to translate to (use Translate.MYLANGUAGE)
     @return                an String Array containing all traslations, length is 0 when there is no translation
     */
    public static String getTranslationURL(String sourceString, int sourceLanguage, int resultLanguage){
        return "http://www.linguee.de/"+languages[sourceLanguage]+"-"+languages[resultLanguage]+"/search?source=auto&query="+sourceString;
    }


    /**
     Returns the html Code of the website as a String
     @param stringUrl   the url of the website
     @return            the html Code of the website as a String
     @throws                IOException
     */
    private static String getUrlSource(String stringUrl) throws IOException {
        //open connection
        java.net.URL url = new URL(stringUrl);
        URLConnection urlConnection = url.openConnection();


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "UTF-8"));

        String inputLine;
        StringBuilder a = new StringBuilder();

        //read every line and save it to the string builder
        while ((inputLine = bufferedReader.readLine()) != null)
            a.append(inputLine);
        bufferedReader.close();

        return a.toString();
    }
}
