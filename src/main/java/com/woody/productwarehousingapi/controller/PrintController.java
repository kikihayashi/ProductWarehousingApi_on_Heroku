package com.woody.productwarehousingapi.controller;

import com.woody.productwarehousingapi.dto.BarcodeItem;
import com.woody.productwarehousingapi.dto.InvalidPalletRequest;
import com.woody.productwarehousingapi.dto.PalletRequest;
import com.woody.productwarehousingapi.dto.ReprintPalletRequest;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            printService.printBarcode(barcodeItemNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItemNew.getQrcode());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/print")
    public ResponseEntity<PrintResponse> printPallet(@RequestBody @Valid PalletRequest palletRequest) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        String[] time = dateFormat.format(date).split("-");
        String pallet = time[0]
                + (char) (65 + (Integer.valueOf(time[1]) + Integer.valueOf(time[2])) % 26)
                + (char) (65 + Math.round((Integer.valueOf(time[3]) + Integer.valueOf(time[4]) + Integer.valueOf(time[5])) * Math.random()) % 26)
                + (Integer.valueOf(time[3]) + Integer.valueOf(time[4]))
                + time[5];

        PrintResponse printResponse = new PrintResponse();
        printResponse.setMessage("成功");
        printResponse.setQrcode(pallet);

        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/invalid")
    public ResponseEntity<PrintResponse> invalidPallet(@RequestBody @Valid InvalidPalletRequest invalidPalletRequest) {
        for (InvalidPalletRequest.SerialQuery s : invalidPalletRequest.getSerialQueryList()) {
            System.out.println(s.getSerialId());
        }
        PrintResponse printResponse = new PrintResponse();
        printResponse.setMessage("成功");

        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/barcode/reprint")
    public ResponseEntity<PrintResponse> reprintBarcode(@RequestBody @Valid BarcodeItem barcodeItem) {
        PrintResponse printResponse = new PrintResponse();

        BarcodeItem barcodeItemNew = printService.getBarcodeByQrcode(barcodeItem.getQrcode());

        if (barcodeItemNew != null) {
            printService.printBarcode(barcodeItemNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItemNew.getQrcode());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/reprint")
    public ResponseEntity<PrintResponse> reprintPallet(@RequestBody @Valid ReprintPalletRequest reprintPalletRequest) {
        PrintResponse printResponse = new PrintResponse();
        printResponse.setMessage("成功");
        printResponse.setQrcode(reprintPalletRequest.getPalletId());
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }
}
