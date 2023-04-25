package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.Superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<Superadmin, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE superadmin SET empresa = ?1 WHERE idsuperadmin = ?2")
    void modificarEmpresa(String empresa, Integer idsuperadmin);

}
