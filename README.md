# Battleships

Sovellus on oma versioni klassisesta laivanupotus-pelistä, jossa tarkoituksena on "upottaa" vastustajan laivat pelilaudalta ennen kuin vastustaja saa upotettua sinun laivasi.

## Dokumentaatio

[Käyttöohje](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/Miniaya/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla `mvn test`

Testikattavuusraportti luodaan komennolla `mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimessa tiedosto target/site/jacoco/index.html

### Checkstyle

Tiedostoon checkstyle.xml määritellyt tarkistukset suoritetaan komennolla `mvn jxr:jxr checkstyle:checkstyle`

Mahdollisia virheitä voi tarkastella avaamalla selaimessa tiedost target/site/checkstyle.html
