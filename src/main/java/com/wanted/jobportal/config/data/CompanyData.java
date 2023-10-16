package com.wanted.jobportal.config.data;

import com.wanted.jobportal.domain.Company;
import com.wanted.jobportal.repository.CompanyRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyData {

  private final CompanyRepository companyRepository;

  @PostConstruct
  public void init() {
    Company company1 = Company.builder()
        .name("원티드")
        .country("한국")
        .region("서울")
        .build();

    Company company2 = Company.builder()
        .name("네이버")
        .country("한국")
        .region("판교")
        .build();

    Company company3 = Company.builder()
        .name("카카오")
        .country("한국")
        .region("판교")
        .build();

    companyRepository.saveAll(List.of(company1, company2, company3));
  }

}
