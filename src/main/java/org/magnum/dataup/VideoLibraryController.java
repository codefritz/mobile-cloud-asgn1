/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.storage.VideoStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log
public class VideoLibraryController {

	private final VideoStorage videoStorage;

	@PostConstruct
	public void postContruct() {
		log.info("Good luck!");
		videoStorage.add(Video.create()
				.withTitle("Alone at home")
				.build());
	}

	@GetMapping
	public ResponseEntity<?> getVideoList() {
		List<Video> videoList = videoStorage.getVideoList();
		List<String> stringList = videoList.stream().map(video -> video.getTitle())
				.collect(Collectors.toList());
		return ResponseEntity.ok(stringList);
	}

}
