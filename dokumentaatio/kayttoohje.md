# Käyttöohje

Lataa tiedosto [battleships.jar](https://github.com/Miniaya/ot-harjoitustyo/releases/tag/viikko5)

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistossa on konfiguraatiotiedosto _config.properties_, joka määrittelee käytetyn tietokannan. Tiedoston muoto on seuraava:

```
shipDB=ships.db
```
Tiedoston viittaaman tietokannan (esitäytetty ships.db) tulee myös olla sovelluksen käynnistyshakemistossa.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

```
java -jar battleships.jar
```

Kun ollaan samassa kansiossa ohjelman kanssa.

## Pelin aloittaminen

Peli käynnistyy aloitusvalikkoon.

![alt_text]()

Peli alkaa, kun painaa _New game_ -painiketta. _Resume game_ -painikkeesta pääsee jatkamaan keskeneräistä peliä.

## Pelin eteneminen

Peli alkaa pelaaja 1:n vuorolla (vasen pelilauta). Vuorot näkyvät pelilautojen alapuolella, ja vuorossa olevan pelaajan pelilauta on hieman suurempi. 

![alt_text]()

Pelaajan painettua jotain pelilaudan ruutua ruutuun tulee näkyviin, osuiko pelaaja (punainen O) vai menikö ammus ohi (musta X). Tämän jälkeen vuoro siirtyy pelaajalle 2, joka puolestaan painaa jotain ruutua omalta pelilaudaltaan (oikea pelilauta).

Kun jompi kumpi pelaajista upottaa jonkin laivan kokonaan, laivan tyyppi ilmestyy näkyville sen pelaajan pelilaudan viereen, joka upotti laivan (pelaaja 1:n upottamat laivat ruudun vasempaan reunaan, _Player 1_ -otsikon alle ja pelaaja 2:n upottamat laivat ruudun oikeaan reunaan _Player 2_ -otsikon alle). 

![alt_text]()

Kun toinen pelaajista upottaa kaikki kuusi laivaa, pelilautojen alle ilmestyy teksti "Player (1/2) won!".

![alt_text]()

## Päävalikkoon palaaminen

Pelinäkymästä pääsee takaisin päävalikkoon painamalla ruudun vasemmassa ylälaidassa olevaa _Back to menu_ -painiketta.

## Pelin sulkeminen

Peli sulkeutuu painamalla aloitusvalikoin _Quit game_ -painiketta.
