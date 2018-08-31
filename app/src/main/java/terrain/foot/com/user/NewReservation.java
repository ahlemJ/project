package terrain.foot.com.user;

public class NewReservation {

    private  String date;
    private  String heure;
    private  String dateDeDemande;
    private  String etat;
    private  String id;
    private  String idTerrain;
    private String profilephoto;


    public NewReservation() {}
    public NewReservation(String date, String heure, String etat, String dateDeDemande) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
    }
    public NewReservation(String date, String heure, String etat, String dateDeDemande, String profilephoto , String idTerrain) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
        this.idTerrain=idTerrain;
        this.profilephoto=profilephoto;
    }
    public NewReservation(String date, String heure, String etat, String dateDeDemande, String idTerrain) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
        this.idTerrain=idTerrain;
    }

    public String getDateDeDemande() {
        return dateDeDemande;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getEtat() {
        return etat;
    }

    public String getIdTerrain() { return idTerrain; }

    public  String getId() {return id;}
    public  void setId(String  id) {this.id=id;}

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }
}


