import { http } from './config'

export default{

  listarCartoes:() => {
    return http.get('cartoes')
  },

  gerarCartao: (solicitacao) => {
    return http.post('cartao', solicitacao);
  },

  comprar: (pagamento) => {
    return http.post('compra', pagamento);
  }

}


