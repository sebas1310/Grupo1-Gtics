package com.example.proyectogticsgrupo1.Repository;

import com.example.proyectogticsgrupo1.Entity.UxUiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UxUiRepository extends JpaRepository<UxUiEntity, Integer> {
    UxUiEntity findByTipodeusuarioIdtipodeusuario(int idTipo);
}
