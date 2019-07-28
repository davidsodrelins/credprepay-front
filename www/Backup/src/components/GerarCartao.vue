<template>
<div id="app">
     <div class="formNovaCompra">
      <form @submit.prevent="comprar" >
        <input type="text" placeholder="Nome do Estabelecimento" id="nome_estabelecimento" v-model="pagamento.estabelecimento">
        <input type="number" step="0.010" placeholder="Valor da compra" id="valor_compra" v-model="pagamento.valorCompra">
        <input type="submit" class="btn2" id="btnSolicita" value="Emitir Cartão">
      </form>
    </div>

<div class="cartaoForm"> 
        <div class="logo">
          <img src="./assets/credprepaylogo.png" height="50px" width="100%" alt="CredPrePay">
        </div>
        <div class="numCartao" id="container-cartao">
            <input type="text" class="input" id="numeroCartao"  placeholder="0000 0000 0000 0000"  v-model="pagamento.cartaoNumero">
          </div>
    <div class="cartaoInfo">
      <div class="validade">
          <input id="mesValidadeCartao" type="text" class="validadeInput" placeholder="MM/YY"  v-model="pagamento.cartaoValidade">
        </div>
      <div class="cvv">
        <input id="cvvCartao" type="text" class="cvvInput" placeholder="000"  v-model="pagamento.cartaoCVV">  
        <div class="cvvLogo">?
          <div class="logo">
            <img src="./assets/cvv.png" height="80px" width="80px" alt="CVV">
          </div>
        </div>
      </div>
    </div>
    
    <div class="titular">
        <input type="text" class="input" id="titularCartao" placeholder="NOME DO TITULAR" >
    </div>  
</div>

<div class="informacao" v-show="retornoMessage">{{retornoMessage}}</div>

</div>

</template>

<script>

import Transacao from './services/cartoes'

export default {


  data(){
    return{
      pagamento: {
        cartaoNumero: '',
        cartaoCVV: '',
        cartaoValidade: '',
        valorCompra: 0,
        estabelecimento: '',
        cartaoSenha :''
      },
      compra: {},
      retornoMessage: ''
    }
  },

  methods:{
      
    comprar(){
      Transacao.realizarCompra(this.pagamento).then(resposta=> {
        this.compra = resposta.data
        this.retornoMessage = `${this.compra.status.split('#')}, 
        O saldo disponível para compras é de R$ ${this.compra.saldo}.`
       })
    }
  }

  
}
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
@import url('https://fonts.googleapis.com/css?family=Secular+One&display=swap');

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  outline: none;
  font-family: 'Secular One', sans-serif;
}

body{
  background: rgb(255, 255, 255); 
  background-size: cover;
  width: 100%;
  height: 100%;
}

.formNovoCartao{
  display: flex;
  position: absolute;
  top: 10%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 610px;
  width: 100%;
  background: rgba(201, 73, 63, 0.5);
  padding: 30px;
  border-radius: 10px;
}

.informacao{
  display: flex;
  position: absolute;
  top: 25%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 610px;
  width: 100%;
  background: rgba(143, 194, 162, 0.5);
  padding: 30px;
  border-radius: 10px;
}





.btn{
  display: inline;
  background-color:green;
  color: rgb(255, 255, 255);
  text-align: center;
  border-radius: 3px;
  padding: 10px;
  text-transform: uppercase;
  font-size: 16px;
  cursor: pointer;
}



#btnSolicita{
  display: inline;
  background-color:green;
  color: rgb(255, 255, 255);
  text-align: center;
  border-radius: 5px;
  padding: 6px;
  text-transform: uppercase;
  font-size: 18px;
  cursor: pointer;
  top: 20%;

}



.formNovoCartao input{
  position: relative;
  margin-right: 35px;
  padding: 30px;

}



.cartaoForm{
  position: absolute;
  top: 48%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 380px;
  width: 100%;
  background: rgba(103, 25, 31, 100);
  padding: 30px;
  border-radius: 25px;

}

.retornoMessage{
  position: absolute;
  top: 60%;
  left: 25%;
  max-width: 50%;
  background: red;
  width: 50%;
  padding: 50px;
  border-radius: 15px;
}

.retornoMessage input{
  top: 60%;
  left: 25%;
  width: 50%;

  border-radius: 3px;
  font-size: 16px;
  border: 0;
  text-align: center;
}


.numCartao{
  margin-bottom: 10px;
}

input[type="text"]{
  padding: 2px;
  border-radius: 3px;
  font-size: 16px;
  border: 0;
  text-align: center;

}

input[type="number"]{
  padding: 2px;
  border-radius: 3px;
  font-size: 16px;
  border: 0;
  text-align: center;
}

.numCartao .input{
  width: 100%;
  text-align: center;
  font-size: 25px;
  background-color: transparent;
  color: #fff;

}

.cartaoInfo{
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.validade  .validadeInput{
  width: 150px;
  margin-right: 1px;
  background-color: transparent;
  color: #fff;

}

.cvv .cvvInput{
  background-color: transparent;
  width: 100px;
  margin-right: 10px;
  color: #fff;

}

.validade,
.cvv{
  display: flex;
}

.cvv{
  justify-content: flex-end;
  align-items: center;
}

.cvvLogo{
  border: 2px solid #fff;
  width: 25px;
  height: 25px;
  color: rgb(253, 253, 253);
  font-size: 18px;
  font-weight: bold;
  border-radius: 50%;
  text-align: center;
  position: relative;
  cursor: pointer;
}
.cvvLogo .logo{
  position: absolute;
  width: 90px;
  height: 75px;
  padding:  10px 10px 5px;
  border-radius: 3px;
  top:-30px;
  right: 0;
  background: rgba(247, 243, 243, 0.2);
  display: none;
}

.cvvLogo .logo .img{
  width: 100px;
  height: 100px;
}

.cvvLogo:hover .logo{
  display: block;
}

.titular{
  width: 100%;
}

.titular .input{
  width: 100%;
  text-align: center;
  font-size: 16px;
  background-color: transparent;
  text-transform:uppercase;
  color: #fff;

}

::-webkit-input-placeholder{
  color: #757575;
}
</style>
