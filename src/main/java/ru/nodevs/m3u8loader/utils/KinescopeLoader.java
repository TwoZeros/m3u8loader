package ru.nodevs.m3u8loader.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.nodevs.m3u8loader.entity.LinkEntity;

import java.io.IOException;

public class KinescopeLoader {
    private final static String FOLDER_TO_LOAD = ".\\video\\";
    public static void load(LinkEntity entity) {

        try {
            Document doc = Jsoup.connect(entity.getUrl()).get();
            String cont = doc.select("script").last().data();
            JsonObject json = new Gson().fromJson(cont, JsonObject.class);
            String url = json.get("contentUrl").getAsString();
            String name = json.get("name").getAsString()+".mp4";
            String fullSavePath = FOLDER_TO_LOAD + name;
            url = url.replace("&amp;token=", "");

            String command = "cmd /c .\\ffmpeg\\ffmpeg.exe -i " + url + " -c copy " + fullSavePath;
            System.out.println(command);

            CommandRunner commandRunner = new CommandRunner();
            commandRunner.executeCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
