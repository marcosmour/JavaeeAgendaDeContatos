package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  MODULO DE CONECÇÃO *. */
	
	// PARAMETROS DE CONECÇÃO
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimerzone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "Mar78945618#";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// MÉTODO DE CONECÇÃO
	private Connection conectar() {
		Connection con = null; // esse objeto iniciara uma conecção com o banco de dados
		try { // podemos interpretar como: NA TENTATIVA DE CONECTAR COM O BANCO DE DADOS
			Class.forName(driver); // essa linha vai buscar no conteudo da string driver
			con = DriverManager.getConnection(url, user, password);// DriverManager - gerencia o banco de dados. getConnection obtem os parametros de conecção
			return con;
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
			
		}
	}
	
	// teste de conecção- serve para testarmos se o programa esta cominicando com o banco, caso NÃO, ele mostra onde esta o erro
	/*public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/
	
	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	// CRUD CREAT
	public void inserirContato(JavaBeans contato) {
		String creat = "insert into contatos (nome, fone, email) values (?,?,?)";
		try {
			
			Connection con = conectar(); // abrindo a conexao
			PreparedStatement pst = con.prepareStatement(creat);// preparando a query para execução no banco de dados
			
			// Substituindo os parametros (?) pelo conteúdo das variaveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.executeUpdate(); // esse comando exrcuta a query, ou seja, insere os dados no banco de dados
			con.close(); // esse comando serve para encerrar a conecção com o banco de dados
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Listar contato.
	 *
	 * @return the array list
	 */
	/* CRUD READ */
	public ArrayList<JavaBeans> listarContato(){ // array para armazenar o conteudo das informaçoes de forma dinamica
		ArrayList<JavaBeans> contatos = new ArrayList(); // Objeto para acessar a classe JavaBeans
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			
			// O laço abaixo será executado enquanto houver contatos,ou seja, ficara procurando os contatos da lista
			while(rs.next()) {
				// Abixo variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}		
	}
	
	/*  CRUD UPDATE  */
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// selecionar contatos
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) { // esse comando sera executado enquanto houver contatos a serem mostrado
				// Setando as variaveis JavaBeans
				contato.setIdcon(rs.getString(1)); // OBSERVAÇÂO IMPORTANTE: contato e objeto que consegue acessar os metodos publivos da classe JavaBeans
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// editar o contato
	public void alterarContato(JavaBeans contato) { // o que esta entre parentes, esta recebendo as informaçoes que estão no JavaBeans atraves do contato
		String create = "update contatos set nome=?, fone=?, email=? where idcon =?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	/* CRUD DELETE */
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	


}
