package com.attractions.attractionsProject.controller;

import com.attractions.attractionsProject.dtos.LocalityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class LocalityControllerTest extends AbstractControllerTest{

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void shouldSuccessfullyMapLocalityDtoToLocalityEntity_SaveItToDb_And_ReturnSavedLocalityEntityInDtoFormat()
            throws Exception{

        String uri = "/localities";
        LocalityDto localityDto = new LocalityDto(
                "Москва",
                "Москва"
        );

        String inputJson = super.mapToJson(localityDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8")
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);

        String content = mvcResult.getResponse().getContentAsString();
        LocalityDto localityResponseDto = super.mapFromJson(content, LocalityDto.class);
        assertEquals(localityDto, localityResponseDto);
    }
}