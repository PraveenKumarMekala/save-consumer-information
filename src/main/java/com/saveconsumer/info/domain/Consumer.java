package com.saveconsumer.info.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consumer implements Serializable {

    private Long id;

    private String name;

    private LocalDate dob;

    private Double salary;

    private Long age;

}
