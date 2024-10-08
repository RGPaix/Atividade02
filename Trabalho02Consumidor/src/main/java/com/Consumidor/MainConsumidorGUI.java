package com.Consumidor;

import javax.swing.*;
import java.awt.*;
import org.springframework.boot.SpringApplication;// Inicializa a aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;// Indica que é uma aplicação Spring Boot
import org.springframework.kafka.annotation.KafkaListener;// Permite o uso do KafkaListener para consumir mensagens do Kafka
import java.util.List;

// Define a classe como uma aplicação Spring Boot
@SpringBootApplication
public class MainConsumidorGUI extends JFrame {

    private JTextArea mensagensArea;

    public MainConsumidorGUI() {
        setTitle("Consumidor Kafka");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mensagensArea = new JTextArea();
        mensagensArea.setEditable(false);
        add(new JScrollPane(mensagensArea), BorderLayout.CENTER);

        setVisible(true);
    }
    //Método para consumir mensagens do topico alta-prioridade
    @KafkaListener(topics = "topico-alta-prioridade", groupId = "grupo-alta-prioridade")
    public void consumirMensagensAltaPrioridade(String mensagem) { ////garante que a atualização do componente de interface gráfica ocorra na thread correta
        SwingUtilities.invokeLater(() -> mensagensArea.append("URGENTE: " + mensagem + "\n"));
    }

    //Método para consumir mensagens do tópico media-prioridade
    @KafkaListener(topics = "topico-media-prioridade", groupId = "grupo-media-prioridade", containerFactory = "kafkaListenerContainerFactory")
    public void consumirMensagensMediaPrioridade(List<String> mensagens) {
        SwingUtilities.invokeLater(() -> { // Para cada mensagem na lista de mensagens, adiciona ao JTextArea
            for (String mensagem : mensagens) {
                mensagensArea.append("Mensagem de média prioridade: " + mensagem + "\n");
            }
        });
    }
    //Método para consumir mensagens do topico baixa-prioridade
    @KafkaListener(topics = "topico-baixa-prioridade", groupId = "grupo-baixa-prioridade", containerFactory = "kafkaListenerContainerFactory")
    public void consumirMensagensBaixaPrioridade(List<String> mensagens) {
        SwingUtilities.invokeLater(() -> { // Para cada mensagem na lista de mensagens, adiciona ao JTextArea
            for (String mensagem : mensagens) {
                mensagensArea.append("Mensagem de baixa prioridade: " + mensagem + "\n");
            }
        });
    }

    //Método para iniciar o spring boot e abrir a janela, que vai começar a consumir as mensagens dos tópicos no Kafka Listener
    public static void main(String[] args) {
        SpringApplication.run(MainConsumidorGUI.class, args);
    }
}