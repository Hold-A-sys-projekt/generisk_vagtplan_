# Generisk Vagtplan - Hold A - Systemudvikling - forår 2024

## Projektbeskrivelse 
Vi (Hold A) er blevet sat til at være leverandører på en applikation, som vi skal levere til Hold B. Applikationen hedder Generisk Vagtplanlægger og er som navnet antyder et system, der tillader en virksomhed at planlægge medarbejdernes vagter. 

### Udviklingsmetode
Vores agile udviklingsmetode er SCRUM, og holdet er indelt i mindre teams, der sammen skal bygge prototypen. Kravspecifikationerne for systemet er aftalt med modtager, og vi skal bygge en prototype af systemet i løbet af to sprint af to ugers varinghed. 

## Applikationens tech stack 

### Der anvendes følgende teknologier i projektet
![Java](https://img.shields.io/badge/Java-%23FF0000?style=flat-square&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%23336791?style=flat-square&logo=postgresql&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-%23000000?style=flat-square&logo=intellij-idea&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-%23181717?style=flat-square&logo=github&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-%23C71A36?style=flat-square&logo=apache-maven&logoColor=white)
![Javalin](https://img.shields.io/badge/Javalin-%230056D6?style=flat-square&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-%23F7DF1E?style=flat-square&logo=javascript&logoColor=black)
![React](https://img.shields.io/badge/React-%2361DAFB?style=flat-square&logo=react&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-%2338B2AC?style=flat-square&logo=tailwind-css&logoColor=white)


## Beskrivelse af applikationens tech stack 

### Database: PostgreSQL
PostgreSQL er en kraftfuld open source objekt-relationsdatabase, der vil fungere som det centrale lagringssted for data. Den understøtter komplekse forespørgsler, transaktionsintegritet og robusthed, hvilket gør den ideel til at håndtere alle datakrav fra applikationen.

### Backend: Javalin og Java
- **Java** er et meget anvendt programmeringssprog, der tilbyder høj performance og sikkerhed. Det vil være grundlaget for at skrive serverlogikken.
- **Javalin** er et meget letvægts web-framework for Java og Kotlin, der gør det nemt at oprette servere i Java. Det er designet til at være simpelt og minimalistisk, og det fungerer godt til hurtige API-udviklinger. Javalin understøtter RESTful API-ruteskrivning og har indbygget support for JSON-serialisering.

### Frontend: React, Vite, Tailwind CSS, og ShadCN
- **React** er et populært JavaScript-bibliotek til bygning af brugergrænseflader, specielt single-page applikationer hvor en hurtig, dynamisk brugeroplevelse er nødvendig. Det tillader udviklere at oprette store webapplikationer, som kan ændre data, uden at siden behøver at genindlæses.
- **Vite** er en moderne, hurtig front-end bygger, der tilbyder hot module replacement (HMR). Vite forbedrer udviklingsoplevelsen ved at tilbyde hurtigere opstarts- og opdateringstider sammenlignet med ældre værktøjer som Webpack.
- **Tailwind CSS** er et utility-first CSS-framework, der tillader hurtig stilisering af applikationer uden at skrive meget brugerdefineret CSS. Ved at bruge Tailwind kan udviklere effektivt implementere design hurtigt ved hjælp af klasser direkte i deres markup.
- **ShadCN** Open Source components som man kan anvende til at lave sit eget komponentbibliotek. 

#### Sammenkobling
- **Backend** (Javalin + Java) vil håndtere HTTP-forespørgsler, udføre logik, interagere med databasen (PostgreSQL), og sende data til frontend.
- **Frontend** (React + Vite + Tailwind CSS) vil præsentere dataene til brugerne og muliggøre interaktioner, hvor brugernes handlinger sendes tilbage til serveren via API-kald.
