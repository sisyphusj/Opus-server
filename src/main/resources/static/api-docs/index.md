# Opus-Server API Docs

Opus 서버 API 명세서

## Introduction

아래의 표는 API의 종류와 간단한 설명이 수록되어 있으며 <br>
각 API의 상세한 설명은 아래의 링크를 통해 확인할 수 있습니다.

## API Summary

| API 종류    | API 명                                                     | 설명                                 |
|-----------|-----------------------------------------------------------|------------------------------------|
| Member    | [member.register](member.md#회원가입)                         | 회원가입                               |
|           | [member.check_duplicated](member.md#중복체크)                 | 회원정보 중복체크                          |
|           | [member.profile](member.md#프로필_조회)                        | 나의 회원정보 조회                         |
|           | [member.check_password](member.md#비밀번호_확인)                | 프로필 수정 전 비밀번호 확인                   |
|           | [member.edit_profile](member.md#프로필_수정)                   | 나의 회원정보 수정                         |
|           | [member.remove_profile](member.md#회원탈퇴)                   | 회원탈퇴                               |
| Auth      | [auth.login](auth.md#로그인)                                 | 로그인                                |
|           | [auth.reissue_token](auth.md#토큰_재발급)                      | 엑세스 토큰 재발급                         |
|           | [auth.logout](auth.md#로그아웃)                               | 로그아웃                               |
| Image     | [image.generate_image](image.md#이미지_생성)                   | Karlo API에서 이미지 생성                 |
| Pin       | [pins.register](pins.md#핀_추가)                             | 핀(게시물) 추가                          |
|           | [pins.list](pins.md#핀_리스트)                                | 핀 리스트 불러오기                         |
|           | [pins.my_list](pins.md#내_핀_리스트)                           | 나의 핀 리스트 불러오기                      |
|           | [pins.search_pin](pins.md#핀_조회)                           | 핀 조회                               |
|           | [pins.total_count](pins.md#핀_개수)                          | 핀의 개수 조회                           |
|           | [pins.remove_pin](pins.md#핀_제거)                           | 핀 제거                               |
| Comment   | [comments.add_comment](comments.md#댓글_추가)                 | 댓글 추가                              |
|           | [comments.list](comments.md#댓글_리스트)                       | 특정 핀에 대한 댓글 전체 리스트 조회              |
|           | [comments.my_list](comments.md#내_댓글_리스트)                  | 내가 작성한 댓글 전체 리스트 조회                |
|           | [comments.edit_comment](comments.md#댓글_수정)                | 댓글 수정                              |
|           | [comments.remove_comment](comments.md#댓글_삭제)              | 댓글 삭제                              |
| Like      | [likes.add_pin_like](like.md#핀_좋아요_추가)                    | 핀에 좋아요 추가                          | 
|           | [likes.check_pin_like](like.md#핀_좋아요_체크)                  | 핀의 좋아요 여부 확인                       |
|           | [likes.count_pin_like](like.md#핀_좋아요_개수)                  | 핀의 좋아요 개수 조회                       |
|           | [likes.remove_pin_like](like.md#핀_좋아요_해제)                 | 핀의 좋아요 해제                          |
|           | [likes.add_comment_like](like.md#댓글_좋아요_추가)               | 댓글에 좋아요 추가                         |
|           | [likes.check_comment_like](like.md#댓글_좋아요_체크)             | 댓글에 좋아요 여부 확인                      |
|           | [likes.count_comment_like](like.md#댓글_좋아요_개수)             | 댓글에 좋아요 여부 개수 조회                   |
|           | [likes.remove_comment_like](like.md#댓글_좋아요_해제)            | 댓글에 좋아요 해제                         |
| Subscribe | [subscribe.pin_like](subscribe.md#핀_좋아요_구독)               | 특정 핀에 대한 구독 추가                     |
|           | [subscribe.broadcast_pin_like](subscribe.md#핀_좋아요_브로드캐스트) | 핀에 대한 좋아요 카운트를 구독한 클라이언트들에게 브로드캐스트 |
|           | [subscribe.unsubscribe_pin_like](subscribe.md#핀_좋아요_구독해제) | 특정 핀에 대한 구독 해제                     |
