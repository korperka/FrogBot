package com.frogbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserConfig {
    String id;

    final String PATH = "src/main/java/com/frogbot/userconfig.json";

    Gson gson = new GsonBuilder()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();
    public UserConfig(String uid)
    {
        id = uid;
    }

    public void init() throws Exception {
        FileReader fr1 = new FileReader(PATH);
        Scanner scan1 = new Scanner(fr1);
        if(gson.fromJson(scan1.nextLine(), Map.class).get(id) != null) return;
        FileReader fr = new FileReader(PATH);
        Scanner scan = new Scanner(fr);
        String content = scan.nextLine();
        scan.close();
        fr.close();
        Map<String, Object> users = gson.fromJson(content, Map.class);
        FileWriter fw = new FileWriter(PATH, false);
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "");
        userData.put("description", "Описания нет.");
        userData.put("age", 0L);
        userData.put("coins", 0L);
        userData.put("warns", 0L);
        userData.put("daily", 0L);
        users.put(id, userData);
        fw.write(gson.toJson(users));
        fw.close();
    }

    public void setValue(String key, Object value) throws Exception
    {
        init();
        FileReader fr = new FileReader(PATH);
        Scanner scan = new Scanner(fr);
        Map<String, Object> users = gson.fromJson(scan.nextLine(), Map.class);
        scan.close();
        fr.close();
        FileWriter fw = new FileWriter(PATH, false);
        Map<String, Object> userData = (Map<String, Object>) users.get(id);
        userData.put(key, value);
        users.put(id, userData);
        fw.write(gson.toJson(users));
        fw.close();
    }

    public Object getValue(String key) throws Exception
    {
        init();
        FileReader fr = new FileReader(PATH);
        Scanner scan = new Scanner(fr);
        Map<String, Object> users = gson.fromJson(scan.nextLine(), Map.class);
        scan.close();
        fr.close();
        Map<String, Object> userData = (Map<String, Object>) users.get(id);
        return userData.get(key);
    }
}
