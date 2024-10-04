package com.Batches;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class KafkaBatchProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private List<String> mediaPrioridade = new ArrayList<>();
    private List<String> baixaPrioridade = new ArrayList<>();

    public void enviarNotificacao(String topic, String message, String priority) {
        switch (priority) {
            case "alta":
                // Alta prioridade: envia imediatamente
                kafkaTemplate.send(topic, message);
                System.out.println("Alta prioridade: mensagem enviada imediatamente.");
                break;

            case "media":
                // Média prioridade: acumula 4 mensagens e envia
                mediaPrioridade.add(message);
                if (mediaPrioridade.size() >= 4) {
                    sendBatch(topic, mediaPrioridade);
                    mediaPrioridade.clear();
                }
                break;

            case "baixa":
                // Baixa prioridade: acumula 7 mensagens e depois envia
                baixaPrioridade.add(message);
                if (baixaPrioridade.size() >= 7) {
                    sendBatch(topic, baixaPrioridade);
                    baixaPrioridade.clear();
                }
                break;
        }
    }

    private void sendBatch(String topic, List<String> messages) {
        for (String message : messages) {
            kafkaTemplate.send(topic, message);
        }
        System.out.println("Batch de " + messages.size() + " mensagens enviado.");
    }
}