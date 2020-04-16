# Desafio Backend
Eu sou o Victor candidato a vaga de desenvolvedor Java da Via Varejo.

Contatos: toedio6@gmail.com / 11 99656-6750

## Tecnologias
- Java 8
- Spring Boot 2.2.6
- Maven
- Redis
- Docker

## Decisões Técnicas

### Cache de aplicação utilizando Redis
Eu implementei cache no serviço de busca taxa de juros SELIC. Essa opção melhora a performance da aplicação evitando fazer a requisição na API externa todas as vezes que a consulta for feita. Foi utilizado como chave do cache a data, portanto ele zera o cache todos os dias evitando que o serviço responda uma taxa de juros desatualizada. Utilizei o banco de dados Redis que é muito performático e ideal para cache.

### Validações no Controller
A classe InstallmentController possui notações de validação como @Valid e @NotNull e os DTOS também possui dentro de seus métodos. Eu escolhi fazer a validação na primeira camada porque dessa forma evita que a aplicação processe objetos inválidos e responda o cliente imediatamente caso tenha objetos inválidos. Outra vantagem é que os serviços já recebem os objetos totalmente validados.

### Converters
A aplicação tem um serviço de conversão de objetos utilizado para converter DTO em entidade e vice-versa e esse serviço é utilizado na classe InstallmentController. Dessa forma, o serviço conhece apenas as entidades e o controller apenas os DTOs.

### Docker Compose
A aplicação utiliza a tecnologia docker compose que inicia automaticamente todas as dependências e em seguida inicia a aplicação na porta 8080. 
Eu escolhi essa tecnologia porque é uma ferramenta de fácil utilização para executar vários containers ao mesmo tempo, permite que a aplicação execute em qualquer ambiente, tenha sua infraestrutura sempre padronizada evitando falhas por versão da JDK por exemplo.

## Serviços
Foi criado apenas um serviço conforme solicitado e o mesmo foi documentado bem como os DTOs utilizando swagger. A documentação fica disponível assim que a API iniciar em http://localhost:8080/swagger-ui.html

## Executar os testes
Comando ```mvn test```

## Executar aplicação
A aplicação foi criada utilizando a estrutura de docker compose conforme falado acima. Quando executar o comando iniciará o redis (necessário para o cache) e em seguida a aplicação;

Comando ```docker-compose up```

Caso não tenha docker-compose instalado: https://docs.docker.com/compose/gettingstarted/
