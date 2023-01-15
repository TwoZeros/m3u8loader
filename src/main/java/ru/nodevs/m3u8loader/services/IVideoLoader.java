package ru.nodevs.m3u8loader.services;

import org.jsoup.nodes.Document;
import ru.nodevs.m3u8loader.entity.LinkEntity;

public interface IVideoLoader {
    String getVideoLink(Document doc);
    String getFileName(Document doc);
    void load(LinkEntity link);
}
