package com.Produtor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  Serviço responsável por enviar mensagens ao Kafka em lote
 *  Está sendo utilizado o KafkaTemplate, que são agrupadas em lotes
 *  de acordo com as configurações definidas.
 */

@Service
public class KafkaBatchProducer {
    //KafkaTemplate injeta a capacidade de produzir mensagem para o Kafka
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //List criada para armazenar as mensagens de media e baixa prioridade
    private List<String> mediaPrioridadeBatch = new ArrayList<>();
    private List<String> baixaPrioridadeBatch = new ArrayList<>();

    //Método para enviar as mensagens conforme sua prioridade
    public void enviarMensagem(String mensagem, String prioridade) {
        prioridade = prioridade.toLowerCase();
        switch (prioridade) {
            case "alta":
                // Alta prioridade: envia imediatamente
                kafkaTemplate.send("topico-alta-prioridade", mensagem); // envia mensagem para o tópico alta prioridade
                System.out.println("Mensagem de alta prioridade enviada: " + mensagem);
                break;

            case "média":
                // Média prioridade: acumula 3 mensagens e envia
                mediaPrioridadeBatch.add(mensagem);
                if (mediaPrioridadeBatch.size() >= 3) {
                    for (String msg : mediaPrioridadeBatch) {
                        kafkaTemplate.send("topico-media-prioridade", msg); // envia mensagem para o tópico media prioridade
                    }
                    mediaPrioridadeBatch.clear(); //Limpa a lista
                    System.out.println("Mensagens de média prioridade enviadas");
                }
                break;

            case "baixa":
                // Baixa prioridade: acumula 6 mensagens e envia
                baixaPrioridadeBatch.add(mensagem);
                if (baixaPrioridadeBatch.size() >= 6) {
                    for (String msg : baixaPrioridadeBatch) {
                        kafkaTemplate.send("topico-baixa-prioridade", msg); // envia mensagem para o tópico baixa prioridade
                    }
                    baixaPrioridadeBatch.clear(); //Limpa a lista
                    System.out.println("Mensagens de baixa prioridade enviadas");
                }
                break;

            default: // caso o usuário digite algo além de "alta", "média" ou "baixa" no campo prioridade
                System.out.println("Prioridade inválida.");
                break;
        }
    }
}