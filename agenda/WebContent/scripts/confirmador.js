/**
 * Confirmação de exclusão de um contato
 * @author Marcos Moura Brito
 * @param idcon
 */

function confirmar(idcon){
	let resposta = confirm("confirma a exclusão deste contato? ")
	if(resposta === true){
		//alert(idcon) // esse comando e apenas um teste de recebimento
		window.location.href = "delete?idcon=" + idcon
	}
}