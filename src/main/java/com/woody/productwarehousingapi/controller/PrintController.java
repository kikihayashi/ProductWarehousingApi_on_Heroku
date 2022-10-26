package com.woody.productwarehousingapi.controller;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletItem;
import com.woody.productwarehousingapi.dto.PalletItemWithNo;
import com.woody.productwarehousingapi.model.PrintResponse;
import com.woody.productwarehousingapi.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class PrintController {
    @Autowired
    private PrintService printService;

    @PostMapping("/barcode/print")
    public ResponseEntity<PrintResponse> printBarcode(@RequestBody @Valid BarcodeItem barcodeItem) {
        PrintResponse printResponse = new PrintResponse();

        Integer id = printService.createBarcode(barcodeItem);

        if (id > 0) {
            BarcodeItem barcodeItemNew = printService.getBarcodeById(id);
            printService.printBarcode(barcodeItem.getPrintIp(), barcodeItemNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItem.getQrcode());
        } else {
            printResponse.setMessage("失敗，條碼:" + barcodeItem.getQrcode() + "已被使用");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/barcode/reprint")
    public ResponseEntity<PrintResponse> reprintBarcode(@RequestBody @Valid BarcodeItem barcodeItem) {
        PrintResponse printResponse = new PrintResponse();

        boolean isExist = printService.checkIfBarcodeExist(barcodeItem.getQrcode());

        if (isExist) {
            printService.printBarcode(barcodeItem.getPrintIp(), barcodeItem);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItem.getQrcode());
        } else {
            printResponse.setMessage("失敗，條碼:" + barcodeItem.getQrcode() + "不存在！");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(printResponse);
    }

    @PostMapping("/pallet/print")
    public ResponseEntity<PrintResponse> printPallet(@RequestBody @Valid PalletItem palletItem) {
        PrintResponse printResponse = new PrintResponse();

        Integer id = printService.createPallet(palletItem);

        if (id > 0) {
            PalletItemWithNo palletItemWithNoNew = printService.getPalletById(id);
            printService.printPallet(palletItem.getMachineId(), palletItem.getPrintIp(), palletItemWithNoNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(palletItemWithNoNew.getPalletNo());
        } else {
            printResponse.setMessage("失敗，沒有符合條件的條碼可以綁定");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/reprint")
    public ResponseEntity<PrintResponse> reprintPallet(@RequestBody @Valid PalletItemWithNo palletItemWithNo) {
        PrintResponse printResponse = new PrintResponse();

        boolean isExist = printService.checkIfPalletExist(palletItemWithNo.getPalletNo());

        if (isExist) {
            printService.printPallet(palletItemWithNo.getMachineId(), palletItemWithNo.getPrintIp(), palletItemWithNo);
            printResponse.setMessage("成功");
            printResponse.setQrcode(palletItemWithNo.getPalletNo());
        } else {
            printResponse.setMessage("失敗，棧板號:" + palletItemWithNo.getPalletNo() + "不存在");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(printResponse);
    }

    @PostMapping("/pallet/invalid")
    public ResponseEntity<PrintResponse> invalidPallet(@RequestBody @Valid InvalidPalletRequest invalidPalletRequest) {
        PrintResponse printResponse = new PrintResponse();

        printService.invalidBarcode(invalidPalletRequest.getSerialQueryList());

        printResponse.setMessage("成功");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(printResponse);
    }
}
