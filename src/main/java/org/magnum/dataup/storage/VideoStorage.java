package org.magnum.dataup.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoStorage {

  private long nextVideoId = 1;
  private final List<Video> videoList = new ArrayList<>();

  public Video add(Video video) {
    video.setId(nextVideoId++);
    videoList.add(video);
    return video;
  }

  public Video getById(long id) {
    return videoList.stream()
        .filter(video -> video.getId() == id)
        .findFirst().orElse(null);
  }

  public void remove(Video video) {
    videoList.remove(video);
  }

  public List<Video> getVideoList() {
    return Collections.unmodifiableList(videoList);
  }

}
