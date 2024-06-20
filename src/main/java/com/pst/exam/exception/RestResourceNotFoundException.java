package com.pst.exam.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class RestResourceNotFoundException extends Exception {
    private final String resourceName;
    @Singular
    private final Set<UUID> resourceIds;
}
