// importation des biblioth�ques JDBC
import java.sql.*;


	public class EmployeModel{
		
		private Connection _conn;
	
	public EmployeModel(){
		
	String nomUser = "root"; // Utilisateur de la BD
	String passwd = ""; // Password de l'utilisateur de la BD
	String url = "jdbc:mysql://localhost/"; // Serveur de la BD
	String nomBase = "estiajdbc"; // Nom de la BD sur laquelle nous allons acceder
	_conn = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	_conn=DriverManager.getConnection(url+nomBase, nomUser, passwd);
	System.out.println("Je me connecte a la base de donnees: " + nomBase);
	}
	catch (SQLException ex1)
	{
	System.out.println("J'ai d�tect� une erreur de type SQL: " + ex1.getMessage());
	}
	catch (Exception ex2)
	{
	System.out.println("J'ai d�tect� une erreur de type lang: " + ex2.getMessage());
	}
	System.out.println("Base de donn�e connect�");
}
// fonction pour montrer

	public void montrer(){
	try
	{
	String requete = new String("SELECT id, nom, login FROM employe;");
	Statement stmt = _conn.createStatement();
	ResultSet rs = stmt.executeQuery(requete);
	while ( rs.next() )
	{
	String login = rs.getString("login");
	System.out.println(rs.getInt("id") +" "+ rs.getString("nom")+" "+ login);
	// � affiner pour montrer les trois champs -------------->DONE<-----------------------
	}
	}
	catch (SQLException ex3)
	{
	while (ex3 != null)
	{
	System.out.println(ex3.getSQLState());
	System.out.println(ex3.getMessage());
	System.out.println(ex3.getErrorCode());
	ex3=ex3.getNextException();
	}
	}
}
// fonction � remplir : v�rifier l'existance de l'utilisateur/mdp, retourner soit son id, soit -1.
	public int verifier(String login, String mdp){  //ici, 2 donn�es envoy�es par l'utilisateur ! Faille de s�curit�
		//il faut faire attention et cr�er des prepared statement (contrairement � la methode precedente)
	
		if((login != null && !login.isEmpty())&&(mdp != null && !mdp.isEmpty())){
			
			try {
			
			String requete = new String ("SELECT id FROM employe WHERE login=? AND mdp=?;");
			PreparedStatement stmt = _conn.prepareStatement(requete);
			
			// on indique les ? � remplir ! attention ici different de c l'indice commence � 1!
			stmt.setString(1, login);
			stmt.setString(2, mdp);
			
			//on execute
			ResultSet rs = stmt.executeQuery();			
			
			//on lit rs 
			if(rs.next()){
				int id = rs.getInt("id");
				return id;
			}
			
			return -1 ; 
				}
			
			catch (SQLException ex3)
			{
			while (ex3 != null){
				
			System.out.println(ex3.getSQLState());
			System.out.println(ex3.getMessage());
			System.out.println(ex3.getErrorCode());
			ex3=ex3.getNextException();
				}
			return -1;
			}
			
		}
		else return -1;
}
// fonction � remplir : ins�rer un nouvel enregistrements avec la date actuelle (utiliser NOW() en MySQL)
	public void inserer(String nom, String login, String mdp){
		try{
			String requete = "INSERT INTO employe(id, nom, login, mdp, date) VALUES (NULL, ?, ?, MD5(?), NOW() );";
			PreparedStatement stmt = _conn.prepareStatement(requete);
			stmt.setString (1, nom);
			stmt.setString (2, login);
			stmt.setString (3, mdp);
			// � noter : executeUpdate sans param�tres !
			@SuppressWarnings("unused")
			int nombre = stmt.executeUpdate();
		}
		catch (SQLException ex3)
		{
		while (ex3 != null)
		{
		System.out.println(ex3.getSQLState());
		System.out.println(ex3.getMessage());
		System.out.println(ex3.getErrorCode());
		ex3=ex3.getNextException();
		}
		}
}
	public void fermerConnexion(){
	try
	{
	if (_conn != null)
	_conn.close();
	}
	catch (SQLException ex1)
	{
	System.out.println("J'ai d�tect� une erreur de type SQL: " + ex1.getMessage());
	}
}

}