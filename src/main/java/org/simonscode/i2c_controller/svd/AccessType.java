package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccessType {
    @JsonProperty("read-write")
    READ_WRITE,
    @JsonProperty("read-only")
    READ_ONLY,
    @JsonProperty("write-only")
    WRITE_ONLY,
}
