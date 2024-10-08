package com.Consumidor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por consumir mensagens em lote do Kafka
 *
 * O consumidor é configurado para processar várias mensagens de uma vez,
 * utilizando o processamento em lote oferecido pelo Kafka.
 */

@Service
public class KafkaBatchConsumer {
    /**
     * Metodo que consome mensagens em lote do tópico informado.
     * O KafkaListener escuta o tópico configurado e recebe as mensagens em lote.
     * O parâmetro List<String> indica que o metodo é capaz de receber
     * múltiplas mensagens em uma única invocação.
     */
    @KafkaListener(topics = "topico-alta-prioridade", groupId = "grupo-prioridade")
    /**
     * Métodos que processam cada mensagem individualmente de um lote.
     */
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