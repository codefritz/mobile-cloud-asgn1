package org.magnum.dataup.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoStorage {

  private final List<Video> videoList = new ArrayList<>();

  public void add(Video video) {
    videoList.add(video);
  }

  public void remove(Video video) {
    videoList.remove(video);
  }

  public List<Video> getVideoList() {
    return Collections.unmodifiableList(videoList);
  }

}
