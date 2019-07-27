<template>
<div id="app">
     <div class="formNovoCartao">
      <form @submit.prevent="gerar" >
        <input type="text" placeholder="Nome Completo" id="nome" v-model="solicitacao.titular">
        <input type="number" step="0.010" placeholder="Informe o saldo" id="saldoInicial" v-model="solicitacao.saldo">
        <input type="submit" class="btn2" id="btnSolicita" value="Emitir Cartão">
      </form>
    </div>

  

<div class="cartaoForm"> 
        <div class="logo">
          <img src="./assets/credprepaylogo.png" height="50px" width="100%" alt="CredPrePay">
        </div>
        <div class="numCartao" id="container-cartao">
            <input type="text" class="input" id="numeroCartao"  placeholder="0000 0000 0000 0000" readonly="readonly" v-model="cartao.numero">
          </div>
    <div class="cartaoInfo">
      <div class="validade">
          <input id="mesValidadeCartao" type="text" class="validadeInput" placeholder="MM/YY" readonly="readonly" v-model="cartao.validade">
        </div>
      <div class="cvv">
        <input id="cvvCartao" type="text" class="cvvInput" placeholder="000" readonly="readonly" v-model="cartao.cvv">  
        <div class="cvvLogo">?
          <div class="logo">
            <img src="./assets/cvv.png" height="80px" width="80px" alt="CVV">
          </div>
        </div>
      </div>
    </div>
    
    <div class="titular">
        <input type="text" class="input" id="titularCartao" placeholder="NOME DO TITULAR" readonly="readonly" v-model="cartao.titular" >
    </div>  
</div>



<div class="senha">
      <input id="senha" v-show="cartao.senha" type="text" readonly="readonly" v-model="senha">
</div>




  
  <div id="mensagem" class="informacao"> </div>

  </div>

</template>

<script>

import Cartao from './services/cartoes'

export default {




  data(){
    return{
      solicitacao: {
        titular: '',
        saldo: ''
      },
      cartoes:[],
      cartao: {},
      senha: ''
    }
  },

  mounted() {
    Cartao.listarCartoes().then(resposta => {
      this.cartoes = resposta.data
    })
  },

  methods:{
    gerar(){
      Cartao.gerarCartao(this.solicitacao).then(resposta=> {

        this.solicitacao = {}
        this.cartao = resposta.data
        this.senha = 'SENHA: '+this.cartao.senha     
       })
    }
  }
}
</script>

<style>
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
  top: 20%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 610px;
  width: 100%;
  background: rgba(143, 194, 162, 0.5);
  padding: 2px;
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
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 380px;
  width: 100%;
  background: rgba(103, 25, 31, 100);
  padding: 30px;
  border-radius: 25px;

}

.senha{
  position: absolute;
  top: 60%;
  left: 40%;
  max-width: 50%;
  width: 20%;
  padding: 10px;
  border-radius: 15px;
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
