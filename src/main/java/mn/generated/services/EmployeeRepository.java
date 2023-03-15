package mn.generated.services;

import mn.generated.models.EmployeeEntity;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface EmployeeRepository extends ReactorCrudRepository<EmployeeEntity, UUID> {

    Flux<EmployeeEntity> findAllByWorkspaceId(UUID workspaceId);

    Mono<EmployeeEntity> findByWorkspaceIdAndId(UUID workspaceId, UUID id);

}