package de.qaware.cloud.nativ.zwitscher.service.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SearchParameter {
    String value;
    int pageSize;
}
