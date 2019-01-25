package de.qaware.cloud.nativ.zwitscher.common.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZwitscherRequest {
    private String query;
    private int number;
}
