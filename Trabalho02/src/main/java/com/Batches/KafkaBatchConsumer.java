package com.Batches;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaBatchConsumer {

    private List<String> mediaPrioridadeBatch = new ArrayList<>();
    private List<String> baixaPrioridadeBatch = new ArrayList<>();

    @KafkaListener(topics = "eventos", groupId = "batch-grupo", containerFactory = "kafkaListenerContainerFactory")
    public void consumirMensagens(List<ConsumerRecord<String, String>> messages) {
        System.out.println("Consumindo lote de mensagens...");

        for (ConsumerRecord<String, String> record : messages) {
            String message = record.value();
            String priority = getPriorityFromMessage(message);

            switch (priority) {
                case "alta":
                    processarMensagem(record);
                    System.out.println("Alta prioridade: mensagem processada imediatamente.");
                    break;

                case "media":
                    mediaPrioridadeBatch.add(message);
                    if (mediaPrioridadeBatch.size() >= 4) {
                        processarLote(mediaPrioridadeBatch);
                        mediaPrioridadeBatch.clear();
                    }
                    break;

                case "baixa":
                    baixaPrioridadeBatch.add(message);
                    if (baixaPrioridadeBatch.size() >= 7) {
                        processarLote(baixaPrioridadeBatch);
                        baixaPrioridadeBatch.clear();
                    }
                    break;
            }
        }
    }

    private void processarMensagem(ConsumerRecord<String, String> record) {
        System.out.println("Processando mensagem: " + record.value());
    }

    private void processarLote(List<String> messages) {
        System.out.println("Processando lote de " + messages.size() + " mensagens.");
        for (String msg : messages) {
            System.out.println("Processando mensagem em lote: " + msg);
        }
    }

    private String getPriorityFromMessage(String message) {
        if (message.contains("alta")) {
            return "alta";
        } else if (message.contains("media")) {
            return "media";
        } else {
            return "baixa";
        }
    }
}