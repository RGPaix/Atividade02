package com.Consumidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta classe é o ponto de entrada da aplicação e é responsável por iniciar o envio de mensagens
 * em lote logo após a inicialização.
 */

@SpringBootApplication
public class MainConsumidor {

    /**
     * Metodo principal que inicia a aplicação Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(MainConsumidor.class, args);
    }

    //Método Listener p/ escutar mensagens de cada topico e métodos para consumir cada uma delas
    @KafkaListener(topics = "topico-alta-prioridade", groupId = "grupo-prioridade")
    public void consumirMensagensAltaPrioridade(String mensagem) {
        System.out.println("Mensagem de alta prioridade consumida: " + mensagem);
    }
    @KafkaListener(topics = "topico-media-prioridade", groupId = "grupo-prioridade")
    public void consumirMensagensMediaPrioridade(List<String> mensagens) {
        System.out.println("Mensagens de média prioridade consumidas em lote:");
        for (String mensagem : mensagens) {
            System.out.println(mensagem);
        }
    }
    @KafkaListener(topics = "topico-baixa-prioridade", groupId = "grupo-prioridade")
    public void consumirMensagensBaixaPrioridade(List<String> mensagens) {
        System.out.println("Mensagens de baixa prioridade consumidas em lote:");
        for (String mensagem : mensagens) {
            System.out.println(mensagem);
        }
    }
}