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
            String printIp = barcodeItem.getPrintIp();
            printService.printBarcode(printIp, barcodeItemNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItemNew.getQrcode());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/barcode/reprint")
    public ResponseEntity<PrintResponse> reprintBarcode(@RequestBody @Valid BarcodeItem barcodeItem) {
        PrintResponse printResponse = new PrintResponse();

        BarcodeItem barcodeItemNew = printService.getBarcodeByQrcode(barcodeItem.getQrcode());

        if (barcodeItemNew != null) {
            String printIp = barcodeItem.getPrintIp();
            printService.printBarcode(printIp, barcodeItemNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(barcodeItemNew.getQrcode());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/print")
    public ResponseEntity<PrintResponse> printPallet(@RequestBody @Valid PalletItem palletItem) {
        PrintResponse printResponse = new PrintResponse();

        Integer id = printService.createPallet(palletItem);

        if (id > 0) {
            PalletItemWithNo palletItemWithNoNew = printService.getPalletById(id);
            //列印棧板標籤
            String printIp = palletItem.getPrintIp();
            String pdaId = palletItem.getMachineId();
            printService.printPallet(pdaId, printIp, palletItemWithNoNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(palletItemWithNoNew.getPalletNo());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/reprint")
    public ResponseEntity<PrintResponse> reprintPallet(@RequestBody @Valid PalletItemWithNo palletItemWithNo) {
        PrintResponse printResponse = new PrintResponse();

        PalletItemWithNo palletItemWithNoNew = printService.getPalletByNo(palletItemWithNo.getPalletNo());

        if (palletItemWithNoNew != null) {
            //列印棧板標籤
            String printIp = palletItemWithNoNew.getPrintIp();
            String pdaId = palletItemWithNoNew.getMachineId();
            printService.printPallet(pdaId, printIp, palletItemWithNoNew);
            printResponse.setMessage("成功");
            printResponse.setQrcode(palletItemWithNoNew.getPalletNo());
        } else {
            printResponse.setMessage("失敗");
        }
        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }

    @PostMapping("/pallet/invalid")
    public ResponseEntity<PrintResponse> invalidPallet(@RequestBody @Valid InvalidPalletRequest invalidPalletRequest) {

        printService.invalidBarcode(invalidPalletRequest.getSerialQueryList());


        for (InvalidPalletRequest.SerialQuery s : invalidPalletRequest.getSerialQueryList()) {
            System.out.println(s.getSerialId());
        }
        PrintResponse printResponse = new PrintResponse();
        printResponse.setMessage("成功");

        return ResponseEntity.status(HttpStatus.OK).body(printResponse);
    }
}
