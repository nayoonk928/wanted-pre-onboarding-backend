package com.wanted.jobportal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "jobPosting")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String position;

  @Column(nullable = false)
  private int reward;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String skill;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
  private Company company;

}
