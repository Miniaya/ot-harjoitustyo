# Testausdokumentti

Ohjelmaa on testattu itse kirjoitetuilla JUnit-testellä. Järjestelmä on testattu manuaalisesti.

## JUnit-testaus

### Sovelluslogiikka

ShipServiceTest ja ShipTest testaavat paketin battleships.domain metodeja. Testit simuloivat pelin mahdollisia tilanteita. Testit olettavat, että DAO-testit toimivat oikein.

Testeissä tallennettavat tiedot tallennetaan testitietokantaan test.db

### DAO-luokka

DAO-luokka tallentaa testeissä käytetyt tiedot testitietokantaan test.db

### Testikattavuus

Käyttöliittymää lukuunottamatta sovelluksen rivi- ja haarautumakattavuus ovat 100%.

![alt_text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testikattavuus.png)

## Järjestelmätestaus

Järjestelmätestaus on suoritettu manuaalisesti

### Asennus ja konfigurointi

Sovellusta on testattu käyttöohjeen kuvaamalla tavalla Linux-ympäristössä. Testeissä sovelluksen käynnistyshakemistossa ovat olleet tietokanta _ships.db_ sekä tiedosto _config.properties_.

### Toiminnallisuudet

Määrittelydokumentissa ja käyttöohjeessa listatut toiminnallisuudet on käyty läpi. Pelissä ei ole mahdollista pelata vastustajan pelilaudalla, samaa nappia ei voi painaa kahdesti ja pelin päättymisen jälkeen ei ole enää mahdollista jatkaa kyseistä peliä.

## Sovellukseen jääneet laatuongelmat

Sovellus ei osaa itse luoda tietokantaa, mikäli se puuttuu.
