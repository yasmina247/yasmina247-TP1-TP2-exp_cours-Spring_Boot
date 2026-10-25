package ma.ensaf.gestionpersonnes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ma.ensaf.gestionpersonnes.dao.PersonneRepository;
import ma.ensaf.gestionpersonnes.model.Personne;

/**
 * Partie 2 + 3 : contrôleur MVC qui retourne des vues Thymeleaf.
 */
@Controller
public class PersonneController {

    private static final List<String> CHAMPS_TRI = List.of("num", "nom", "prenom");

    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/showAll";
    }

    //Ajout
    @GetMapping(value = "/addPerson")
    public String addPerson() {
        return "addPerson";
    }

    @PostMapping(value = "/addPerson")
    public ModelAndView addPerson(@RequestParam(value = "nom") String nom,
                                  @RequestParam(value = "prenom") String prenom) {
        Personne personne = new Personne(nom, prenom);
        personneRepository.save(personne);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("confirm");
        mv.addObject("nom", nom);
        mv.addObject("prenom", prenom);
        return mv;
    }

    //Liste
    @GetMapping(value = "/showAll")
    public ModelAndView showAll() {
        List<Personne> personnes = personneRepository.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }

    //Liste par page : i = numéro de page (0 = première), j = taille de la page
    @GetMapping(value = "/showAllByPage/{i}/{j}")
    public ModelAndView showAllByPage(@PathVariable int i, @PathVariable int j) {
        int page = Math.max(i, 0);
        int taille = Math.max(j, 1);
        Page<Personne> personnes = personneRepository.findAll(PageRequest.of(page, taille));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes.getContent());
        // utilisés par result.html pour afficher les liens Précédent / Suivant
        mv.addObject("pageCourante", page);
        mv.addObject("taille", taille);
        mv.addObject("totalPages", personnes.getTotalPages());
        return mv;
    }

    //Liste triée (ordre croissant)
    @GetMapping(value = "/showAllSorted")
    public ModelAndView showAllSorted() {
        List<Personne> personnes = personneRepository.findAll(Sort.by("nom"));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }

    // Exercice : tri avec variable de chemin (/showAllSorted/nom ou /showAllSorted/prenom)
    @GetMapping(value = "/showAllSorted/{v}")
    public ModelAndView showAllSorted(@PathVariable String v) {
        String champ = CHAMPS_TRI.contains(v) ? v : "nom"; // évite une erreur si le champ n'existe pas
        List<Personne> personnes = personneRepository.findAll(Sort.by(champ));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }

    //Méthodes personnalisées du repository
    @GetMapping(value = "/findByNomAndPrenom")
    public ModelAndView findByNomAndPrenom(@RequestParam(value = "nom") String nom,
                                           @RequestParam(value = "prenom") String prenom) {
        List<Personne> personnes = personneRepository.findByNomAndPrenom(nom, prenom);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }

    @GetMapping(value = "/findByNom")
    public ModelAndView findByNom(@RequestParam(value = "nom") String nom) {
        List<Personne> personnes = personneRepository.findByNom(nom);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }
    @GetMapping(value = "/search")
    public ModelAndView search(@RequestParam(value = "nom") String nom) {
        List<Personne> personnes = personneRepository.findByNomContainingIgnoreCase(nom);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }

    // Requête HQL (@Query)
    @GetMapping(value = "/chercherSelonLePrenom")
    public ModelAndView chercherSelonLePrenom(@RequestParam(value = "prenom") String prenom) {
        List<Personne> personnes = personneRepository.chercherSelonLePrenom(prenom);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("personnes", personnes);
        return mv;
    }
}
