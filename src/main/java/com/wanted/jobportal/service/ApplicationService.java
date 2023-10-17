package com.wanted.jobportal.service;

import com.wanted.jobportal.dto.ApplicationDto;
import com.wanted.jobportal.dto.ResponseDto;

public interface ApplicationService {

  ResponseDto apply(ApplicationDto applicationDto);

}
