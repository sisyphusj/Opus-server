package com.opus.pin.service;

import com.opus.pin.controller.PinController;
import com.opus.pin.domain.Pin;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PinService {

    @Value("${local.url}")
    private String localUrl;

    private final PinMapper pinMapper;

    int a = (int) (Math.random() * 1000);

    @Transactional
    public ResponseEntity<String> savePin(Pin pin) {

        try{
            URL url = new URL(pin.getImage_path());
            InputStream inputStream = url.openStream();
            OutputStream outputStream = null;
            File tempFile = null;
            try {
                tempFile = File.createTempFile("tempfile", ".jpg");
                outputStream = new FileOutputStream(tempFile);

                byte[] buffer = new byte[2048];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush();
            } finally {
                // 리소스 해제
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        log.error("outputStream close error", e);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error("inputStream close error", e);
                    }
                }
            }

            String directoryPath = localUrl;
            int random =  ThreadLocalRandom.current().nextInt(1, 1001);
            File destinationFile = new File(directoryPath + File.separator + "image" + random +".jpg");


            if(!tempFile.renameTo(destinationFile)) {
                throw new IOException("Faild to move temporary file to destination file");
            };

            log.info("File name = {}", destinationFile.getName());

            // 컨트롤러의 image_path는 이미지 URL이고, 여기에서 로컬 주소로 변경함
            // 골저 완성 이후 해당 필드 수정 필요
            pin.setImage_path(destinationFile.getPath());
            pinMapper.savePin(pin);

            return ResponseEntity.status(HttpStatus.OK).body("File downloaded successfully");

        } catch (MalformedURLException e) {
            log.info("URL is not valid", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL is not valid");
        } catch (IOException e) {
            log.info("IO Exception", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IO Exception");
        }
    }

    @Transactional
    public Pin findById(int pid) {
        return pinMapper.findById(pid);
    }

    @Transactional
    public void updatePin(Pin pin) {
        pinMapper.updatePin(pin);
    }

    @Transactional
    public void deletePin(int pid) {
        pinMapper.deletePin(pid);
    }

}
