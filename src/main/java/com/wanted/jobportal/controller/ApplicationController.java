package com.wanted.jobportal.controller;

import com.wanted.jobportal.dto.ApplicationDto;
import com.wanted.jobportal.dto.ResponseDto;
import com.wanted.jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apply")
public class ApplicationController {

  private final ApplicationService applicationService;

  @PostMapping
  public ResponseEntity<ResponseDto> apply(@RequestBody ApplicationDto applicationDto) {
    return ResponseEntity.ok().body(applicationService.apply(applicationDto));
  }

}
