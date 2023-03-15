package mn.generated.services;

import mn.generated.models.EmployeeDto;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Singleton
public class EmployeeService {

    private final EmployeeTransformer employeeTransformer;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeTransformer employeeTransformer, EmployeeRepository employeeRepository) {
        this.employeeTransformer = employeeTransformer;
        this.employeeRepository = employeeRepository;
    }

    public Mono<EmployeeDto> getEmployee(UUID workspaceId, UUID employeeId) {
        return employeeRepository.findByWorkspaceIdAndId(workspaceId, employeeId)
            .map(employeeTransformer::from);
    }

    public Flux<EmployeeDto> listByWorkspace(UUID workspaceId) {
        return employeeRepository.findAllByWorkspaceId(workspaceId)
        .map(employeeTransformer::from);
    }

    public Mono<EmployeeDto> create(EmployeeDto employeeDto, UUID workspaceId) {
        return employeeRepository.save(employeeTransformer.to(employeeDto, null, workspaceId))
        .map(employeeTransformer::from);
    }

    public Mono<EmployeeDto> update(UUID workspaceId, UUID employeeId, EmployeeDto employeeDto) {
        return employeeRepository.update(employeeTransformer.to(employeeDto, employeeId, workspaceId))
            .map(employeeTransformer::from);
    }
}
