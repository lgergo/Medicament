package com.yevsp8.medicament;

public class Constants {

    static final String BaseUrlForOgyei="https://ogyei.gov.hu/gyogyszeradatbazis&action=show_details&item=";
    static final String AdapterType_ImageResult = "0";
    static final String AdapterType_SearchResult = "1";
    static final String Intent_SearchedText = "searchedText";
    public static final String Asset_MedicamentsFileName = "ogyei_gyogyszerek.json";
    public static final String Asset_SubstitutesFileName = "HelyettesithetosegiLista_mod_json.json";
    public static final String RootObject_Medicaments = "medicaments";
    public static final String RootObject_Substances = "substances";

    public static final String DatabaseName="app_db";

    //json files
    public static final String MedicamentsAttribute_Name="name";
    public static final String MedicamentsAttribute_OgyiKey="ogyi_key";
    public static final String SubstancesAttribute_Id="Id";
    public static final String SubstancesAttribute_Name="Name";
    public static final String SubstancesAttribute_Replaceable="Replaceable";
    public static final String Substances_InnerElementAttribute_Name="Name";
}
