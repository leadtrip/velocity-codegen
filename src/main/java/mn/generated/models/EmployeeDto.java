package mn.generated.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Introspected
public record EmployeeDto (

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    UUID id,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    UUID workspaceId,

    UUID accountId,
    String name,
    String description,

    ){}