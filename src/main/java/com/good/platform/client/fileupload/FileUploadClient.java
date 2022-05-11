package com.good.platform.client.fileupload;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.good.platform.client.project.config.FiegnConfiguration;
import com.good.platform.request.WorkdocsFileUploadRequest;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.response.dto.WorkdocsFileUploadResponse;

@FeignClient(name = "FILE-UPLOAD", url = "http://localhost:8088/file-upload", configuration = FiegnConfiguration.class)
public interface FileUploadClient {

	@PostMapping("v1/workdocs")
	public GoodPlatformResponseVO<WorkdocsFileUploadResponse> uploadFileToWorkDocsFromS3(
			@RequestBody WorkdocsFileUploadRequest workdocsFileUploadRequest);
	
	@GetMapping("v1/workdocs/filecontent/{documentData}")
	public ResponseEntity<Resource> getFileContentFromWorkDocs(@PathVariable String documentData);

}
