package mn.generated.models;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity("employee")
public class EmployeeEntity {
    @Id
    @AutoPopulated
    private UUID id;

    @Column(nullable = false)
    private UUID workspaceId;

    @Column
    UUID accountId;
    @Column
    String name;
    @Column
    String description;
}