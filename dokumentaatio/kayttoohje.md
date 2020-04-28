# Käyttöohje

Lataa tiedosto [battleships.jar]()

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

`java -jar battleships.jar`

Kun ollaan samassa kansiossa ohjelman kanssa.

## Pelin aloittaminen

Peli käynnistyy aloitusvalikkoon. Peli alkaa, kun painaa _New game_ -painiketta.

## Pelin eteneminen

Peli alkaa pelaaja 1:n vuorolla (vasen pelilauta). Pelaajan painettua jotain pelilaudan ruutua ruutuun tulee näkyviin, osuiko pelaaja (punainen O) vai menikö ammus ohi (musta X). Tämän jälkeen vuoro siirtyy pelaajalle 2, joka puolestaan painaa jotain ruutua omalta pelilaudaltaan (oikea pelilauta). Kun jompi kumpi pelaajista upottaa jonkin laivan kokonaan, laivan tyyppi ilmestyy näkyville sen pelaajan pelilaudan viereen, joka upotti laivan (pelaaja 1:n upottamat laivat ruudun vasempaan reunaan, _Player 1_ -otsikon alle ja pelaaja 2:n upottamat laivat ruudun oikeaan reunaan _Player 2_ -otsikon alle). Kun toinen pelaajista upottaa kaikki kuusi laivaa, pelilautojen alle ilmestyy teksti "Player (1/2) won!".

## Päävalikkoon palaaminen

Pelinäkymästä pääsee takaisin päävalikkoon painamalla ruudun vasemmassa ylälaidassa olevaa _Back to menu_ -painiketta.

## Pelin sulkeminen

Peli sulkeutuu painamalla aloitusvalikoin _Quit game_ -painiketta.
