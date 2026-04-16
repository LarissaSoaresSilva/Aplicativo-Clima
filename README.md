<p align="center">
  <img src="https://github.com/user-attachments/assets/64d3f6ef-503c-44eb-9f5d-5062fae30c75" alt="Banner do Projeto">
</p>

<h1 align="center">🌤️ Aplicativo Clima </h1>

<p align="center">
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-purple?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Java-17-blueviolet?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-3.8.6-orange?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img src="https://img.shields.io/badge/API-Open--Meteo-00ADEF?style=for-the-badge" />
  <img src="https://img.shields.io/badge/JSON-org.json-black?style=for-the-badge" />
</p>

<p align="center">
  <a href="#descrição-do-projeto">Descrição</a> •
  <a href="#tecnologias-utilizadas">Tecnologias</a> •
  <a href="#funcionalidades">Funcionalidades</a> •
  <a href="#estrutura-do-projeto">Estrutura</a> •
  <a href="#como-executar-o-projeto">Como Executar</a> •
  <a href="#melhorias-futuras">Melhorias Futuras</a> 
</p>

---

## 📄 Descrição do Projeto

O **Aplicativo Clima** é uma aplicação desenvolvida em **Java 17**, que permite ao usuário consultar informações meteorológicas em tempo real a partir do nome de uma cidade.

O sistema utiliza a API pública **Open-Meteo** para:

- Converter o nome da cidade em coordenadas geográficas (latitude e longitude)
- Buscar dados meteorológicos atualizados
- Exibir o clima atual e a previsão dos próximos dias

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Maven
- API Open-Meteo
- Biblioteca JSON (`org.json`)
- VS Code

---

## ⚙️ Funcionalidades

- 🔍 Busca de clima por nome da cidade
- 🌡 Exibição da temperatura atual
- 💨 Exibição da velocidade do vento
- 🌤 Descrição da condição climática
- 📅 Previsão do tempo para os próximos 3 dias
- 🇧🇷 Formatação de datas no padrão brasileiro (dd/MM/yyyy)

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 17+
- Maven

### Passos

```bash
git clone https://github.com/LarissaSoaresSilva/Aplicativo-Clima.git
cd Aplicativo-Clima
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.Main"
```

---

## 👩🏻‍💻 Desenvolvedora

Feito com 💜 por **Larissa Soares**
