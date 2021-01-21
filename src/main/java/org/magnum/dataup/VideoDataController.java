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

import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.http.HttpResponse;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus.VideoState;
import org.magnum.dataup.storage.VideoStorage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = VideoSvcApi.VIDEO_DATA_PATH)
@RequiredArgsConstructor
@Log
public class VideoDataController {

	private final VideoStorage videoStorage;
	private final VideoFileManager videoFileManager;

	@GetMapping
	@ResponseBody
	public ResponseEntity<?> getVideoData(@PathVariable(name = "id") Long id, HttpResponse response)
			throws IOException {
		Video video = videoStorage.getById(id);
		if (video == null) {
			return ResponseEntity.notFound().build();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		videoFileManager.copyVideoData(video, out);
		return ResponseEntity.ok(out.toByteArray());
	}

	@PostMapping(consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVideoData(
			@PathVariable(name = "id") Long id /* videoId */,
			@RequestParam("data") MultipartFile[] files) throws IOException {
		log.info("Num files:" + files.length);
		Video video = videoStorage.getById(id);
		if (video == null) {
			return ResponseEntity.notFound().build();
		}
		videoFileManager.saveVideoData(video, files[0].getInputStream());
		return ResponseEntity.ok(ImmutableMap.of("state", VideoState.READY));
	}

}
