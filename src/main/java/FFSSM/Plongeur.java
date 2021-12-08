package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;

public class Plongeur extends Personne {

    private int niveau;
    private ArrayList<Licence> licences;
    private GroupeSanguin groupeSanguin;

    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int niveau, ArrayList<Licence> licenses, GroupeSanguin groupeSanguin) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.niveau = niveau;
        this.licences = licenses;
        this.groupeSanguin = groupeSanguin;
    }


    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.licences = new ArrayList<>();
    }

    public void ajouteLicence( String numero, LocalDate delivrance, Club c){
        licences.add(new Licence(this, numero, delivrance, c));
    }

    public Licence derniereLicence() throws Exception {
        if (!licences.isEmpty()){
            return licences.get(licences.size()-1);
        }else{
            throw new Exception("La liste de licence est vide");
        }
    }
}
