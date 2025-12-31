package com.diro.ift2255.model;

import java.util.List;

/**
 * Classe representant les informations detaillees dun cours.
 * Elle regroupe les donnees descriptives les prerequis
 * les disponibilites et les liens externes associes au cours.
 */
public class CourseDetails {

    /**
     * Identifiant du cours.
     */
    private String id;

    /**
     * Nom du cours.
     */
    private String name;

    /**
     * Nombre de credits associes au cours.
     */
    private int credits;

    /**
     * Description detaillee du contenu du cours.
     */
    private String description;

    /**
     * Liste des cours prealables requis.
     */
    private List<String> prerequisiteCourses;

    /**
     * Texte descriptif des exigences du cours.
     */
    private String requirementText;

    /**
     * Indique si le cours est offert au trimestre dautomne.
     */
    private boolean availableAutumn;

    /**
     * Indique si le cours est offert au trimestre dhiver.
     */
    private boolean availableWinter;

    /**
     * Indique si le cours est offert au trimestre dete.
     */
    private boolean availableSummer;

    /**
     * Indique si le cours est offert de jour.
     */
    private boolean availableDay;

    /**
     * Indique si le cours est offert de soir.
     */
    private boolean availableNight;

    /**
     * Lien vers la page officielle du cours sur le site de l universite.
     */
    private String udemWebsite;

    /**
     * Construit un objet contenant les details complets dun cours.
     *
     * @param id identifiant du cours
     * @param name nom du cours
     * @param credits nombre de credits
     * @param description description du cours
     * @param prerequisiteCourses liste des cours prealables
     * @param requirementText texte des exigences
     * @param availableAutumn disponibilite a lautomne
     * @param availableWinter disponibilite en hiver
     * @param availableSummer disponibilite en ete
     * @param availableDay disponibilite de jour
     * @param availableNight disponibilite de soir
     * @param udemWebsite lien vers le site officiel du cours
     */
    public CourseDetails(String id,
                         String name,
                         int credits,
                         String description,
                         List<String> prerequisiteCourses,
                         String requirementText,
                         boolean availableAutumn,
                         boolean availableWinter,
                         boolean availableSummer,
                         boolean availableDay,
                         boolean availableNight,
                         String udemWebsite) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.prerequisiteCourses = prerequisiteCourses;
        this.requirementText = requirementText;
        this.availableAutumn = availableAutumn;
        this.availableWinter = availableWinter;
        this.availableSummer = availableSummer;
        this.availableDay = availableDay;
        this.availableNight = availableNight;
        this.udemWebsite = udemWebsite;
    }

    /**
     * Retourne l identifiant du cours.
     *
     * @return l identifiant du cours
     */
    public String getId() { return id; }

    /**
     * Retourne le nom du cours.
     *
     * @return le nom du cours
     */
    public String getName() { return name; }

    /**
     * Retourne le nombre de credits du cours.
     *
     * @return le nombre de credits
     */
    public int getCredits() { return credits; }

    /**
     * Retourne la description du cours.
     *
     * @return la description du cours
     */
    public String getDescription() { return description; }

    /**
     * Retourne la liste des cours prealables.
     *
     * @return la liste des cours prealables
     */
    public List<String> getPrerequisiteCourses() { return prerequisiteCourses; }

    /**
     * Retourne le texte des exigences du cours.
     *
     * @return le texte des exigences
     */
    public String getRequirementText() { return requirementText; }

    /**
     * Indique si le cours est offert au trimestre dautomne.
     *
     * @return vrai si offert a lautomne
     */
    public boolean isAvailableAutumn() { return availableAutumn; }

    /**
     * Indique si le cours est offert au trimestre dhiver.
     *
     * @return vrai si offert en hiver
     */
    public boolean isAvailableWinter() { return availableWinter; }

    /**
     * Indique si le cours est offert au trimestre dete.
     *
     * @return vrai si offert en ete
     */
    public boolean isAvailableSummer() { return availableSummer; }

    /**
     * Indique si le cours est offert de jour.
     *
     * @return vrai si offert de jour
     */
    public boolean isAvailableDay() { return availableDay; }

    /**
     * Indique si le cours est offert de soir.
     *
     * @return vrai si offert de soir
     */
    public boolean isAvailableNight() { return availableNight; }

    /**
     * Retourne le lien vers le site officiel du cours.
     *
     * @return le lien du site officiel
     */
    public String getUdemWebsite() { return udemWebsite; }

    /**
     * Retourne la liste des cours prealables.
     *
     * @return la liste des cours prealables
     */
    public List<String> getPrereqCourses() {
        return prerequisiteCourses;
    }

}
