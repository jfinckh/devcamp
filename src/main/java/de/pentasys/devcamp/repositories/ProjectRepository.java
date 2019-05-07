package de.pentasys.devcamp.repositories;

import de.pentasys.devcamp.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
