package com.Batches;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan("com.Batches")
public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerBatchConfig.class, KafkaBatchConfig.class);
            KafkaBatchProducer producer = context.getBean(KafkaBatchProducer.class);
            enviarNotificacao(producer);

            ((AnnotationConfigApplicationContext) context).close();
        } catch (BeansException e) {
            System.err.println("Erro ao iniciar o contexto Spring: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificações: " + e.getMessage());
        }
    }

    private static void enviarNotificacao(KafkaBatchProducer producer) {
        producer.enviarNotificacao("eventos", "Notificação urgente!", "alta");
        producer.enviarNotificacao("eventos", "Lembrete de prazo", "media");
        producer.enviarNotificacao("eventos", "Workshop amanhã", "baixa");

        // Testando o envio em lote
        Arrays.asList("Notificação 2", "Notificação 3", "Notificação 4", "Notificação 5")
                .forEach(msg -> producer.enviarNotificacao("eventos", msg, "media"));

        Arrays.asList("Notificação 2", "Notificação 3", "Notificação 4", "Notificação 5", "Notificação 6", "Notificação 7", "Notificação 8")
                .forEach(msg -> producer.enviarNotificacao("eventos", msg, "baixa"));
    }
}