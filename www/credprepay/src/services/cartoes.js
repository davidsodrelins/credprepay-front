import { http } from './config'

export default{

  listarCartoes:() => {
    return http.get('cartoes')
  },

  gerarCartao: (solicitacao) => {
    return http.post('cartao', solicitacao);
  }

}


