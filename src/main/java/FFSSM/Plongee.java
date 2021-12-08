/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Plongee {

	public Site lieu;

	public Moniteur chefDePalanquee;

	public LocalDate date;

	public int profondeur;

	public int duree;

	ArrayList<Licence> palanquees;


	public Plongee(Site lieu, Moniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
		this.palanquees = new ArrayList<>();
	}

	public Plongee(Moniteur chef){
		this.chefDePalanquee = chef;
		this.palanquees = new ArrayList<>();
	}


	public void ajouteParticipant(Plongeur participant) throws Exception {
		// DONE: Implémenter cette méthode
		palanquees.add(participant.derniereLicence());
	}


	/**
	 * Détermine si la plongée est conforme.
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme() throws Exception {
		// TODO: Implémenter cette méthode
		for (Licence l : palanquees){
			if (!l.estValide(this.date)){
				return false;
			}
		}
		return true;
	}

	public LocalDate getDate() {
		return date;
	}

	public ArrayList<Licence> getPalanquees() {
		return palanquees;
	}

}
