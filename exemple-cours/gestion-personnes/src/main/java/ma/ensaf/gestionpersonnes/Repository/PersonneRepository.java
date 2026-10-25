package ma.ensaf.gestionpersonnes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.ensaf.gestionpersonnes.model.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    // Méthodes personnalisées (requêtes dérivées du nom de la méthode, sans implémentation)
    List<Personne> findByNomAndPrenom(String nom, String prenom);

    List<Personne> findByNom(String nom);

    // Exercice : opérateurs Containing + IgnoreCase
    List<Personne> findByNomContainingIgnoreCase(String nom);

    // Requête HQL avec @Query
    @Query("select p from Personne p where p.prenom = ?1")
    List<Personne> chercherSelonLePrenom(String prenom);
}
