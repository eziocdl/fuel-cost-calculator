# ⛽ Calculadora de Combustível 

## 🚀 Descrição

Uma aplicação Java de linha de comando (CLI) que calcula **consumo e custo por km**, armazenando os dados em **CSV** para análise posterior. Desenvolvida com boas práticas de programação e princípios como **DDD (Domain-Driven Design), Clean Architecture, SOLID e Clean Code** para garantir modularidade, escalabilidade e fácil manutenção.

## 🔥 Funcionalidades

✅ **Cálculo de consumo (km/l)**  
✅ **Cálculo do custo por km (R$/km)**  
✅ **Persistência dos cálculos em CSV**  
✅ **Saída formatada e intuitiva na linha de comando**  
✅ **Testes unitários para garantir confiabilidade**  

## 🛠 Tecnologias Utilizadas

☕ **Java 21 (LTS):** Linguagem principal, com melhorias de desempenho e segurança.  
📦 **Gradle:** Gerenciador de dependências e automação de build.  
✅ **JUnit 5:** Framework de testes unitários.  
💡 **IntelliJ IDEA:** Ambiente de desenvolvimento integrado (IDE).  

## 📂 Arquitetura e Estrutura do Projeto

Este projeto segue os princípios do **DDD (Domain-Driven Design)** e **Clean Architecture**, organizando as responsabilidades em camadas bem definidas:

📦 **fuelcalculatorcli**  
 ┣ 📂 **src/main/java**  
 ┃ ┣ 📂 **adapters**        # Interfaces (CLI, Web) e gateways (CSV)  
 ┃ ┣ 📂 **application**     # Orquestração dos casos de uso  
 ┃ ┣ 📂 **domain**         # Lógica de domínio (entidades, serviços)  
 ┃ ┣ 📂 **infrastructure**  # Implementação dos gateways  
 ┃ ┣ 📂 **interfaces**      # Interfaces de usuário (CLI e Web)  
 ┃ ┣ 📂 **usecases**        # Casos de uso  
 ┣ 📂 **resources**        # Arquivos de configuração e recursos  
 ┣ 📂 **src/test/java**    # Diretório para testes  
 ┃ ┣ 📜 **FuelCalculatorTest.java**  # Testes da lógica de cálculo de combustível  
 ┣ 📜 **build.gradle.kts**  # Arquivo de configuração do Gradle  
 ┣ 📜 **README.md**         # Documentação do projeto  
 ┣ 📜 **.gitignore**        # Arquivo para ignorar arquivos irrelevantes no Git  

## 🏛 Padrões de Projeto e Boas Práticas

🔹 **Domain-Driven Design (DDD):** O domínio da aplicação é centralizado e isolado das demais camadas.  
🔹 **Clean Architecture:** Independência entre camadas, garantindo separação de responsabilidades.  
🔹 **SOLID:** Código modular e bem estruturado.  
🔹 **DRY (Don't Repeat Yourself):** Reutilização máxima de código.  
🔹 **KISS (Keep It Simple, Stupid):** Implementação simples e objetiva.  
🔹 **YAGNI (You Ain't Gonna Need It):** Foco apenas no essencial.  
🔹 **Testes Unitários:** Cobertura progressiva para garantir qualidade.  

## ▶️ Como Executar

1️⃣ Clone o repositório:  
   ```bash
   git clone https://github.com/eziocdl/fuelcalculatorcli.git
   ```
2️⃣ Navegue até o diretório do projeto:  
   ```bash
   cd fuelcalculatorcli
   ```
3️⃣ Compile e execute o projeto com Gradle:  
   ```bash
   gradle run
   ```
4️⃣ Siga as instruções na linha de comando.

## 🧪 Como Executar os Testes

1️⃣ Navegue até o diretório do projeto no terminal.  
2️⃣ Execute o comando:  
   ```bash
   gradle test
   ```

## 🤝 Contribuição

Contribuições são bem-vindas! Para colaborar:  
1️⃣ Faça um fork do repositório.  
2️⃣ Crie uma branch para sua funcionalidade:  
   ```bash
   git checkout -b minha-funcionalidade
   ```
3️⃣ Faça commit das alterações:  
   ```bash
   git commit -am 'Adiciona nova funcionalidade'
   ```
4️⃣ Faça push para a branch:  
   ```bash
   git push origin minha-funcionalidade
   ```
5️⃣ Crie um pull request.  

## 📜 Licença

Este projeto está sob a licença MIT.

## ✍️ Autor

👤 **Ezio Cintra de Lima**

