package com.saveconsumer.info.controller;

import com.saveconsumer.info.domain.FileType;
import com.saveconsumer.info.response.ConsumerResponse;
import com.saveconsumer.info.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Consumer Application")
public class ServeConsumerController {

    private final ConsumerService service;

    public ServeConsumerController(ConsumerService service) {
        this.service = service;
    }


    @Transactional
    @PostMapping(path = "/save-consumer", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    @Operation(method = "POST", summary = "Stores the data",
    description = "API write data to either xml or csv format")
    public ResponseEntity<Long> store(@RequestBody byte[] consumer, @Parameter(
            description = "Name of file type",
            array = @ArraySchema(schema = @Schema(type = "string", allowableValues = {"CSV", "XML"}))
    ) @PathVariable("fileType") FileType fileType) {

        return status(HttpStatus.CREATED).body(service.decryptAndWriteToRequestedFileFormat(consumer, fileType));
    }

    @GetMapping("/fetch-consumers")
    @Operation(method = "GET", summary = "Used to retrieve all consumer details")
    public ResponseEntity<byte[]> consumers() {
        return ok(service.getAllConsumers());
    }
}
