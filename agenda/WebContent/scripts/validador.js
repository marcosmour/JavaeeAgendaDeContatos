/**
 * Validação de formulário
 * @author Marcos Moura
 */

function validar(){
	//alert('teste');// esse comando e para faser o teste de comunicação
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	if(nome === ""){
		alert('Preencha o campo Nome');
		frmContato.nome.focus()
		return false
	} else if(fone === ""){
		alert('Preencha o campo Fone');
		frmContato.fone.focus()
		return false
	} else{
		document.forms["frmContato"].submit()
	}
}