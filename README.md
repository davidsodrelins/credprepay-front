Api para geração de cartão crédito, validação de dados dos cartões gerados e transação de compras. 

Pode testar a aplicação! 
Link aplicação: https://credprepay-vuejs.herokuapp.com/
link API: https://credprepay.herokuapp.com/

A api tem o swagger-ui para ver e testar as rotas:
link: https://credprepay.herokuapp.com/swagger-ui.html


Tecnologias usadas: 
->Frontend - VueJS
->Backend -JAVA + Spring Boot

Instalação da API.

Faça o clone do projeto para seu computador e execute. O maven se encarregará de baixar todas as dependências.
Banco de dados POSTGRE é gerado automaticamente pela aplicação.
O arquivo atributes.propreties contem o endereço da api, caso queira basta istalar o postgres e seu computador e colocar os dados do seu servidor local.
Requitos: Java 8

Instalação do Frontend:
Faça o clone do projeto para sua máquina e em seguida digite os seguintes comandos yarn:
yarn install (instala as dependencias presentes no packge.json)
yarn serve (executa a aplicação)
yarn build  (builda para produção)

Rotas: 

get:
/cartao - Retorna todos os cartões cadastrado
/cartao/{id} - Retorna um cartão a partir do id
/cartao/{numero} = Retorna um cartão a partir do numero informado


post:
/cartao - Gera um cartão pre pago

modelo de objeto para post:

{
  "titular" : "David Sodre Lins",
  "saldo" : 365.25
}

Retorno da API:

{
    "id": 0,
    "titular": "David Sodré Lins",
    "numero": "1991088570962504",
    "validade": "07/21",
    "senha": "9526",
    "saldo": 365.25,
    "cvv": "532"
}


Transações de Compra:

Rotas: 

get:
/transacao - Retorna todas as transações realizadas (não seguro na pratica!)
/transacao/{id} - Retorna uma transacao a partir do id


post:
/transacao - Executa uma trasação de pagamento e aceita ou rejeita a partir dos dados do cartão inserido.

modelo de objeto para post:

{
        "estabelecimento": "Sorveteria XYZ",
        "valorCompra": 100,
    	  "cvv": "646",
	      "validade": "07/21",
        "numeroCartao": "1991081173947644",
    	  "senha": "7685"
}

Modelo do objeto de retorno:

{
    "estabelecimento": "Sorveteria XYZ",
    "valorCompra": 100,
    "cvv": "646",
    "validade": "07/21",
    "numeroCartao": "1991081173947644",
    "senha": "7685",
    "autorizado": false,
    "status": "COD 01 - Código CVV inválido. # COD 03 - Saldo Insuficiente # COD 04 - Senha inválida"
 }
