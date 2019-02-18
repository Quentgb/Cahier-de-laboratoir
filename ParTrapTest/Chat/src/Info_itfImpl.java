
import java.rmi.*;

public  class Info_itfImpl implements Info_itf {

	private String nom;
	private String mdp;
 
	public Info_itfImpl(String s, String mdp) {
		nom = s ;
		this.mdp=mdp;
	}

	public String getName(){
		return nom;
	}

	public boolean passwordIsGood(String p){
		return p.equals(mdp);
	}
}

