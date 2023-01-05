package com.woody.productwarehousingapi.controller;

import com.woody.productwarehousingapi.dto.UploadRequest;
import com.woody.productwarehousingapi.model.UploadResponse;
import com.woody.productwarehousingapi.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@Validated
@Api(tags = "上傳功能")
//@RequestMapping(path = "/root客製")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 使用PreAuthorize前，需再SecurityConfiguration中加上
     * EnableWebSecurity、EnableGlobalMethodSecurity(prePostEnabled = true)這兩個註釋
     * */
    @ApiOperation("上傳入庫資訊")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('admin') && hasRole('manager')")
    public ResponseEntity<?> upload(@RequestBody @Valid UploadRequest uploadRequest) {
        UploadResponse uploadResponse = new UploadResponse();
        UploadResponse.Result result = new UploadResponse.Result();

        String uploadedPallet = uploadService.getUploadedPallet(uploadRequest.getSerialNo().getAllSerialNoList());

        if (uploadedPallet.isEmpty()) {
            String warehouseNo = uploadService.storeProduct(uploadRequest);
            result.setErrMessage("");
            result.setIfSucceed("True");
            result.setWorkId(warehouseNo);
        } else {
            result.setErrMessage("錯誤，棧板號：" + uploadedPallet + "已上傳過！");
            result.setIfSucceed("False");
        }
        uploadResponse.setResult(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(uploadResponse);
    }

    @ApiOperation("上傳資料庫")
    @PostMapping("/uploadDB")
    @PreAuthorize("hasAuthority('admin') && hasRole('manager')")
    public ResponseEntity<?> uploadDatabase(@RequestParam("file") MultipartFile file) {
        uploadService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setSuccess("true");
        return ResponseEntity.status(HttpStatus.OK).body(uploadResponse);
    }
}
