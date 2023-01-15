package ru.nodevs.m3u8loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.nodevs.m3u8loader.utils.CommandRunner;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("").get();
            String cont =  doc.select("script").last().data();
            JsonObject json = new Gson().fromJson(cont, JsonObject.class);
            String url = json.get("contentUrl").getAsString();
            System.out.println(url);
            url = url.replace("&amp;token=", "");
            String command = "cmd /c .\\ffmpeg\\ffmpeg.exe -i "+url +" -c copy potoki.mp4";
            System.out.println(command);
            CommandRunner commandRunner = new CommandRunner();
            commandRunner.executeCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
