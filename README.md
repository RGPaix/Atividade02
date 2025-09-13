# Atividade02 - Sistema Produtor-Consumidor

## 📋 Descrição

Este projeto implementa o padrão **Producer-Consumer** (Produtor-Consumidor), demonstrando a comunicação assíncrona entre processos através de filas de mensagens. O sistema é composto por dois módulos principais que trabalham de forma independente para simular um ambiente de processamento distribuído.

## 🎯 Objetivos

- Implementar o padrão arquitetural Producer-Consumer
- Demonstrar comunicação assíncrona entre processos
- Aplicar conceitos de concorrência e sincronização
- Gerenciar filas de mensagens de forma eficiente
- Tratar cenários de alta disponibilidade e tolerância a falhas

## 🏗️ Arquitetura do Sistema

```
┌─────────────────┐    📨 Mensagens    ┌─────────────────┐
│                 │ ──────────────────► │                 │
│    PRODUTOR     │                    │   CONSUMIDOR    │
│                 │                    │                 │
│  - Gera dados   │                    │ - Processa dados│
│  - Envia msgs   │                    │ - Executa ações │
└─────────────────┘                    └─────────────────┘
```

## 📁 Estrutura do Projeto

```
Atividade02/
├── Trabalho02Produtor/     # Módulo Produtor
│   ├── src/                # Código fonte do produtor
│   ├── config/             # Configurações
│   └── requirements.txt    # Dependências
├── Trabalho02Consumidor/   # Módulo Consumidor  
│   ├── src/                # Código fonte do consumidor
│   ├── config/             # Configurações
│   └── requirements.txt    # Dependências
└── README.md              # Este arquivo
```

## 🚀 Funcionalidades

### Produtor
- ✅ Geração automática de mensagens
- ✅ Envio para fila de mensagens
- ✅ Controle de rate limiting
- ✅ Logging detalhado de operações
- ✅ Tratamento de erros de conectividade

### Consumidor
- ✅ Consumo contínuo de mensagens
- ✅ Processamento assíncrono
- ✅ Confirmação de recebimento (ACK)
- ✅ Retry automático em caso de falha
- ✅ Métricas de performance

## 💻 Tecnologias Utilizadas

- **Linguagem**: [Python/Java/Node.js]
- **Message Broker**: [RabbitMQ/Apache Kafka/Redis]
- **Bibliotecas**:
  - [pika/amqp] - Comunicação com message broker
  - [json/pickle] - Serialização de dados
  - [logging] - Sistema de logs
  - [threading/asyncio] - Programação concorrente

## ⚡ Pré-requisitos

- [Linguagem] versão X.X+
- Message Broker instalado e configurado
- Dependências listadas em requirements.txt

## 🛠️ Instalação e Execução

### 1. Clone o repositório
```bash
git clone https://github.com/RGPaix/Atividade02.git
cd Atividade02
```

### 2. Configure o Message Broker
```bash
# Para RabbitMQ (exemplo)
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Para outros brokers, seguir documentação específica
```

### 3. Instalar dependências do Produtor
```bash
cd Trabalho02Produtor
pip install -r requirements.txt
```

### 4. Instalar dependências do Consumidor
```bash
cd ../Trabalho02Consumidor
pip install -r requirements.txt
```

### 5. Executar o sistema

**Terminal 1 - Iniciar Consumidor:**
```bash
cd Trabalho02Consumidor
python main.py
```

**Terminal 2 - Iniciar Produtor:**
```bash
cd Trabalho02Produtor  
python main.py
```

## 📊 Monitoramento

O sistema inclui métricas de:
- Taxa de produção de mensagens
- Taxa de consumo de mensagens  
- Latência média de processamento
- Número de mensagens na fila
- Erros e reprocessamentos

## 🧪 Testes

Para executar os testes:

```bash
# Testes do Produtor
cd Trabalho02Produtor
python -m pytest tests/

# Testes do Consumidor
cd Trabalho02Consumidor
python -m pytest tests/
```

## ⚙️ Configuração

Ambos os módulos podem ser configurados através de arquivos de configuração:

**config.json**
```json
{
  "broker_url": "amqp://localhost:5672",
  "queue_name": "task_queue",
  "retry_attempts": 3,
  "batch_size": 100
}
```

## 🔍 Cenários de Uso

1. **Processamento de dados em lote**
2. **Sistema de notificações**
3. **Pipeline de ETL (Extract, Transform, Load)**
4. **Processamento de imagens/arquivos**
5. **Sistema de pedidos e-commerce**

## 🐛 Tratamento de Erros

- **Conexão perdida**: Reconexão automática
- **Mensagem inválida**: Log do erro e descarte
- **Falha no processamento**: Reenvio para fila de retry
- **Sobrecarga do sistema**: Controle de backpressure

## 📈 Melhorias Futuras

- [ ] Interface web para monitoramento
- [ ] Balanceamento de carga automático
- [ ] Persistência de mensagens
- [ ] Métricas em tempo real
- [ ] Deploy com Docker Compose

## 👨‍💻 Autor

**Desenvolvido por:** [@RGPaix](https://github.com/RGPaix)  
**Disciplina:** Sistemas Distribuídos / Programação Concorrente  
**Tema:** Implementação do Padrão Producer-Consumer

## 📚 Referências

- [Documentação RabbitMQ para Java](https://www.rabbitmq.com/java-client.html)
- [Padrões de Mensageria](https://www.enterpriseintegrationpatterns.com/)
- [Java Concurrency in Practice](https://jcip.net/)
- [Apache Maven Guide](https://maven.apache.org/guides/getting-started/)

## 💡 Exemplo de Código

**Estrutura básica do Producer:**
```java
public class Producer {
    private static final String QUEUE_NAME = "task_queue";
    
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
```

## 📞 Contato

- GitHub: [@RGPaix](https://github.com/RGPaix)
- Issues: [Reportar problemas](https://github.com/RGPaix/Atividade02/issues)

---

⭐ **Observação**: Este projeto demonstra conceitos fundamentais de sistemas distribuídos e pode ser usado como base para implementações mais complexas em ambiente de produção.
