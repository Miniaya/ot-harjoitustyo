# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on yksin pelattava Laivanupotus-peli, jonka tarkoitus on tuottaa pelaajalleen iloa ja mukavia pelhetkiä. Pelissä on
pelin satunnaisesti generoimat laivat, joita pelaaja yrittää upottaa.

## Perusversion tarjoama toiminnallisuus

- Pelialusta, johon pelaajien laivat generoidaan valmiiksi
  * Laivojen koordinaatit on tallennettu tietokantaan
- Laivoja upotetaan klikkailemalla pelilautaa
  * X, mikäli osuma meni ohi, O, mikäli laivaan osui
- Peli loppuu, kun kaikki toisen pelaajan laivat on upotettu
- Pelaaja vs pelaaja

## Jatkokehitysideoita

- Pelaaja voi itse vaikuttaa laivojen paikkaan ja asentoon
- Mahdollisuus valita pelimuoto "pelaaja vs pelaaja" tai "pelaaja vs tietokone"
- Kokonaan uponneet laivat näkyvät kuvina
- Sovellus pitää kirjaa klikkauksista, joilla peli voitetaan. Mahdollisuus verrata aiempiin klikkausten määrään.

## Toteutetut toiminnallisuudet (Perusversio)

### Käyttäjälle näkyvät toiminnallisuudet

- [x] Aloitusvalikko
- [x] Aloitusvalikosta pääsee peliin
- [x] Aloitusvalikosta voi sulkea sovelluksen
- [x] Pelinäkymässä näkyy molempien pelaajien peli
- [x] Napin painaminen vaihtaa napin tekstin
- [x] Napin teksti on joko X tai O riippuen siitä, osuiko laivaan vai ei
- [x] O erottuu X:stä värin perusteella
- [x] Peli ilmoittaa, mikä laiva on uponnut, kun se uppoaa kokonaan
- [x] Pelin reunassa näkyy uponneet laivat
- [x] Peli ilmoittaa, kun jompi kumpi pelaajista voittaa
- [x] Kesken jäänyttä peliä voi jatkaa 

### Ohjelman sisäiset toiminnallisuudet

- [x] Ennen pelin alkamista generoidaan laivat
- [x] Laivat tallennetaan tietokantaan
- [x] Randomgeneroidut laivat
- [x] Pelin sulkeutuessa tietokannasta tyhjennetään kaikki ylimääräinen
