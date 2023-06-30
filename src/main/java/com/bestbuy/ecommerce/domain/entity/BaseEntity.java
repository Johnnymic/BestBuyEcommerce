package com.bestbuy.ecommerce.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter


public  abstract class BaseEntity {

    @CreatedBy
  private LocalDateTime createdAt;

    @LastModifiedDate
  private LocalDateTime updateAt;
}
