package com.opus.pin.service;

import com.opus.common.ResponseCode;
import com.opus.pin.domain.PinListRequest;
import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinListRequestDTO;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PinService {

    @Value("${local.url}")
    private String localUrl;

    @Value("${local.url1}")
    private String localUrl1;

    private final PinMapper pinMapper;

    int a = (int) (Math.random() * 1000);

    public ResponseEntity<ResponseCode> savePin(PinDTO pinDTO, int memberId) {

        Pin pin = Pin.builder()
                .image_path(pinDTO.getImage_path())
                .tag(pinDTO.getTag())
                .mId(memberId)
                .build();


        try {
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
            int random = ThreadLocalRandom.current().nextInt(1, 1001);
            log.info(directoryPath + "image" + random + ".jpg");
            File destinationFile = new File(directoryPath + "image" + random + ".jpg");
            // 클라이언트 url 상대 경로
            String urlPath = localUrl1 + "/image" + random + ".jpg";


            if (!tempFile.renameTo(destinationFile)) {
                throw new IOException("Faild to move temporary file to destination file");
            }
            ;

            log.info("File name = {}", destinationFile.getName());

            // 컨트롤러의 image_path는 이미지 URL이고, 여기에서 로컬 주소로 변경함
            // 골저 완성 이후 해당 필드 수정 필요
            pin.setImage_path(urlPath);
            pinMapper.savePin(pin);

            return ResponseEntity.ok(ResponseCode.SUCCESS);

        } catch (MalformedURLException e) {
            log.info("URL is not valid", e);
            return ResponseEntity.badRequest().body(ResponseCode.INVALID_INPUT_VALUE);
        } catch (IOException e) {
            log.info("IO Exception", e);
            return ResponseEntity.badRequest().body(ResponseCode.IO_EXCEPTION);
        }
    }

    public List<Pin> pinList(PinListRequestDTO pinListRequestDTO) {

        PinListRequest pinListRequest = PinListRequest.builder()
                .offset(pinListRequestDTO.getOffset())
                .amount(pinListRequestDTO.getAmount())
                .build();

        return pinMapper.pinList(pinListRequest);
    }

    public List<Pin> pinListById(PinListRequestDTO pinListRequestDTO, int memberId) {

        PinListRequest pinListRequest = PinListRequest.builder()
                .offset(pinListRequestDTO.getOffset())
                .amount(pinListRequestDTO.getAmount())
                .mId(memberId)
                .build();

        return pinMapper.pinListById(pinListRequest);
    }

    public int getTotalCount() {
        return pinMapper.getTotalCount();
    }

    public void updatePin(PinDTO pinDTO, int memberId) {

        Pin pin = Pin.builder()
                .pId(pinDTO.getPId())
                .image_path(pinDTO.getImage_path())
                .tag(pinDTO.getTag())
                .mId(memberId)
                .build();

        pinMapper.updatePin(pin);
    }

    public void deletePin(int pid, int memberId) {
        pinMapper.deletePin(pid, memberId);
    }
}
