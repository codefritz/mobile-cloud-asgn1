package org.magnum.dataup;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoConfiguration {

  @Bean
  public VideoFileManager videoFileManager() {
    try {
      return VideoFileManager.get();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

}
