
package com.example.gestionproduits.repository;

import com.example.gestionproduits.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {

    List<Vente> findAllByOrderByDateVenteDesc();
}
