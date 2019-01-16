package de.qaware.cloud.nativ.zwitscher.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SearchParameter {
    String value;
    int pageSize;
}
