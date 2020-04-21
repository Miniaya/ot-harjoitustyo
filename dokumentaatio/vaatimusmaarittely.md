# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on yksin pelattava Laivanupotus-peli, jonka tarkoitus on tuottaa pelaajalleen iloa ja mukavia pelhetkiä. Pelissä on
pelin satunnaisesti generoimat laivat, joita pelaaja yrittää upottaa.

## Käyttöliittymäluonnos


## Perusversion tarjoama toiminnallisuus

- Pelialusta, johon pelaajien laivat generoidaan valmiiksi
  * Laivojen koordinaatit on tallennettu tietokantaan
- Laivoja upotetaan klikkailemalla pelilautaa
  * X, mikäli osuma meni ohi, O, mikäli laivaan osui
- Peli loppuu, kun kaikki tietokoneen tai pelaajan laivat on upotettu
- Pelaaja vs pelaaja

## Toiminnallisuudet, jotka lisätään, jos aikaa jää

- Pelaaja voi itse vaikuttaa laivojen paikkaan ja asentoon
- Tietokone on "viisaampi" kuin perusversiossa
- Vastustaja on tietokone
- Keskenjäänyttä peliä on mahdollista jatkaa pelin uudelleenkäynnistyksen jälkeen
- Kokonaan uponneet laivat näkyvät kuvina

## Toteutetut toiminnallisuudet (Perusversio)

### Käyttäjälle näkyvät toiminnallisuudet

- [x] Aloitusvalikko
- [x] Aloitusvalikosta pääsee peliin
- [x] Aloitusvalikosta voi sulkea sovelluksen
- [ ] Pelin säännöt ovat sovelluksessa näkyvissä
- [x] Pelinäkymässä näkyy molempien pelaajien peli
- [x] Napin painaminen vaihtaa napin tekstin
- [x] Napin teksti on joko X tai O riippuen siitä, osuiko laivaan vai ei
- [x] O erottuu X:stä värin perusteella
- [x] Peli ilmoittaa, mikä laiva on uponnut, kun se uppoaa kokonaan
- [x] Pelin reunassa näkyy uponneet laivat
- [x] Peli ilmoittaa, kun jompi kumpi pelaajista voittaa
- [ ] Voitetun / hävityn pelin jälkeen tulee valikko, jossa pelin voi aloittaa alusta
- [ ] Mahdollisuus valita, onko peli kaksinpeli vai peli tietokonetta vastaan

### Ohjelman sisäiset toiminnallisuudet

- [x] Ennen pelin alkamista generoidaan laivat
- [x] Laivat tallennetaan tietokantaan
- [x] Randomgeneroidut laivat
- [ ] "Tekoäly", joka pelaa pelaajaa vastaan
- [x] Pelin sulkeutuessa tietokannasta tyhjennetään kaikki ylimääräinen
