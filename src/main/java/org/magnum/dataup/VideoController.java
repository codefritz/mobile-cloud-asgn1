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

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.storage.VideoStorage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = VideoSvcApi.VIDEO_SVC_PATH)
@RequiredArgsConstructor
@Log
public class VideoController {

	private final VideoStorage videoStorage;

	@PostConstruct
	public void postConstruct() {
		log.info("Good luck!");
		videoStorage.add(Video.create()
				.withTitle("Alone at home")
				.build());
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVideoList() {
		return ResponseEntity.ok(videoStorage.getVideoList());
	}

	@PostMapping
	public ResponseEntity<?> addVideo(@RequestBody Video video, HttpServletRequest request) {
		Video addedVideo = videoStorage.add(video);
		addedVideo.setDataUrl(getURLBase(request) + VideoSvcApi.VIDEO_SVC_PATH + "/" + addedVideo.getId() + "/" + VideoSvcApi.DATA_PARAMETER);
		return ResponseEntity.ok(addedVideo);
	}

	public String getURLBase(HttpServletRequest request) {
		try {
			URL requestURL =  new URL(request.getRequestURL().toString());
			String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
			return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
