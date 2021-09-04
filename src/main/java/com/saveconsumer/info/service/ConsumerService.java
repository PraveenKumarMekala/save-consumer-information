package com.saveconsumer.info.service;

import com.saveconsumer.info.domain.FileType;
import com.saveconsumer.info.encyption.CryptoUtils;
import com.saveconsumer.info.encyption.EncryptAndDecrypt;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    FileReadWriteService fileService;

    public Long decryptAndWriteToRequestedFileFormat(byte[] consumerInfo, FileType fileType) {
        Long response = null;
        try {
            val decryptConsumer = EncryptAndDecrypt.decryptConsumer(consumerInfo,
                    CryptoUtils.getAESKey(256), CryptoUtils.getRandomNonce(12));
            if (fileType.equals(FileType.XML)) {
                fileService.writeDataToXmlFile(decryptConsumer);
            } else {
                fileService.writeDataToCsvFile(decryptConsumer);
            }
        } catch (Exception e) {
            // TODO: Add Logger

        }
        return response;

    }

    public byte[] getAllConsumers() {
        val consumers = fileService.readDataFromFile();
        return EncryptAndDecrypt.encryptedObjects(consumers);
    }
}
