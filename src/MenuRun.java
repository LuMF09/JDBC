//import java.security.interfaces.RSAKey;     en faisant le test mdp warnings apparaissent ici
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;				et ici aussi !!
import java.util.Scanner;

public class MenuRun {
	
	public static void main(String args[]){
		
		EmployeModel employe = new EmployeModel();
		Scanner scan = new Scanner(System.in);
		boolean fin = false;
		while (!fin){
			
	System.out.println("Faites votre choix :");
	System.out.println("---------------------------------------");
	System.out.println("(1) Montrer tous les employés");
	System.out.println("(2) Insérer un employé");
	System.out.println("(3) Vérifier login/mot de passe d'un employé");
	System.out.println("(4) Quitter");
	System.out.println("---------------------------------------");
	System.out.println("");
	// entrée clavier entier
	String choixString = scan.nextLine();
	int choix = Integer.parseInt(choixString);
	switch (choix)
	{
	case 1 :
	System.out.println("1 - Les employées :");
	employe.montrer();
	break;
	case 2 :
	System.out.println("2 - Insérer un employé :");
	System.out.print("Nom : ");
	String nom = scan.nextLine();
	System.out.print("login : ");
	String login = scan.nextLine();
	System.out.print("mdp : ");
	String mdp = scan.nextLine();
	System.out.println("***");
	employe.inserer(nom,login,mdp);// Insérer l'employé dans la table employé
	break;
	case 3 :
		try{
	System.out.println("3 - Vérification :");
	System.out.println("Rentrez votre login et votre mot de passe :");// Demander à l'utilisateur de rentrer login et mot de passe
	login = scan.nextLine();
	mdp = scan.nextLine();
	
	int choix2=employe.verifier(login, mdp);
	// Vérifier si :
		// - le login existe
	if (choix2!=-1){
		System.out.println("Le login existe");
	}
	else System.out.println("Le login n'existe pas");
	
	// on recup le mot de passe pour le tester
		Connection _conn;
		//EXACTEMENT LA MEME METHODE QUE EMPLOYE MODEL... PAS DAUTRES SOLUTIONS????
		String nomUser = "root"; // Utilisateur de la BD
		String passwd = ""; // Password de l'utilisateur de la BD
		String url = "jdbc:mysql://localhost/"; // Serveur de la BD
		String nomBase = "estiajdbc"; // Nom de la BD sur laquelle nous allons acceder
		_conn = null;
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		_conn=DriverManager.getConnection(url+nomBase, nomUser, passwd);
		
		}
		catch (SQLException ex1)
		{
		System.out.println("J'ai détecté une erreur de type SQL: " + ex1.getMessage());
		}
		catch (Exception ex2)
		{
		System.out.println("J'ai détecté une erreur de type lang: " + ex2.getMessage());
		}
		
		String requete = new String ("SELECT mdp FROM employe WHERE login=?;");
		PreparedStatement stmt = _conn.prepareStatement(requete);

		stmt.setString(1, login);
		ResultSet rs = stmt.executeQuery();	
		String mdp2=rs.getString("mdp");
		
	// - le mot de passe est le bon mot de passe
	if(mdp.equals(mdp2)){
		System.out.println("Le mot de passe est correct");}
	else System.out.println("Le mot de passe est incorrect");
		}
		catch (SQLException ex3)
		{
		while (ex3 != null){
			
		System.out.println(ex3.getSQLState());
		System.out.println(ex3.getMessage());
		System.out.println(ex3.getErrorCode());
		ex3=ex3.getNextException();
			}
		}
	break;
	case 4 :
	fin = true;
	break;
}
}
		scan.close();	
}
}