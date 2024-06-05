package com.opus.pin.service;

import com.opus.common.ResponseCode;
import com.opus.pin.domain.*;
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
public class PinService {

    @Value("${local.url}")
    private String localUrl;

    @Value("${local.url1}")
    private String localUrl1;

    private final PinMapper pinMapper;

    @Transactional
    public ResponseEntity<ResponseCode> savePin(PinDTO pinDTO, int memberId) {

        Pin pin = Pin.of(pinDTO, memberId);

        try {
            URL url = new URL(pin.getImagePath());
            try (InputStream inputStream = url.openStream()) {
                File tempFile = File.createTempFile("tempfile", ".jpg");
                try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                String directoryPath = localUrl;
                int random = ThreadLocalRandom.current().nextInt(1, 1001);

                String fileName = String.format("%simage%d.jpg", directoryPath, random);
                File destinationFile = new File(fileName);
                String urlPath = String.format("%s/image%d.jpg", localUrl1, random);

                if (!tempFile.renameTo(destinationFile)) {
                    throw new IOException("Failed to move temporary file to destination file");
                }

                log.info("File name = {}", destinationFile.getName());

                pin.setImagePath(urlPath);
                pinMapper.savePin(pin);

                return ResponseEntity.ok(ResponseCode.SUCCESS);
            }
        } catch (MalformedURLException e) {
            log.info("URL is not valid", e);
            return ResponseEntity.badRequest().body(ResponseCode.INVALID_INPUT_VALUE);
        } catch (IOException e) {
            log.info("IO Exception", e);
            return ResponseEntity.badRequest().body(ResponseCode.IO_EXCEPTION);
        }
    }

    @Transactional(readOnly = true)
    public List<PinVO> pinList(PinListRequestDTO pinListRequestDTO) {

        PinListRequest pinListRequest = PinListRequest.of(pinListRequestDTO);

        if (pinListRequest.getKeyword() == null || pinListRequest.getKeyword().trim().isEmpty()) {
            return pinMapper.pinList(pinListRequest);
        } else {
            return pinMapper.pinListByKeyword(pinListRequest);
        }
    }

    @Transactional(readOnly = true)
    public List<PinVO> pinListById(PinListRequestDTO pinListRequestDTO, int currentUserId) {

        PinListRequest pinListRequest = PinListRequest.of(pinListRequestDTO, currentUserId);
        return pinMapper.pinListById(pinListRequest);
    }

    @Transactional(readOnly = true)
    public int getTotalCount(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return pinMapper.getTotalCount();
        }

        return pinMapper.getTotalCountByKeyword(keyword);

    }

    public PinVO getPinByPId(int pid) {
        return pinMapper.getPinByPId(pid);
    }

    @Transactional
    public void deletePin(int pid, int memberId) {
        pinMapper.deletePin(pid, memberId);
    }

}
