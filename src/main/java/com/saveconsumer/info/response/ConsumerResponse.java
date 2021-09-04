package com.saveconsumer.info.response;

import com.saveconsumer.info.domain.Consumer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumerResponse {

    private List<Consumer> consumers;
}
