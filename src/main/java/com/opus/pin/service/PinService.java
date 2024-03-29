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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PinService {

    @Value("${local.url}")
    private String localUrl;

    private final PinMapper pinMapper;

    @Transactional
    public ResponseEntity<String> savePin(Pin pin) {

        log.info("env = {}", localUrl);

        // 로컬에 이미지를 저장하는 방식이지만 추후 서버에 이미지를 저장하는 방식으로 변경해야함
        try{
            URL url = new URL(pin.getImage_path());
            InputStream inputStream = url.openStream();
            File tempFile = File.createTempFile("tempfile", ".jpg");
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[2048];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

//            String directoryPath = "C:\\Users\\gpflv\\Desktop\\imageRepository\\";
            String directoryPath = localUrl;

            int random = (int)(Math.random() * 1000)+1;
            File destinationFile = new File(directoryPath + "image" + random +".jpg");
            tempFile.renameTo(destinationFile);

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
