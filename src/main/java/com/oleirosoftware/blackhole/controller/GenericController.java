package com.oleirosoftware.blackhole.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.oleirosoftware.blackhole.model.ErrorDesc;
import com.oleirosoftware.blackhole.model.ErrorObj;
import com.oleirosoftware.blackhole.model.SuccessDesc;
import com.oleirosoftware.blackhole.model.SuccessObj;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GenericController {

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestHeader("response-code") int response) {

        log.info("Header response-header informado: {}", response);

        if (!testeHttpCode(response))
            return new ResponseEntity<>(mountErrorObj(), HttpStatus.BAD_REQUEST);

        HttpStatus status = HttpStatus.valueOf(response);

        if (status.is2xxSuccessful()) {

            SuccessDesc successDesc = new SuccessDesc();
            successDesc.setMessage("Response Informado " + String.valueOf(response));

            SuccessObj successObj = new SuccessObj();
            successObj.setSuccessDesc(successDesc);

            return new ResponseEntity<>(successObj, status);
        }

        ErrorDesc error = new ErrorDesc();
        error.setMessage("Response Informado " + String.valueOf(response));

        ErrorObj errorObj = new ErrorObj();
        errorObj.setErrorDesc(error);

        return new ResponseEntity<>(errorObj, status);
    }

    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestHeader("response-code") int response, @RequestBody Object bodyObject) {

        if (!testeHttpCode(response))
            return new ResponseEntity<>(mountErrorObj(), HttpStatus.BAD_REQUEST);

        HttpStatus status = HttpStatus.valueOf(response);
        return new ResponseEntity<>(bodyObject, status);
    }

    @PutMapping("/put")
    public ResponseEntity<?> put(@RequestHeader("response-code") int response, @RequestBody Object bodyObject) {

        if (!testeHttpCode(response))
            return new ResponseEntity<>(mountErrorObj(), HttpStatus.BAD_REQUEST);

        HttpStatus status = HttpStatus.valueOf(response);
        return new ResponseEntity<>(bodyObject, status);
    }

    private static Boolean testeHttpCode(int response) {

        List<Integer> possiblesHttpCodes = Arrays.asList(HttpStatus.values())
                .stream().map(code -> code.value())
                .collect(Collectors.toList());

        return possiblesHttpCodes.contains(response);
    }

    private static ErrorObj mountErrorObj() {

        ErrorDesc error = new ErrorDesc();
        error.setMessage("O valor do responde é inválido");

        ErrorObj errorObj = new ErrorObj();
        errorObj.setErrorDesc(error);

        return errorObj;
    }
}
