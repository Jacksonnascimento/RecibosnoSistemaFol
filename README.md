# RecibosnoSistemaFol
Projeto para inserir os recibos do eSocial no sistema de folha de pagamento. Pega os recibos que estão nos arquivos XML do eSocial e os insere no sistema de folha de pagamento da Freire. 



Fiz esse pequeno programa para pegar os recibos dos arquivos do eSocial e inserir em um sistema de folha de pagamento. O programa pega os arquivos XML que estão em uma pasta e cria um script para inseri-los no sistema. O sistema possibilita duas formas de executar os scripts: 
1.	Criando um arquivo sql: o usuário seleciona a opção “Salvar arquivo.sql”, então um arquivo com o script é gerado, que poderá ser executado no banco de dados desejado.
2.	Escolher uma base para inserir os recibos: o usuário escolhe uma das bases apresentadas na lista e o sistema insere os recibos direto no banco de dados. 

<h3>Sobre as abas</h3> 

<p>Bases</P>
É possível escolher entre salvar o script em um arquivo e uma das bases em que os recibos serão inseridos. Após a lista de bases, é apresentado o campo do caminho dos arquivos XML. O caminho padrão é “pasta em que o programa está sendo executado/ArquivoXML”, mas pode ser modificado pelo usuário, como pode ser visto no vídeo. 
<>Adicionar bases</p>
Adicionar a base/banco do sistema em que os recibos serão adicionados.
<p>Configurações</p>
Selecionar os tipos de arquivos que serão obtidos os recibos. Também há campo para o caminho do arquivo.sql, onde será salvo o arquivo contendo os scripts. O caminho padrão é “pasta em que o programa está sendo executado/ArquivoRE”, mas o pode ser modificado. 
