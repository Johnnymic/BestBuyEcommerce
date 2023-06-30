package com.bestbuy.ecommerce.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract  class BaseEntity {

    @CreatedBy
  private LocalDateTime createdAt;

    @LastModifiedDate
  private LocalDateTime updateAt;
}
