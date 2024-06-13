package com.br.entrada.saida.pessoas.configuration; // Declaração do pacote onde a classe WebConfig está localizada

import org.springframework.context.annotation.Bean; // Importação da anotação Bean do Spring
import org.springframework.context.annotation.Configuration; // Importação da anotação Configuration do Spring
import org.springframework.web.filter.HiddenHttpMethodFilter; // Importação da classe HiddenHttpMethodFilter do Spring

@Configuration // Marca a classe como uma classe de configuração do Spring
public class WebConfig {
    
    @Bean // Marca o método como um método de criação de bean do Spring
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter(); // Cria uma instância de HiddenHttpMethodFilter e a retorna
    }
}
/*
 * Contexto e Motivação
Manipulação de Métodos HTTP
Em aplicações web, especialmente em RESTful APIs, existem operações que não são facilmente mapeáveis com os métodos HTTP padrão (GET, POST, PUT, DELETE). Por exemplo, para atualizar um recurso, muitas vezes é necessário enviar uma solicitação POST ou PUT com um parâmetro indicando que a operação desejada é na verdade uma atualização (_method=PUT).

HiddenHttpMethodFilter
O HiddenHttpMethodFilter é uma classe do Spring Framework que permite simular requisições HTTP PUT, PATCH e DELETE usando apenas requisições POST. Essa funcionalidade é alcançada adicionando um campo oculto _method nos formulários HTML ou usando um parâmetro _method na URL.

Implementação como Bean
Ao definir o método hiddenHttpMethodFilter() como um bean no contexto do Spring, estamos configurando o framework para usar essa funcionalidade automaticamente em nossa aplicação. O Spring detecta a presença dessa bean e a aplica para interceptar requisições HTTP que usam essa técnica de simulação de métodos.

Vantagens
Simplicidade na Implementação: Permite implementar operações de atualização (PUT), parcial (PATCH) e exclusão (DELETE) usando apenas requisições POST.

Padrões RESTful: Facilita a adesão aos padrões RESTful sem a necessidade de configurações adicionais complexas.

Compatibilidade: Suportado nativamente pelo Spring MVC, garantindo compatibilidade com outras funcionalidades do framework.

Exemplo de Uso
Suponha que tenhamos um formulário HTML para atualizar um recurso. Em vez de enviar uma requisição PUT, podemos enviar uma requisição POST com o campo _method=PUT. O HiddenHttpMethodFilter intercepta essa requisição, reconhece o parâmetro _method e a converte internamente para uma requisição PUT antes de encaminhá-la ao controlador responsável.

html
Copiar código
<form action="/resource/1" method="POST">
    <input type="hidden" name="_method" value="PUT">
    <input type="text" name="name" value="Novo Nome">
    <button type="submit">Atualizar</button>
</form>
 */
