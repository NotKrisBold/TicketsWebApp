package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> { }