package terrain.foot.com.user;

public class PublicationUser {
    private String   text;
    private String titre;
    private  String img, key;
    private String name, lastname, photoProfile;


    public PublicationUser() {}
    public void  publication (String img, String text , String titre, String key)
    {
        this.text=text;
        this.titre=titre;
        this.img=img;
        this.key=key;


    }
    public void  publicationUser (String img, String text , String titre, String key, String name , String lastname, String photoProfile)
    {
        this.text=text;
        this.titre=titre;
        this.img=img;
        this.key=key;
        this.name= name;
        this.lastname= lastname;
        this.photoProfile=photoProfile;

    }

    public String getImg(){return this.img;}
    public String getText(){return this.text;}
    public String getTitre(){return this.titre;}
    public void setTitre(String Titre){this.titre=Titre;}
    public String getKey(){return this.key;}
    public void setKey(String key){this.key=key;}
    public void setText(String Text){this.text=Text;}
    public void setImg(String Img){this.img=Img;}

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }
}
