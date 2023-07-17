
  <h1>MicroserviceEmail</h1>

  <p>Este é um projeto de microserviço de email que utiliza o AWS SES e RabbitMQ para mensageria. O serviço é construído com base no framework Spring Boot e possui funcionalidades para enviar emails.</p>

  <h2>Configuração</h2>

  <p>Antes de executar o microserviço, é necessário configurar algumas credenciais e propriedades.</p>

  <p>No arquivo <code>application.properties</code>, localize as seguintes propriedades e substitua os valores de acordo com suas configurações:</p>

  <pre>
    spring.datasource.url=jdbc:mysql://localhost:3306/email_microservice
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=update

    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=seu_email@gmail.com
    spring.mail.password=sua_senha
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

    spring.rabbitmq.addresses=amqps://seu_usuario:seu_senha@beaver.rmq.cloudamqp.com/sua_fila
    spring.rabbitmq.queue=ms.email
  </pre>

  <p>Certifique-se de substituir <code>seu_email@gmail.com</code> e <code>sua_senha</code> pelas suas credenciais de e-mail, e <code>seu_usuario</code>, <code>seu_senha</code> e <code>sua_fila</code> pelas informações de configuração do RabbitMQ.</p>

  <h2>Dependências</h2>

  <p>O projeto utiliza o Apache Maven para gerenciamento de dependências. As dependências do projeto estão listadas no arquivo <code>pom.xml</code>. Abaixo estão as principais dependências utilizadas:</p>

  <ul>
    <li>Spring Boot</li>
    <li>Spring Data JPA</li>
    <li>Spring Web</li>
    <li>Spring AMQP</li>
    <li>Spring Mail</li>
    <li>Spring Validation</li>
    <li>Lombok</li>
    <li>MySQL Connector/J</li>
    <li>Spring Security</li>
    <li>Java JWT</li>
    <li>Spring Boot Test</li>
  </ul>

  <h2>Rotas</h2>

  <p>O microserviço possui uma rota para enviar emails. Abaixo está a definição da rota:</p>

  <pre>
    POST /sending-email

    {
      "ownerRef": "ownerRef",
      "emailFrom": "email@gmail.com",
      "emailTo": "email@gmail.com",
      "subject": "subject",
      "text": "text"
    }
  </pre>

  <p>Exemplo de resposta:</p>

  <pre>
    {
      "emailFrom": "email@gmail.com",
      "emailTo": "email@gmail.com",
      "subject": "subject",
      "text": "text"
    }
  </pre>

  <p>Em caso de sucesso, a resposta terá o status HTTP 201 (Created) e retornará o objeto do email enviado.</p>

  <h2>Executando o Microserviço</h2>

  <p>Para executar o microserviço, certifique-se de ter o Apache Maven instalado em sua máquina. Em seguida, execute o seguinte comando na raiz do projeto:</p>

  <pre>
    mvn spring-boot:run
  </pre>

  <p>O microserviço será iniciado e estará disponível no endereço <code>http://localhost:8080</code>.</p>
