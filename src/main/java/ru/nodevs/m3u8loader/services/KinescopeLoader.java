package ru.nodevs.m3u8loader.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.nodevs.m3u8loader.entity.LinkEntity;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KinescopeLoader implements IVideoLoader {
    private final static String FOLDER_TO_LOAD = ".\\video\\";

    private final CommandRunner commandRunner;

    @Override
    public void load(LinkEntity entity) {

        try {
            Document doc = Jsoup.connect(entity.getUrl()).get();

            String url = getVideoLink(doc);
            String name = getFileName(doc);

            String command = "cmd /c .\\ffmpeg\\ffmpeg.exe -i " + url + " -c copy " + name ;
            System.out.println(command);
            commandRunner.executeCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getVideoLink(Document doc) {
        String script = doc.select("script").last().data();
        JsonObject json = new Gson().fromJson(script, JsonObject.class);
        String url = json.get("contentUrl").getAsString();
        url = url.replace("&amp;token=", "");
        return url;
    }

    @Override
    public String getFileName(Document doc) {
        String script = doc.select("script").last().data();
        JsonObject json = new Gson().fromJson(script, JsonObject.class);
        String name = json.get("name").getAsString().replace(" ","_") + ".mp4";
        System.out.println(FOLDER_TO_LOAD + name);
        return FOLDER_TO_LOAD + name;
    }


}

