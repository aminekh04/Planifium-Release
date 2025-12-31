# Planifium – Outil d’aide au choix de cours (IFT2255)

Planifium est un outil d’aide au choix de cours destiné aux étudiants du DIRO (Université de Montréal).
L’application repose sur une **API REST** jouant le rôle de façade à l’API officielle **Planifium** et
propose une **interface en ligne de commande (CLI)** permettant d’explorer les cours, horaires,
résultats académiques et avis étudiants.

Ce projet a été réalisé dans le cadre du cours **IFT2255 – Génie logiciel**, **Devoir 3 – Implémentation de la solution**.

---

## Fonctionnalités

### Recherche et consultation de cours
- Recherche par **sigle partiel** (ex. `IFT`)
- Recherche par **mots-clés** dans le titre ou la description
- Consultation des cours offerts pour un **trimestre donné** (H25, A24, E24)
- Consultation des cours offerts dans un **programme**
- Visualisation de l’**horaire d’un cours** (sections et types d’activités)

### Éligibilité et résultats académiques
- Vérification de l’**éligibilité** à un cours (prérequis + cycle)
- Consultation des **résultats académiques agrégés**
- Message d’erreur convivial si le cours demandé n’existe pas

### Avis étudiants (Discord)
- Consultation des **avis étudiants agrégés** pour un cours
- **Soumission d’avis** via un **bot Discord** (POST vers l’API)
- Le bot sert uniquement à la collecte et à la soumission des avis

### Comparaison et planification
- **Comparaison de deux cours** (difficulté, charge de travail, résultats)
- Création d’un **ensemble de cours** (max. 6 cours)
- Génération de l’**horaire résultant** pour un trimestre
- Détection des **conflits d’horaire** *(bonus si applicable)*

---

## Organisation du dépôt

Planifium-Release/
├── backend/ # API REST (Java / Javalin)
├── cli/ # Application CLI (Java)
├── rapport/ # Rapport du projet
└── README.md

markdown
Copier le code

Chaque composant (**backend** et **CLI**) est un **projet IntelliJ indépendant**.

---

## Technologies utilisées
- **Java 17**
- **Maven**
- **Javalin** (API REST)
- **JUnit 5** (tests unitaires)
- **Mockito** (optionnel)
- **Discord API** (collecte des avis)
- **Planifium API** (données officielles)

---

## Installation

### Prérequis
- Java **17**
- Maven **3.8+**
- IntelliJ IDEA (recommandé)

### Cloner le dépôt
```bash
git clone https://github.com/aminekh04/Planifium-Release.git


### Exécution
Backend (API REST)
Ouvrir backend/ dans IntelliJ

Exécuter la classe Main

L’API démarre sur http://localhost:7000

Frontend (CLI)
Ouvrir cli/ dans IntelliJ

Exécuter la classe Main

Suivre les instructions affichées dans le terminal

Bot Discord (avis étudiants)
Le bot Discord est utilisé uniquement pour collecter et soumettre des avis étudiants à l’API REST.

Le token Discord n’est pas inclus pour des raisons de sécurité

Les variables d’environnement doivent être définies localement (.env)

Deux modes possibles :

commande explicite

écoute d’un canal dédié


Lien d’invitation Discord

[(Lien d’invitation fourni pour la démonstration)](https://discord.gg/JRUGrmuQ)
