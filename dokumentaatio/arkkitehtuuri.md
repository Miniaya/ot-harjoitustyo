## Rakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria. Koodin pakkausrakenne on seuraava:
![alt_text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkaus _battleships.ui_ sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, _battleships.domain_ sovelluslogiikan ja _battleships.dao_ tietojen talletuksesta vastaavan koodin.

## Käyttöliittymä

Käyttöliittymä sisältää kaksi eri näkymää

- Pelin päävalikko
- Pelinäkymä

Molemmat näkymät on toteutettu erillisinä Scene-olioina. Näkymät ovat näkyvillä vuorotellen. Käyttöliittymä luodaan ohjelmallisesti luokassa battleships.ui.BattleshipsUi.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat _Ship_ ja _ShipType_, jotka kuvaavat pelilaudalla olevia laivoja.

_ShipService_ vastaa sovelluksen toiminnallisista kokonaisuuksista. Luokka tarjoaa käyttöliittymän toiminnallisuuksille metodit. Luokka pystyy muokkaamaan tietokannassa olevia tietoja pakkauksessa _battleships.dao_ olevan _SQLShipDao_ avulla, joka toteuttaa rajapinnan _ShipDao_.

Ohjelman sisäisiä suhteita kuvaava luokka/pakkauskaavio:
![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.png)

## Tietojen pyssyväistallennus

Pakkauksen _battleships.dao_ luokka _SQLShipDao_ vastaa laivojen ja koordinaattien hallinnoinnista tietokannassa. Luokka perii rajapinnan _ShipDao_.

### Tietokanta

Sovellus tallentaa laivat, niiden sijainnit ja pelaajien ohiampumiset tietokantaan. Tietokanta rakentuu seuraavasti:
![alt_text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/tietokanta.png)

Tauluun _Shiptypes_ on valmiiksi tallennettu pelin laivojen tyypit seuraavasti:
| id | type |
|----|------|
| 1  | carrier |
| 2  | battleship |
| 3  | cruiser |
| 4  | submarine |
| 5  | destroyer |
| 6  | rowboat |

Muissa tauluissa on pelitilanteesta riippuen tallennettu laivojen omistajat ja tieto uponneista laivoista tauluun _Ships_, laivojen osien koordinaatit ja tieto siitä, onko laivan osa uponnut, tauluun _Coordinates_ ja pelaajien ampumat ohilaukaukset tauluun _Missed_.

### Päätoiminnallisuudet

#### Ampuminen kohtaan, jossa ei ole laivaa

![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi1.png)

Tapahtumankäsittelijä kutsuu shipServicen metodia isShip, jolle annetaan parametreina painalluksen koordinaatit ja pelaajan numero. Tämä metodi kutsuu SQLShipDaon metodia findByCoordinates, joka saa parametreina aiemmin mainittujen lisäksi yhteyden tietokantaan. Metodi tarkistaa, löytyykö tietokannasta pelaajan 1 laivaa, jonka osa on parametreina annetuissa koordinaateissa (1,1). Koordinaateissa ei ole laivaa, joten metodi palauttaa -1. isShip palauttaa -1 tapahtumankäsittelijälle, joka tarkistaa arvon. Koska se on negatiivinen, napin arvoksi asetetaan "X" (ei osumaa). Tapahtumankäsittelijä kutsuu vielä metodia shipServicen addMissed, joka kutsuu SQLShipDaon samannimistä metodia. Tämä metodi tallentaa koordinaatit tietokannan tauluun Missed. Kutsutaan vielä UI:n metodia changeTurn ja vuoro siirtyy toiselle pelaajalle.

#### Ampuminen kohtaan, jossa on laiva, mutta laiva ei uppoa

![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi2.png)

Toinen pelaaja painaa samaa nappia omalla pelilaudallaan. Nyt SQLShipDaon metodi findByCoordinates löytää laivan ja palauttaa sen indeksin (7). Laivan indeksi palautetaan tapahtumankäsittelijälle. Koska palautettu arvo on nyt positiivinen, tapahtumankäsittelijä asettaa napin arvoksi "O" (laivaan osui) ja kutsu shipServicen metodia sink (parametrinaan laivan koordinaatit ja indekis), joka puolestaan kutsuu SQLShipDaon metodia sinkPart. Metodi poistaa kyseisen laivan kyseisistä koordinaateista. Tämän jälkeen tapahtumankäsittelijä kutsuu metodia isSunk parametrinaan laivan indeksi. Metodi kutsuu SQLShipDaon samannimistä metodia. Tämä palauttaa false, koska laivasta on upotettu vasta osa, ei koko laivaa. ShipService palauttaa tapahtumankäsittelijälle null. Kutsutaan UI:n metodia changeTurn ja vuoro siirtyy takaisin ensimmäiselle pelaajalle.

#### Ampuminen kohtaan, jossa on laiva, ja se uppoaa

![alt_text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi3.png)

Pelaaja 2 painaa nappia, jossa on edellä löydetyn laivan viimeinen uppoamaton osa. Erona edelliseen tapahtumaan on, että SQLShipDaon metodi isSunk palauttaa true, koska kaikki laivan osat ovat nyt uponneet. Metodi kutsuu nyt SQLShipDaon metodia getShip, joka palauttaa uponneen laivan tyypin (tässä tapauksessa carrier). Metodi kutsuu vielä SQLShipDaon metodia sinkShip, joka merkitsee kyseisen laivan upotetuksi. ShipServicen metodi palauttaa tapahtumankäsittelijälle arvon carrier. Koska tämän arvo ei ole null, laiva lisätään upotettujen listalle pelaajan 2 alapuolelle. UI:n metodia changeTurn kutsutaan ja vuoro siirtyy pelaajalle 1.

## Ohjelman rakenteen heikkoudet

### Käyttöliittymä

Pelilauta ja siihen liittyvät metodit on nyt määritelty luokassa _BattleshipsUi_. Rakenne olisi selkeämpi, mikäli nämä olisi määritelty omassa luokassa.
