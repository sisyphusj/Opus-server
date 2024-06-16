package com.opus.feature.pin.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.image.service.S3Service;
import com.opus.feature.pin.domain.PinInsertDTO;
import com.opus.feature.pin.domain.PinListRequestVO;
import com.opus.feature.pin.domain.PinListResponseDTO;
import com.opus.feature.pin.domain.PinResponseDTO;
import com.opus.feature.pin.domain.PinVO;
import com.opus.feature.pin.mapper.PinMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PinService {

	private final S3Service s3Service;
	private final PinMapper pinMapper;

	@Transactional
	public void addPin(PinInsertDTO pinInsertDTO) {
		PinVO pinVO = PinVO.of(pinInsertDTO, SecurityUtil.getCurrentUserId());

		try {
			String imageUrl = pinVO.getImagePath();
			String s3Url = s3Service.uploadFileFromUrl(imageUrl);

			pinVO.updateImagePath(s3Url);
			pinMapper.insertPin(pinVO);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("error : ", e);
			throw new BusinessException("이미지 업로드 실패");
		}
	}

	@Transactional(readOnly = true)
	public List<PinListResponseDTO> getPinList(int offset, int amount, String keyword) {
		PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
			.offset(offset)
			.amount(amount)
			.keyword(keyword)
			.build();

		return PinListResponseDTO.of(pinMapper.selectPinsByKeyword(pinListRequestVO));
	}

	@Transactional(readOnly = true)
	public List<PinListResponseDTO> getMyPinList(int offset, int amount) {
		PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.offset(offset)
			.amount(amount)
			.build();

		return PinListResponseDTO.of(pinMapper.selectPinsByMemberId(pinListRequestVO));
	}

	@Transactional
	public PinResponseDTO getPinByPinId(int pinId) {
		return pinMapper.selectPinByPinId(pinId)
			.map(PinResponseDTO::of)
			.orElseThrow(() -> new NoSuchElementException("해당 핀을 찾을 수 없습니다."));
	}

	@Transactional(readOnly = true)
	public int countPins(String keyword) {
		return pinMapper.countPinsByKeyword(keyword);
	}

	@Transactional
	public void removePin(int pinId) {
		pinMapper.selectPinByPinId(pinId)
			.orElseThrow(() -> new NoSuchElementException("해당 핀을 찾을 수 없습니다."));

		pinMapper.deletePin(pinId, SecurityUtil.getCurrentUserId());
	}
}

