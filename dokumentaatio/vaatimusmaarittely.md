# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on yksin pelattava Laivanupotus-peli, jonka tarkoitus on tuottaa pelaajalleen iloa ja mukavia pelhetkiä. Pelissä on
pelin satunnaisesti generoimat laivat, joita pelaaja yrittää upottaa.

## Käyttöliittymäluonnos


## Perusversion tarjoama toiminnallisuus

- Pelialusta, johon pelaajan ja tietokoneen laivat generoidaan valmiiksi
  * Laivojen koordinaatit on tallennettu tietokantaan
- Pelaaja upottaa tietokoneen laivoja klikkailemalla pelilautaa
  * X, mikäli osuma meni ohi, O, mikäli laivaan osui
- Tietokone yrittää upottaa pelaajan laivoja
- Peli loppuu, kun kaikki tietokoneen tai pelaajan laivat on upotettu

## Toiminnallisuudet, jotka lisätään, jos aikaa jää

- Pelaaja voi itse vaikuttaa laivojen paikkaan ja asentoon
- Tietokone on "viisaampi" kuin perusversiossa
- Mahdollisuus kaksinpeliin
- Keskenjäänyttä peliä on mahdollista jatkaa pelin uudelleenkäynnistyksen jälkeen
- Kokonaan uponneet laivat näkyvät kuvina

## Toteutetut toiminnallisuudet (Perusversio)

### Käyttäjälle näkyvät toiminnallisuudet

- [x] Aloitusvalikko
- [x] Aloitusvalikosta pääsee peliin
- [ ] Aloitusvalikosta voi sulkea sovelluksen
- [ ] Pelin säännöt ovat sovelluksessa näkyvissä
- [ ] Pelinäkymässä näkyy molempien pelaajien peli
- [x] Napin painaminen vaihtaa napin tekstin
- [x] Napin teksti on joko X tai O riippuen siitä, osuiko laivaan vai ei
- [x] O erottuu X:stä värin perusteella
- [x] Peli ilmoittaa, mikä laiva on uponnut, kun se uppoaa kokonaan
- [ ] Pelin reunassa näkyy, montako laivaa on uponnut ja mitkä uponneet laivat ovat
- [ ] Peli ilmoittaa, kun pelaaja / kone voittaa
- [ ] Voitetun / hävityn pelin jälkeen tulee valikko, jossa pelin voi aloittaa alusta

### Ohjelman sisäiset toiminnallisuudet

- [x] Ennen pelin alkamista generoidaan laivat
- [x] Laivat tallennetaan tietokantaan
- [ ] Randomgeneroidut laivat
- [ ] "Tekoäly", joka pelaa pelaajaa vastaan
- [ ] Pelin sulkeutuessa tietokannasta tyhjennetään kaikki ylimääräinen
