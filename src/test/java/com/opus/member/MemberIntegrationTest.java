package com.opus.member;

import com.opus.BaseIntegrationTest;
import com.opus.member.domain.MemberDTO;
import com.opus.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberIntegrationTest extends BaseIntegrationTest {

    private final String BASE_URL = "/api/member";

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void 회원가입() throws Exception {
        // given
        MemberDTO memberDTO = MemberDTO.of("username", "password", "nickname", "email@gmail.com");

        // when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/register")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDTO)));


        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("member-register"));

    }

    @Test
    @DisplayName("회원가입 Validation 오류 테스트")
    void 회원가입_유효성_오류() throws Exception {
        MemberDTO invalidMemberDTO = MemberDTO.of("", "", "nickname", "invalid-email");

        ResultActions actions = mockMvc.perform(post(BASE_URL + "/register")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMemberDTO)));

        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이디 중복 체크 테스트")
    void 아이디_중복체크() throws Exception {
        // given
        String username = "existingUser";

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/check/username/{username}", username)
                .with(csrf().asHeader()));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("check-username-duplicate"));
    }

    @Test
    @DisplayName("닉네임 중복 체크 테스트")
    void 닉네임_중복체크() throws Exception {
        // given
        String nickname = "existingNickname";

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/check/nickname/{nickname}", nickname)
                .with(csrf().asHeader()));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("check-nickname-duplicate"));
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트")
    void 이메일중복체크() throws Exception {
        // given
        String email = "existingEmail@gmail.com";

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/check/email/{email}", email)
                .with(csrf().asHeader()));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("check-email-duplicate"));
    }

    @Test
    @DisplayName("프로필 조회 테스트")
    @WithMockUser(username = "23", roles = {"USER"})
    void 프로필조회() throws Exception {

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/profile")
                .with(csrf().asHeader()));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.nickname").value("test"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andDo(document("get-profile"));


    }

    @Test
    @DisplayName("프로필 수정 테스트")
    @WithMockUser(username = "23", roles = {"USER"})
    void 프로필수정() throws Exception {
        // given
        MemberDTO updatedProfile = MemberDTO.of("newUsername", "newPassword", "newNickname", "newEmail@gmail.com");

        // when
        ResultActions actions = mockMvc.perform(put(BASE_URL)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProfile)));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("edit-profile"));
    }

    @Test
    @DisplayName("프로필 수정 Validation 오류 테스트")
    @WithMockUser(username = "23", roles = {"USER"})
    void 프로필_수정_유효성_오류() throws Exception {
        MemberDTO invalidProfile = MemberDTO.of("", "", "nickname", "invalid-email");

        ResultActions actions = mockMvc.perform(put(BASE_URL)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProfile)));

        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    @WithMockUser(username = "23", roles = {"USER"})
    void 회원탈퇴() throws Exception {
        // when
        ResultActions actions = mockMvc.perform(delete(BASE_URL)
                .with(csrf().asHeader()));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("remove-profile"));
    }
}
