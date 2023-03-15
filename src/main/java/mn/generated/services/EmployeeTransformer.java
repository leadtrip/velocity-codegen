package mn.generated.services;

import mn.generated.models.EmployeeDto;
import mn.generated.models.EmployeeEntity;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class EmployeeTransformer {

    public EmployeeDto from(EmployeeEntity entity) {
        return new EmployeeDto(
            entity.getId(),
            entity.getWorkspaceId());
    }

    public EmployeeEntity to(EmployeeDto dto, UUID id, UUID workspaceId) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(id);
        entity.setWorkspaceId(workspaceId);

        return entity;
    }
}
