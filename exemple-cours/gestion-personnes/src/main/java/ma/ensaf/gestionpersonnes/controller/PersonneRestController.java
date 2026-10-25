package ma.ensaf.gestionpersonnes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ma.ensaf.gestionpersonnes.dao.AdresseRepository;
import ma.ensaf.gestionpersonnes.dao.PersonneRepository;
import ma.ensaf.gestionpersonnes.model.Adresse;
import ma.ensaf.gestionpersonnes.model.Personne;

/**
 * Partie 4 : service web REST (JSON ou XML). @RestController = @Controller + @ResponseBody sur chaque méthode.
 */
@RestController
public class PersonneRestController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String XML = MediaType.APPLICATION_XML_VALUE;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    // GET : récupération de toutes les personnes
    @GetMapping(value = "/personnes", produces = { JSON, XML })
    public List<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    // GET : récupération d'une personne
    @GetMapping(value = "/personnes/{id}", produces = { JSON, XML })
    public Personne getPersonne(@PathVariable("id") long id) {
        return personneRepository.findById(id).orElse(null);
    }

    // POST : ajout d'une personne (avec ses adresses éventuelles)
    @PostMapping(value = "/personnes", consumes = { JSON, XML }, produces = { JSON, XML })
    public Personne addPersonne(@RequestBody Personne personne) {
        System.out.println(personne);
        List<Adresse> adresses = personne.getAdresses();
        if (adresses != null) {
            for (int k = 0; k < adresses.size(); k++) {
                Adresse adresse = adresses.get(k);
                if (adresse.getId() != null) {
                    // adresse existante : seule la clé primaire compte, on la rattache
                    Long idAdresse = adresse.getId();
                    adresses.set(k, adresseRepository.findById(idAdresse).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse introuvable : " + idAdresse)));
                } else {
                    // nouvelle adresse : on la sauvegarde d'abord (évite "unsaved transient instance")
                    adresses.set(k, adresseRepository.save(adresse));
                }
            }
        }
        return personneRepository.saveAndFlush(personne);
    }

    // PUT : modification d'une personne (créée si elle n'existe pas)
    @PutMapping(value = "/personnes/{id}", consumes = { JSON, XML }, produces = { JSON, XML })
    public Personne modifierPersonne(@PathVariable("id") long id, @RequestBody Personne personne1) {
        return personneRepository.findById(id).map(personne -> {
            personne.setNom(personne1.getNom());
            personne.setPrenom(personne1.getPrenom());
            return personneRepository.save(personne);
        }).orElseGet(() -> {
            personne1.setNum(id);
            return personneRepository.save(personne1);
        });
    }

    // DELETE : suppression d'une personne
    @DeleteMapping("/personnes/{id}")
    public void deletePersonne(@PathVariable("id") long id) {
        if (!personneRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable : " + id);
        }
        personneRepository.deleteById(id);
    }
}
