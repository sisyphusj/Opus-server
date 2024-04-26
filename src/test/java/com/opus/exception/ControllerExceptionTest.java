package com.opus.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addMember_NullFields() throws Exception {
        String requestBody = "{\"m_id\": null, \"id\": null, \"pw\": null, \"nickname\": null, \"email\": null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }
}
