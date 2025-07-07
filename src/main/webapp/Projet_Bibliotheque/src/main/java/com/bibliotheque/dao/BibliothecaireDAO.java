package com.bibliotheque.dao;

import com.bibliotheque.entity.Bibliothecaire;
import java.util.List;

public interface BibliothecaireDAO {
    List<Bibliothecaire> findAll();
    Bibliothecaire findById(Long id);
    void save(Bibliothecaire biblio);
    void delete(Long id);
    boolean emailExiste(String email, Long idExclu);
}