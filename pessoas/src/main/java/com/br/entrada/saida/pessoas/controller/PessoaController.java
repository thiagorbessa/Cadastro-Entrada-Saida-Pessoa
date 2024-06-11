package com.br.entrada.saida.pessoas.controller; // Define o pacote onde esta classe está localizada

import java.time.LocalDateTime; // Importa a classe LocalDateTime para manipulação de datas e horas
import java.time.format.DateTimeParseException; // Importa a classe DateTimeParseException para tratar exceções de parsing de datas
import java.util.UUID; // Importa a classe UUID para geração de IDs únicos
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação Autowired para injeção de dependência
import org.springframework.stereotype.Controller; // Importa a anotação Controller para definir a classe como um controlador do Spring
import org.springframework.web.bind.annotation.*; // Importa anotações do Spring para mapeamento de requests
import org.springframework.web.servlet.ModelAndView; // Importa a classe ModelAndView para retornar views com dados do modelo
import com.br.entrada.saida.pessoas.model.Pessoa; // Importa a classe Pessoa do pacote model
import com.br.entrada.saida.pessoas.repository.PessoaRepository; // Importa a interface PessoaRepository do pacote repository

@Controller // Anota a classe como um controlador do Spring MVC
public class PessoaController {

    @Autowired // Injeta automaticamente uma instância do PessoaRepository
    private PessoaRepository pessoaRepository; // Declara uma variável para acessar o repositório de pessoas

    @GetMapping("/") // Mapeia requests GET para a raiz da aplicação
    public String index() {
        return "index"; // Retorna a view "index"
    }

    @GetMapping("/pessoas") // Mapeia requests GET para "/pessoas"
    public ModelAndView listar() {
        ModelAndView modelAndView = new ModelAndView("listar"); // Cria um objeto ModelAndView para a view "listar"
        modelAndView.addObject("pessoas", pessoaRepository.findAll()); // Adiciona a lista de pessoas ao modelo
        return modelAndView; // Retorna o ModelAndView
    }

    @GetMapping("/pessoas/novo") // Mapeia requests GET para "/pessoas/novo"
    public ModelAndView novo() {
        ModelAndView modelAndView = new ModelAndView("formulario"); // Cria um objeto ModelAndView para a view "formulario"
        modelAndView.addObject("pessoa", new Pessoa()); // Adiciona uma nova instância de Pessoa ao modelo
        return modelAndView; // Retorna o ModelAndView
    }

    @PostMapping("/pessoas") // Mapeia requests POST para "/pessoas"
    public String cadastrar(Pessoa pessoa) {
        pessoa.setId(UUID.randomUUID().toString()); // Define um ID único para a nova pessoa
        pessoa.sethoraEntrada(LocalDateTime.now()); // Define a hora de entrada como a hora atual
        pessoaRepository.save(pessoa); // Salva a pessoa no repositório
        return "redirect:/pessoas"; // Redireciona para a lista de pessoas
    }

    @GetMapping("/pessoas/{id}/editar") // Mapeia requests GET para "/pessoas/{id}/editar"
    public ModelAndView editar(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("formularioEdicao"); // Cria um objeto ModelAndView para a view "formularioEdicao"
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null); // Busca a pessoa pelo ID
        modelAndView.addObject("pessoa", pessoa); // Adiciona a pessoa ao modelo
        return modelAndView; // Retorna o ModelAndView
    }

    @PostMapping("/pessoas/{id}/editar") // Mapeia requests POST para "/pessoas/{id}/editar"
    public String editar(@PathVariable String id, Pessoa pessoaEditada) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null); // Busca a pessoa pelo ID
        if (pessoa != null) { // Verifica se a pessoa existe
            pessoa.setNome(pessoaEditada.getNome()); // Atualiza o nome da pessoa
            pessoa.setTelefone(pessoaEditada.getTelefone()); // Atualiza o telefone da pessoa
            pessoa.setUnidade(pessoaEditada.getUnidade()); // Atualiza a unidade da pessoa
            pessoa.setSetor(pessoaEditada.getSetor()); // Atualiza o setor da pessoa
            pessoa.setAndar(pessoaEditada.getAndar()); // Atualiza o andar da pessoa
            pessoa.setPaciente(pessoaEditada.getPaciente()); // Atualiza o paciente da pessoa
            pessoa.setObservacao(pessoaEditada.getObservacao()); // Atualiza a observação da pessoa
            pessoaRepository.save(pessoa); // Salva a pessoa no repositório
        }
        return "redirect:/pessoas"; // Redireciona para a lista de pessoas
    }

    @PutMapping("/pessoas/{id}") // Mapeia requests PUT para "/pessoas/{id}"
    public String atualizar(Pessoa pessoa) {
        pessoaRepository.save(pessoa); // Atualiza a pessoa no repositório
        return "redirect:/pessoas"; // Redireciona para a lista de pessoas
    }

    @DeleteMapping("/pessoas/{id}") // Mapeia requests DELETE para "/pessoas/{id}"
    public String remover(@PathVariable String id) {
        pessoaRepository.deleteById(id); // Remove a pessoa pelo ID
        return "redirect:/pessoas"; // Redireciona para a lista de pessoas
    }

    @GetMapping("/pessoas/{id}/formularioSaida") // Mapeia requests GET para "/pessoas/{id}/formularioSaida"
    public ModelAndView cadastrarSaida(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("formularioSaida"); // Cria um objeto ModelAndView para a view "formularioSaida"
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null); // Busca a pessoa pelo ID
        if (pessoa == null) { // Verifica se a pessoa não existe
            modelAndView.setViewName("redirect:/pessoas"); // Redireciona para a lista de pessoas
        } else {
            modelAndView.addObject("pessoa", pessoa); // Adiciona a pessoa ao modelo
        }
        return modelAndView; // Retorna o ModelAndView
    }

    @PostMapping("/pessoas/{id}/salvarSaida") // Mapeia requests POST para "/pessoas/{id}/salvarSaida"
    public String salvarSaida(@PathVariable String id, @RequestParam("horaSaida") String horaSaida) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null); // Busca a pessoa pelo ID
        if (pessoa != null) { // Verifica se a pessoa existe
            try {
                LocalDateTime dateTime = LocalDateTime.parse(horaSaida); // Tenta parsear a hora de saída
                pessoa.setHoraSaida(dateTime); // Define a hora de saída da pessoa
                pessoaRepository.save(pessoa); // Salva a pessoa no repositório
            } catch (DateTimeParseException e) { // Captura exceções de parsing de data/hora
                e.printStackTrace(); // Imprime o stack trace da exceção
            }
        }
        return "redirect:/pessoas"; // Redireciona para a lista de pessoas
    }
}
