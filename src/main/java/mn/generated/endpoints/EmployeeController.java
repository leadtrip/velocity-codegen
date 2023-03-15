package ${endPointsPkg};

import mn.generated.models.EmployeeDto;
import mn.generated.services.EmployeeService;
import io.asterisms.security.authorization.rules.annotation.IpcAuthorization;
import io.asterisms.security.authorization.rules.annotation.RequiresMemberOfWorkspace;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;


@SecurityRequirement(name = "scheduler-user")
@Controller("/api/workspace/{workspaceId}/employees")
@ExecuteOn(TaskExecutors.IO)
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @IpcAuthorization(serviceUrls = "*", exclusive = false)
    @Get("/{employeeId}")
    public Mono<EmployeeDto> getEmployee(@PathVariable UUID workspaceId, @PathVariable UUID employeeId) {
        return employeeService.getEmployee(workspaceId, employeeId);
    }

    @IpcAuthorization(serviceUrls = "*", exclusive = false)
    @RequiresMemberOfWorkspace
    @Get
    public Flux<EmployeeDto> listEmployees(@PathVariable UUID workspaceId) {
        return employeeService.listByWorkspace(workspaceId);
    }

    @IpcAuthorization(serviceUrls = "*", exclusive = false)
    @RequiresMemberOfWorkspace
    @Post
    public Mono<MutableHttpResponse<EmployeeDto>> create(@PathVariable UUID workspaceId, @Body EmployeeDto employeeDto) {
        return Mono.from( employeeService.create(employeeDto, workspaceId) )
        .map( crDto -> HttpResponse.created( crDto )
        .headers(headers -> headers.location(location(workspaceId, crDto.id()))));
    }

    @IpcAuthorization(serviceUrls = "*", exclusive = false)
    @RequiresMemberOfWorkspace
    @Put("/{employeeId}")
    public Mono<EmployeeDto> update(@PathVariable UUID workspaceId, @PathVariable UUID employeeId, @Body EmployeeDto employeeDto) {
            return employeeService.update(workspaceId, employeeId, employeeDto);
    }

    private URI location(UUID workspaceId, UUID id) {
        return URI.create("/api/workspace/" + workspaceId + "/employees/" + id);
    }
}
