package com.woody.productwarehousingapi.controller;


import com.woody.productwarehousingapi.dto.UploadRequest;
import com.woody.productwarehousingapi.model.UploadResponse;
import com.woody.productwarehousingapi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Validated
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody @Valid UploadRequest uploadRequest) {

        UploadResponse.Result result = new UploadResponse.Result();
        result.setErrMessage("");
        result.setIfSucceed("True");
        result.setWorkId(String.valueOf(new Date().getTime()));
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setResult(result);

        return ResponseEntity.status(HttpStatus.OK).body(uploadResponse);
    }

    @PostMapping("/uploadDB")
    public ResponseEntity<?> uploadDatabase(@RequestParam("file") MultipartFile file) {
        uploadService.store(file);
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setSuccess("true");
        return ResponseEntity.status(HttpStatus.OK).body(uploadResponse);
    }
}
