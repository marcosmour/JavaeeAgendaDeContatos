package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document; // para que essa importação seja comcluida e nessecario im portar a Biblioteca itextpdf.jar
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO; // classe importada do proprio projeto
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select","/update", "/delete","/report" })// essas são as infoamaçoes que estão no <form> 
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();

	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) // esse e o metodo principal
			throws ServletException, IOException {
		// response.getWriter().append("Served at:.append(request.getContextPath());// usada para testar a conecção com o servelt
		// TESTE DE CONEXAO
		// dao.testeConexao(); // testar a conexao apenas
		
		String action = request.getServletPath(); // essa variavel pega as informaçoes dentro "urlPatterns
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response); // requeste e response, são heranças
		} else if(action.equals("/insert")) {
			novoContato(request, response);
		} else if(action.equals("/select")) {
			listarContato(request, response);
		} else if(action.equals("/update")) {
			editarContato(request, response);
		}  else if(action.equals("/delete")) {
			removerContato(request, response);
		} else if(action.equals("/report")) {
			gerarRelatorio(request, response);
		}
		else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Listar de Contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp"); // comando envia as informaçoes para o documento jsp de nome agenda que// criamos.
		ArrayList<JavaBeans> lista = dao.listarContato(); // esse cria um objeto que ira receber os dados JavaBeans
		// encaminhando a lista ao documento agenda.jsp
		request.setAttribute("contato",lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		/*for(int i = 0; i < lista.size(); i++){ // teste de recebimento da lista
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}*/
	}
	
	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo Contatos
		protected void novoContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			/*// Teste de Recebimento dos dados do formulario
			System.out.println(request.getParameter("nome")); // esse comando pega os dados que estão no formulario
			System.out.println(request.getParameter("fone"));
			System.out.println(request.getParameter("email"));*/
			
			// Setando as variaveis JavaBeans
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));			
			dao.inserirContato(contato); // incovando o método inserirContato passando o objeto contato
			response.sendRedirect("main");
		}
		
		/**
		 * Listar contato.
		 *
		 * @param request the request
		 * @param response the response
		 * @throws ServletException the servlet exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		// EDITAR CONTATO
		protected void listarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// Recebimento do id do contato que sera editado
			String idcon = request.getParameter("idcon");
			contato.setIdcon(idcon); // esse comando seta a variavel JavaBeans
			//System.out.println(idcon);// usada apenas para teste
			dao.selecionarContato(contato);// Executando o metodo selecionarContato (DAO)
			// teste de recebimento
			/*System.out.println(contato.getIdcon());
			System.out.println(contato.getNome());
			System.out.println(contato.getFone());
			System.out.println(contato.getEmail());*/
			
			// Setar os atributos do formulario com o conteudo do JavaBeans
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());
			
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");// Encaminhando para o documento editar.jsp
			rd.forward(request, response);
		}
		
		/**
		 * Editar contato.
		 *
		 * @param request the request
		 * @param response the response
		 * @throws ServletException the servlet exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		protected void editarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			/* para faser o teste de recebimento podemos usar o que esta comentado no metodo novoContato*/
			
			// Setando as variaveis JavaBeans ou seja os dados que estão vindo do formulario serão armazenada temporariamente no JavaBeans
			contato.setIdcon(request.getParameter("idcon"));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			// executando o metodo alterarContato
			dao.alterarContato(contato);
			// redirecionando para o documento agenda.jsp (atualizando as alteraçoes)
			response.sendRedirect("main");
		}
		
		/**
		 * Remover contato.
		 *
		 * @param request the request
		 * @param response the response
		 * @throws ServletException the servlet exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		protected void removerContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String idcon = request.getParameter("idcon"); // recebimento do id do contato a ser excluido (validador.js)
			//System.out.println(idcon); // apenas para teste de recebimento
			contato.setIdcon(idcon); // setando a variavel idcon JavaBeans
			dao.deletarContato(contato); // executando o metodo deletarContato (DAO) passando o objeto contato
			dao.alterarContato(contato);
			response.sendRedirect("main"); // redirecionando para o documento agenda.jsp (atualizando as alteraçoes)
			
		}
		
		/**
		 * Gerar relatorio.
		 *
		 * @param request the request
		 * @param response the response
		 * @throws ServletException the servlet exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		// GERANDO RELATOTIO EM PDF
		protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Document documento = new Document();
			try {
				response.setContentType("aplication/pdf"); // esse e o tipo de conteudo que queremos criar
				response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf"); //esse comando configura no servelet - nome do documento
				PdfWriter.getInstance(documento, response.getOutputStream()); // esse comando gera o documento
				documento.open(); // esse comando abre o documento
				documento.add(new Paragraph("Lista de contatos")); // esse comando escreve dentro da pagina do pdf
				documento.add(new Paragraph(" ")); // esse quebra uma linha pagina do pdf
				PdfPTable tabela = new PdfPTable(3); // comando cria uma tabela com 3 colunas dentro da pagina do pdf
				PdfPCell col1 = new PdfPCell(new Paragraph("nome"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				ArrayList<JavaBeans> lista = dao.listarContato(); // populando a tabela com os contatos
				for (int i = 0; i < lista.size() ; i++) {
					tabela.addCell(lista.get(i).getNome());
					tabela.addCell(lista.get(i).getFone());
					tabela.addCell(lista.get(i).getEmail());
				}
				documento.add(tabela);
				documento.close();
			} catch (Exception e) {
				System.out.println(e);
				documento.close(); // finalizando a conexao
			}
		}
		
		

}
