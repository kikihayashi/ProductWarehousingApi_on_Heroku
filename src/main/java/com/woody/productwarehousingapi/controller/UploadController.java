package com.woody.productwarehousingapi.controller;

import com.woody.productwarehousingapi.dto.UploadRequest;
import com.woody.productwarehousingapi.model.UploadResponse;
import com.woody.productwarehousingapi.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    /**
     * 使用前，需再SecurityConfiguration中加上
     * EnableWebSecurity、EnableGlobalMethodSecurity(以下方法名稱 = true)這兩個註釋
     * 或在 Application上面加上EnableGlobalMethodSecurity(以下方法名稱 = true)註釋
     *
     * @Secured({"ROLE_manager","ROLE_worker"}) -> 執行方法前的權限驗證，有這兩個角色(任意一個)，才能使用
     * @PreAuthorize("hasAuthority('admin')") -> 執行方法前的權限驗證，有這個權限才能使用
     * @PostAuthorize("hasAuthority('admin')") -> 執行方法後再權限驗證，適合驗證帶有返回值的方法
     * @PreFilter(value="filterObject.id%2==0") -> 執行方法前對傳入資料進行過濾。例如：只取得id為偶數的資料
     * @PostFilter("filterObject.username=='admin'") -> 執行方法後對回傳資料進行過濾。例如：只回傳username=admin的資料
     * */

    @Autowired
    private UploadService uploadService;

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
