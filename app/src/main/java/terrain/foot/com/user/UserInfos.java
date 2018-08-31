package terrain.foot.com.user;

/**
 * Created by user on 26/11/2017.
 */

public class UserInfos {
    private String _name,_lastname,Mobile,Localisation;
    private String _filephoto;
    public UserInfos() {}
    public UserInfos(String name, String lastname, String filepfoto, String Mobile, String localisation){

        this._name=name;
        this._lastname=lastname;
        this._filephoto=filepfoto;
        this.Mobile=Mobile;
        this.Localisation=localisation;

    }

    public String get_name ()
    {
        return(this._name);
    }
    public String get_lastname ()
    {
        return(this._lastname);
    }


    public String get_filephoto(){return (this._filephoto);}
public  String getMobile() {return  this.Mobile;}
    public void set_name(String name){
        this._name=name;
    }
    public void set_lastname(String lastname){
        this._lastname=lastname;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public  void set_filephoto(String filephoto){this._filephoto=filephoto;}


}
