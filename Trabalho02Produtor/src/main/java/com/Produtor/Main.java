package com.Produtor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta classe é o ponto de entrada da aplicação e é responsável por iniciar o envio de mensagens
 * em lote logo após a inicialização.
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    // Injeta o serviço responsável por enviar mensagens em lote para o Kafka
    @Autowired
    private KafkaBatchProducer kafkaBatchProducer;

    /**
     * Metodo principal que inicia a aplicação Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Metodo que é executado após a inicialização da aplicação.
     *
     * Aqui chamamos o metodo enviarMensagem() do `KafkaBatchProducer` para enviar
     * as mensagens ao Kafka logo após a inicialização.
     *
     */
    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Digite a mensagem: ");
            String mensagem = scanner.nextLine();

            System.out.println("Defina a prioridade (alta, média ou baixa): ");
            String prioridade = scanner.nextLine();

            kafkaBatchProducer.enviarMensagem(mensagem, prioridade);
        }
    }
}