# Gestion des Personnes — Spring Boot (JPA/MySQL + Thymeleaf + REST)

Exemple unique regroupant les parties du cours (Pr. Youness IDRISSI KHAMLICHI, ENSA Fès) :

| Partie du cours | Contenu | Où dans le projet |
|---|---|---|
| 2 – Spring BD MySQL | Entité JPA, `JpaRepository`, pagination, tri, méthodes personnalisées, `@Query` (HQL) | `model/Personne.java`, `dao/PersonneRepository.java`, `controller/PersonneController.java`, `application.properties` |
| 3 – Thymeleaf | `th:text`, `th:each`, `th:if/unless`, `@{}`, fragments, i18n | `templates/*.html`, `templates/fragments/layout.html`, `messages*.properties`, `configuration/MyConfig.java` |
| 4 – REST | `@RestController`, `@RequestBody`, JSON/XML (`produces`/`consumes`), PUT/DELETE, `@ManyToMany` Personne–Adresse, `@JsonIgnoreProperties` | `controller/PersonneRestController.java`, `AdresseRestController.java`, `model/Adresse.java` |

## Structure

```
gestion-personnes
├── pom.xml
└── src/main
    ├── java/ma/ensaf/gestionpersonnes
    │   ├── GestionPersonnesApplication.java
    │   ├── configuration/MyConfig.java
    │   ├── controller/  PersonneController, PersonneRestController, AdresseRestController
    │   ├── dao/         PersonneRepository, AdresseRepository
    │   └── model/       Personne, Adresse
    └── resources
        ├── application.properties
        ├── messages.properties / messages_en.properties
        ├── static/css/style.css
        └── templates/   addPerson, confirm, result, fragments/layout
```

## Lancer

1. Démarrer MySQL (XAMPP). La base `db_springdata` est créée automatiquement (`createDatabaseIfNotExist=true`), identifiants `root` / mot de passe vide (à adapter dans `application.properties`).
2. Lancer `GestionPersonnesApplication` (Spring Tools) ou `mvn spring-boot:run`.
3. Ouvrir http://localhost:8080/

## Application web (vues Thymeleaf)

| URL | Rôle |
|---|---|
| `/addPerson` (GET / POST) | formulaire d'ajout, puis page de confirmation |
| `/showAll` | toutes les personnes |
| `/showAllByPage/{i}/{j}` | page `i` (à partir de 0), `j` personnes par page, ex. `/showAllByPage/0/3` |
| `/showAllSorted` | tri par `nom` (croissant) |
| `/showAllSorted/{v}` | tri par `nom` ou `prenom` |
| `/findByNom?nom=...` | `findByNom` |
| `/findByNomAndPrenom?nom=...&prenom=...` | `findByNomAndPrenom` |
| `/chercherSelonLePrenom?prenom=...` | requête HQL `@Query` |
| `/search?nom=...` | exercice : `findByNomContainingIgnoreCase` |
| `?lang=fr` / `?lang=en` | changement de langue (i18n) |

## Service REST (tests avec Postman)

Ajouter les en-têtes `Accept` et `Content-Type` = `application/json` (ou `application/xml`).

| Méthode | URL | Corps |
|---|---|---|
| GET | `/personnes` | — |
| GET | `/personnes/{id}` | — |
| POST | `/personnes` | `{"nom":"Alami","prenom":"Rachid"}` |
| POST | `/personnes` (avec nouvelle adresse) | `{"nom":"Idrissi","prenom":"Salah","adresses":[{"rue":"Trik ain chkaf","ville":"Fès","codePostal":"30000"}]}` |
| POST | `/personnes` (adresse existante) | `{"nom":"Idrissi","prenom":"jamal","adresses":[{"id":1}]}` |
| PUT | `/personnes/{id}` | `{"nom":"Lahrari","prenom":"Mouad"}` |
| DELETE | `/personnes/{id}` | — |
| GET / POST | `/adresses` | `{"rue":"Atlas","ville":"Fès","codePostal":"30000"}` |

## Choix et remarques

- **Thymeleaf à la place de JSP** : les parties 2 et 3 proposent deux technologies de vue pour le même exemple ; on garde Thymeleaf (pas besoin de `tomcat-embed-jasper`, de JSTL ni du packaging WAR).
- **Pas de conflit d'URL** : l'interface web utilise `/addPerson`, `/showAll`, … ; l'API REST garde `/personnes` comme dans la partie 4.
- **Spring Boot 2.7 / Java 17** : les slides utilisent `javax.persistence` (Spring Boot 2.x). Avec Spring Boot 3, remplacer par `jakarta.persistence`.
- **`MySQL8Dialect`** : crée les tables en InnoDB, nécessaire pour les clés étrangères de la table de jointure `personne_adresses`.
- **Si erreur au démarrage après modification des entités** : supprimer les tables (ou la base) puis relancer.
- `Sort.by("nom")` trie par ordre **croissant** ; pour un ordre décroissant : `Sort.by("nom").descending()`.
- `spring.messages.fallback-to-system-locale=false` évite que la langue du système prenne le dessus sur `messages.properties`.
