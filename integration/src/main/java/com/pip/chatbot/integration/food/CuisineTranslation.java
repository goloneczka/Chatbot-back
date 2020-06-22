package com.pip.chatbot.integration.food;

import java.util.HashMap;
import java.util.Map;

public class CuisineTranslation {
    private static final Map<String, String> TRANSLATIONS = new HashMap<>();
    static {
        TRANSLATIONS.put("American","Kuchnia amerykańska");
        TRANSLATIONS.put("Arabian","Kuchnia arabska");
        TRANSLATIONS.put("Argentine","Kuchnia argentyńska");
        TRANSLATIONS.put("Armenian","Kuchnia armeńska");
        TRANSLATIONS.put("Asian","Kuchnia azjatycka");
        TRANSLATIONS.put("Bagels","Bajgle");
        TRANSLATIONS.put("Bakery","Piekarnia");
        TRANSLATIONS.put("Balkans","Kuchnia bałkańska");
        TRANSLATIONS.put("Beverages","Napoje");
        TRANSLATIONS.put("Burger","Burgery");
        TRANSLATIONS.put("Cafe","Kawa");
        TRANSLATIONS.put("Caribbean","Kuchnia karaibska");
        TRANSLATIONS.put("Chinese","Chińszczyzna");
        TRANSLATIONS.put("Coffee and Tea","Kawa i herbata");
        TRANSLATIONS.put("Crepes","Naleśniki");
        TRANSLATIONS.put("Czech","Kuchnia czeska");
        TRANSLATIONS.put("Desserts","Desery");
        TRANSLATIONS.put("Donuts","Pączki");
        TRANSLATIONS.put("Drinks Only","Napoje %");
        TRANSLATIONS.put("European","Kuchnia europejska");
        TRANSLATIONS.put("Fast Food","Fast Food");
        TRANSLATIONS.put("Finger Food","Przekąski");
        TRANSLATIONS.put("French","Kuchnia francuska");
        TRANSLATIONS.put("Georgian","Kuchnia gruzińska");
        TRANSLATIONS.put("German","Kuchnia niemiecka");
        TRANSLATIONS.put("Greek","Kuchnia grecka");
        TRANSLATIONS.put("Grill","Kuchnia z grila");
        TRANSLATIONS.put("Healthy Food","Zdrowe jedzenie");
        TRANSLATIONS.put("Hungarian","Kuchnia węgierska");
        TRANSLATIONS.put("Ice Cream","Lody");
        TRANSLATIONS.put("Indian","Kuchnia indyjska");
        TRANSLATIONS.put("International","Kuchnia międzynarodowa");
        TRANSLATIONS.put("Israeli","Kuchnia izraelska");
        TRANSLATIONS.put("Italian","Kuchnia włoska");
        TRANSLATIONS.put("Japanese","Kuchnia japońska");
        TRANSLATIONS.put("Jewish","Kuchnia żydowska");
        TRANSLATIONS.put("Kebab","Kebab");
        TRANSLATIONS.put("Korean","Kuchnia koreańska");
        TRANSLATIONS.put("Lebanese","Kuchnia libańska");
        TRANSLATIONS.put("Lithuanian","Kuchnia litewska");
        TRANSLATIONS.put("Mediterranean","Kuchnia śródziemnomorska");
        TRANSLATIONS.put("Mexican","Kuchnia meksykańska");
        TRANSLATIONS.put("Moroccan","Kuchnia marokańska");
        TRANSLATIONS.put("Nepalese","Kuchnia nepalska");
        TRANSLATIONS.put("Pizza","Pizza");
        TRANSLATIONS.put("Polish","Kuchnia polska");
        TRANSLATIONS.put("Portuguese","Kuchnia portugalska");
        TRANSLATIONS.put("Russian","Kuchnia rosyjska");
        TRANSLATIONS.put("Salad","Sałatki");
        TRANSLATIONS.put("Sandwich","Kanapki");
        TRANSLATIONS.put("Scandinavian","Kuchnia skandynawska");
        TRANSLATIONS.put("Seafood","Owoce morza");
        TRANSLATIONS.put("Spanish","Kuchnia hiszpańska");
        TRANSLATIONS.put("Steak","Steki");
        TRANSLATIONS.put("Sushi","Sushi");
        TRANSLATIONS.put("Tea","Herbata");
        TRANSLATIONS.put("Tex-Mex","Kuchnia teksańska");
        TRANSLATIONS.put("Thai","Kuchnia tajska");
        TRANSLATIONS.put("Turkish","Kuchnia turecka");
        TRANSLATIONS.put("Ukrainian","Kuchnia ukraińska");
        TRANSLATIONS.put("Vegetarian","Wegetariańska");
        TRANSLATIONS.put("Vietnamese","Kuchnia wietnamska");
        TRANSLATIONS.put("Welsh","Kuchnia walijska");
        TRANSLATIONS.put("African","Kuchnia afrykańska");
        TRANSLATIONS.put("Austrian","Kuchnia austriacka");
        TRANSLATIONS.put("Others","Inne");
    }


    public static String translate(String name){
        return TRANSLATIONS.getOrDefault(name, name);
    }
}
