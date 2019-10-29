# WigAPP

project Where I Go - application on Android

URL DA API:

http://localhost:8080/WigWS/webresources/
* o termo localhost deve ser trocado pelo ip da maquina de execução caso deseja-se consumir a api em maquinas virtuais, emuladores ou outros dispositivos

URL DOS METODOS:
---- usuario

usuario/get/{login} - buscar usuario X
usuario/login/{login}/{senha} - validar login X
usuario/Listar - listar usuarios x
usuario/Excluir/{login} = excluir usuario - method not allowed - não funcionando X
usuario/Inserir - inserir usuario X
usuario/Alterar - alterar usuario X

---- cliente

cliente/Cadastrar - cadastro de cliente
cliente/Listar - listar clientes cadastrados
- alteração e exclusão de clientes serão feitos pelo caminho de usuario

---- avaliação

cliente/Avaliacao/Listar - listar avaliações
cliente/Avaliacao/Inserir - fazer avaliação
cliente/Avaliacao/Responder - responder avaliação - sera utilizado pelo cliente e pela emprssa // por motivo de preguiça do desenvolvedor
