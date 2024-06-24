package com.opus.feature.pin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.pin.domain.PinListReqVO;
import com.opus.feature.pin.domain.PinListResDTO;
import com.opus.feature.pin.domain.PinReqDTO;
import com.opus.feature.pin.domain.PinResDTO;
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
	public void addPin(PinReqDTO pinReqDTO) {
		pinMapper.insertPin(PinVO.of(pinReqDTO));
	}

	/**
	 * 핀 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<PinListResDTO> getPinList(int offset, int amount, String keyword) {

		PinListReqVO pinListReqVO = PinListReqVO.builder()
			.offset(offset)
			.amount(amount)
			.keyword(keyword)
			.build();

		return PinListResDTO.of(pinMapper.selectPinsByKeyword(pinListReqVO));
	}

	/**
	 * 내 핀 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<PinListResDTO> getMyPinList(int offset, int amount) {

		PinListReqVO pinListReqVO = PinListReqVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.offset(offset)
			.amount(amount)
			.build();

		return PinListResDTO.of(pinMapper.selectPinsByMemberId(pinListReqVO));
	}

	/**
	 * 핀 조회
	 */
	@Transactional
	public PinResDTO getPinByPinId(int pinId) {

		// 해당 핀이 존재하지 않으면 BusinessException 발생
		return pinMapper.selectPinByPinId(pinId)
			.map(PinResDTO::of)
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

