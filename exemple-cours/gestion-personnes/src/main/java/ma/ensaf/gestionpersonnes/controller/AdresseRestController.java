package ma.ensaf.gestionpersonnes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ma.ensaf.gestionpersonnes.dao.AdresseRepository;
import ma.ensaf.gestionpersonnes.model.Adresse;

/**
 * Petit service complémentaire pour créer / consulter les adresses
 * (utile pour récupérer l'id d'une adresse à rattacher à une personne).
 */
@RestController
public class AdresseRestController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String XML = MediaType.APPLICATION_XML_VALUE;

    @Autowired
    private AdresseRepository adresseRepository;

    @GetMapping(value = "/adresses", produces = { JSON, XML })
    public List<Adresse> getAdresses() {
        return adresseRepository.findAll();
    }

    @PostMapping(value = "/adresses", consumes = { JSON, XML }, produces = { JSON, XML })
    public Adresse addAdresse(@RequestBody Adresse adresse) {
        return adresseRepository.save(adresse);
    }
}
