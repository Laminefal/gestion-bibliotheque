package com.bibliotheque.service;

import com.bibliotheque.dao.BibliothecaireDAO;
import com.bibliotheque.dao.impl.BibliothecaireDAOImpl;
import com.bibliotheque.entity.Bibliothecaire;
import java.util.List;

public class BibliothecaireService {
    private BibliothecaireDAO dao = new BibliothecaireDAOImpl();

    public List<Bibliothecaire> findAll() { return dao.findAll(); }
    public Bibliothecaire findById(Long id) { return dao.findById(id); }
    public void save(Bibliothecaire b) { dao.save(b); }
    public void delete(Long id) { dao.delete(id); }

    public boolean emailExiste(String email, Long idExclu) {
        return dao.emailExiste(email, idExclu);
    }
}