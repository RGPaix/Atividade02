package com.Produtor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;//inicia Springboot
import org.springframework.boot.autoconfigure.SpringBootApplication;//inicia Springboot

//Marca a classe como uma aplicação Spring Boot. Isso habilita a configuração automática do Spring.
@SpringBootApplication
public class MainGUI extends JFrame implements ActionListener {

    @Autowired //O Spring Boot injeta automaticamente a dependência da classe KafkaBatchProducer, que vai ser usada para enviar as mensagens.
    private KafkaBatchProducer kafkaBatchProducer;

    private JTextField mensagemField;
    private JTextField prioridadeField;
    private JTextArea statusArea;

    public MainGUI() {
        setTitle("Produtor Kafka");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        mensagemField = new JTextField(20);
        prioridadeField = new JTextField(10);
        JButton enviarButton = new JButton("Enviar Mensagem");
        statusArea = new JTextArea(10, 30);
        statusArea.setEditable(false);

        enviarButton.addActionListener(this);

        add(new JLabel("Mensagem:"));
        add(mensagemField);
        add(new JLabel("Prioridade (alta, média ou baixa):"));
        add(prioridadeField);
        add(enviarButton);
        add(new JScrollPane(statusArea));

        setVisible(true);
    }

    @Override
    //Método chamado quando o botão "enviar mensagem" é clicado
    public void actionPerformed(ActionEvent e) {
        String mensagem = mensagemField.getText(); // obtém mensagem
        String prioridade = prioridadeField.getText(); //obtém prioridade
        kafkaBatchProducer.enviarMensagem(mensagem, prioridade); //chama o método enviarMensagem da classe KafkaBatchProducer
        statusArea.append("Mensagem enviada: " + mensagem + " com prioridade: " + prioridade + "\n");
        mensagemField.setText(""); // limpa o campo mensagem
        prioridadeField.setText("");//limpa o campo prioridade
    }

    //método para iniciar o springboot e criar a janela gráfica
    public static void main(String[] args) {
        SpringApplication.run(MainGUI.class, args);
        new MainGUI();
    }
}
