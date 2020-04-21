## Sovelluslogiikka

![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/arkkitehtuuri.png)

## Päätoiminnallisuudet

### "Ampuminen" kohtaan, jossa ei ole laivaa

![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi1.png)

Tapahtumankäsittelijä kutsuu shipServicen metodia isShip, jolle annetaan parametreina painalluksen koordinaatit ja pelaajan numero. Tämä metodi kutsuu SQLShipDaon metodia findByCoordinates, joka saa parametreina aiemmin mainittujen lisäksi yhteyden tietokantaan. Metodi tarkistaa, löytyykö tietokannasta pelaajan 1 laivaa, jonka osa on parametreina annetuissa koordinaateissa (1,1). Koordinaateissa ei ole laivaa, joten metodi palauttaa -1. isShip palauttaa -1 tapahtumankäsittelijälle, joka tarkistaa arvon. Koska se on negatiivinen, napin arvoksi asetetaan "X" (ei osumaa) ja vuoro siirtyy toiselle pelaajalle.

### "Ampuminen" kohtaan, jossa on laiva, mutta laiva ei uppoa

![alt text](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi2.png)

Toinen pelaaja painaa samaa nappia omalla pelilaudallaan. Nyt SQLShipDaon metodi findByCoordinates löytää laivan ja palauttaa sen indeksin (7). Laivan indeksi palautetaan tapahtumankäsittelijälle. Koska palautettu arvo on nyt positiivinen, kutsutaan shipServicen metodia sink (parametrinaan laivan koordinaatit ja indekis), joka kutsuu SQLShipDaon metodia sinkPart. Metodi poistaa kyseisen laivan kyseisistä koordinaateista. Tämän jälkeen tapahtumankäsittelijä kutsuu metodia isSink parametrinaan laivan indeksi. Metodi kutsuu SQLShipDaon metodia isSunk. Metodi palauttaa false, koska laivasta on upotettu vasta osa, ei koko laivaa. ShipService palauttaa tapahtumankäsittelijälle false ja tapahtumankäsittelijä asettaa napin arvoksi "O" (laivaan osui) ja vuoro siirtyy seuraavalle.
