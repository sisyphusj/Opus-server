package com.opus.feature.pin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.pin.domain.PinListRequestVO;
import com.opus.feature.pin.domain.PinListResponseDTO;
import com.opus.feature.pin.domain.PinRequestDTO;
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

	private final PinMapper pinMapper;

	/**
	 * 핀 추가
	 */
	@Transactional
	public void addPin(PinRequestDTO pinRequestDTO) {
		pinMapper.insertPin(PinVO.of(pinRequestDTO));
	}

	/**
	 * 핀 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<PinListResponseDTO> getPinList(int offset, int amount, String keyword) {

		PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
			.offset(offset)
			.amount(amount)
			.keyword(keyword)
			.build();

		return PinListResponseDTO.of(pinMapper.selectPinsByKeyword(pinListRequestVO));
	}

	/**
	 * 내 핀 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<PinListResponseDTO> getMyPinList(int offset, int amount) {

		PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.offset(offset)
			.amount(amount)
			.build();

		return PinListResponseDTO.of(pinMapper.selectPinsByMemberId(pinListRequestVO));
	}

	/**
	 * 핀 조회
	 */
	@Transactional
	public PinResponseDTO getPinByPinId(int pinId) {

		// 해당 핀이 존재하지 않으면 BusinessException 발생
		return pinMapper.selectPinByPinId(pinId)
			.map(PinResponseDTO::of)
			.orElseThrow(() -> new BusinessException("해당 핀을 찾을 수 없습니다."));
	}

	/**
	 * 핀 개수 조회
	 */
	@Transactional(readOnly = true)
	public int countPins(String keyword) {
		return pinMapper.countPinsByKeyword(keyword);
	}

	/**
	 * 핀 삭제
	 */
	@Transactional
	public void removePin(int pinId) {

		// 해당 핀이 존재하지 않으면 BusinessException 발생
		Optional<PinVO> pinVO = pinMapper.selectPinByPinId(pinId);

		if (pinVO.isEmpty()) {
			throw new BusinessException("해당 핀을 찾을 수 없습니다.");
		}

		pinMapper.deletePin(pinId, SecurityUtil.getCurrentUserId());
	}
}

