package com.diro.ift2255.model;

/**
 * Classe representant un avis associe a un cours.
 * Elle contient les informations provenant des messages
 * publies sur une plateforme externe.
 */
public class Opinion {

    /**
     * Identifiant du message.
     */
    private String message_id;

    /**
     * Identifiant du serveur.
     */
    private String guild_id;

    /**
     * Identifiant du canal.
     */
    private String channel_id;

    /**
     * Nom du canal.
     */
    private String channel_name;

    /**
     * Identifiant de lauteur du message.
     */
    private String author_id;

    /**
     * Nom de lauteur du message.
     */
    private String author_name;

    /**
     * Date de creation du message.
     */
    private String created_at;

    /**
     * Contenu textuel de lavis.
     */
    public String text;

    /**
     * Code du cours associe a lavis.
     */
    public String course_code;

    /**
     * Nom du professeur associe au cours.
     */
    private String professor_name;

    /**
     * Construit un avis vide.
     */
    public Opinion() {}

    /**
     * Retourne lidentifiant du message.
     *
     * @return lidentifiant du message
     */
    public String getMessage_id() {
        return message_id;
    }

    /**
     * Retourne lidentifiant du serveur.
     *
     * @return lidentifiant du serveur
     */
    public String getGuild_id() {
        return guild_id;
    }

    /**
     * Retourne lidentifiant du canal.
     *
     * @return lidentifiant du canal
     */
    public String getChannel_id() {
        return channel_id;
    }

    /**
     * Retourne le nom du canal.
     *
     * @return le nom du canal
     */
    public String getChannel_name() {
        return channel_name;
    }

    /**
     * Retourne lidentifiant de lauteur.
     *
     * @return lidentifiant de lauteur
     */
    public String getAuthor_id() {
        return author_id;
    }

    /**
     * Retourne le nom de lauteur.
     *
     * @return le nom de lauteur
     */
    public String getAuthor_name() {
        return author_name;
    }

    /**
     * Retourne la date de creation du message.
     *
     * @return la date de creation
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Retourne le texte de lavis.
     *
     * @return le texte de lavis
     */
    public String getText() {
        return text;
    }

    /**
     * Retourne le code du cours associe.
     *
     * @return le code du cours
     */
    public String getCourse_code() {
        return course_code;
    }

    /**
     * Retourne le nom du professeur.
     *
     * @return le nom du professeur
     */
    public String getProfessor_name() {
        return professor_name;
    }
}
