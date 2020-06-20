package com.pip.chatbot.integration.food;

import java.util.HashMap;
import java.util.Map;

public class CusineTranslate {
    private static Map<String, String> mapWithCusineNames()
    {
        Map<String, String> result = new HashMap<>();

        result.put("American","Kuchnia amerykańska");
        result.put("Arabian","Kuchnia arabska");
        result.put("Argentine","Kuchnia argentyńska");
        result.put("Armenian","Kuchnia armeńska");
        result.put("Asian","Kuchnia azjatycka");
        result.put("Bagels","Bajgle");
        result.put("Bakery","Piekarnia");
        result.put("Balkans","Kuchnia bałkańska");
        result.put("Beverages","Napoje");
        result.put("Burger","Burgery");
        result.put("Cafe","Kawa");
        result.put("Caribbean","Kuchnia karaibska");
        result.put("Chinese","Chińszczyzna");
        result.put("Coffee and Tea","Kawa i herbata");
        result.put("Crepes","Naleśniki");
        result.put("Czech","Kuchnia czeska");
        result.put("Desserts","Desery");
        result.put("Donuts","Pączki");
        result.put("Drinks Only","Napoje %");
        result.put("European","Kuchnia europejska");
        result.put("Fast Food","Fast Food");
        result.put("Finger Food","Przekąski");
        result.put("French","Kuchnia francuska");
        result.put("Georgian","Kuchnia gruzińska");
        result.put("German","Kuchnia niemiecka");
        result.put("Greek","Kuchnia grecka");
        result.put("Grill","Kuchnia z grila");
        result.put("Healthy Food","Zdrowe jedzenie");
        result.put("Hungarian","Kuchnia węgierska");
        result.put("Ice Cream","Lody");
        result.put("Indian","Kuchnia indyjska");
        result.put("International","Kuchnia międzynarodowa");
        result.put("Israeli","Kuchnia izraelska");
        result.put("Italian","Kuchnia włoska");
        result.put("Japanese","Kuchnia japońska");
        result.put("Jewish","Kuchnia żydowska");
        result.put("Kebab","Kebab");
        result.put("Korean","Kuchnia koreańska");
        result.put("Lebanese","Kuchnia libańska");
        result.put("Lithuanian","Kuchnia litewska");
        result.put("Mediterranean","Kuchnia śródziemnomorska");
        result.put("Mexican","Kuchnia meksykańska");
        result.put("Moroccan","Kuchnia marokańska");
        result.put("Nepalese","Kuchnia nepalska");
        result.put("Pizza","Pizza");
        result.put("Polish","Kuchnia polska");
        result.put("Portuguese","Kuchnia portugalska");
        result.put("Russian","Kuchnia rosyjska");
        result.put("Salad","Sałatki");
        result.put("Sandwich","Kanapki");
        result.put("Scandinavian","Kuchnia skandynawska");
        result.put("Seafood","Owoce morza");
        result.put("Spanish","Kuchnia hiszpańska");
        result.put("Steak","Steki");
        result.put("Sushi","Sushi");
        result.put("Tea","Herbata");
        result.put("Tex-Mex","Kuchnia teksańska");
        result.put("Thai","Kuchnia tajska");
        result.put("Turkish","Kuchnia turecka");
        result.put("Ukrainian","Kuchnia ukraińska");
        result.put("Vegetarian","Wegetariańska");
        result.put("Vietnamese","Kuchnia wietnamska");
        result.put("Welsh","Kuchnia walijska");
        result.put("African","Kuchnia afrykańska");
        result.put("Austrian","Kuchnia austriacka");
        result.put("Others","Inne");

        return result;
    }

    public static String translate(String name){
        if(mapWithCusineNames().containsKey(name))
            return mapWithCusineNames().get(name);
        return name;
    }
}
